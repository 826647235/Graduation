import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


@WebServlet(name = "GetTran")
public class GetTran extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        OutputStreamWriter out = new OutputStreamWriter(response.getOutputStream());
        String tag = request.getParameter("Tag");
        String position = request.getParameter("Position");
        try {
            Connection connection = ConnectSQL.getConnection();
            String SQL = "Select * from transaction where tag like ? and id < ? and isFinish = 0 order by id DESC";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1,"%" + tag + "%");
            preparedStatement.setInt(2, Integer.parseInt(position));
            ResultSet resultSet = preparedStatement.executeQuery();
            TranFormat tranFormat = new TranFormat(resultSet, 10);
            String tranJson = new Gson().toJson(tranFormat);
            connection.close();
            out.write(tranJson);
        } catch (Exception e) {
            out.write("false");
        }
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
