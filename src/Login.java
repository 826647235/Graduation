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

@WebServlet(name = "Login")
public class Login extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        OutputStreamWriter out = new OutputStreamWriter(response.getOutputStream());
        String wechat = request.getParameter("WeChat");
        try {
            Connection connection = ConnectSQL.getConnection();
            String SQL = "select * from account where wechat = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1,wechat);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                Account account = new Account(resultSet);
                out.write(new Gson().toJson(account));
            } else {
                out.write("Not Exist");
            }
            connection.close();
        } catch (Exception e) {
            out.write("false");
        }
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
