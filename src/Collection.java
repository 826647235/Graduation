import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet(name = "Collection")
public class Collection extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        String account = request.getParameter("Account");
        String Id = request.getParameter("Id");
        OutputStreamWriter out = new OutputStreamWriter(response.getOutputStream());
        try {
            Connection connection = ConnectSQL.getConnection();
            String SQL = "insert into collection values (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1,account);
            preparedStatement.setInt(2,Integer.parseInt(Id));
            preparedStatement.executeUpdate();
            connection.close();
            out.write("success");
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.write("false");
        }
        out.flush();
        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
