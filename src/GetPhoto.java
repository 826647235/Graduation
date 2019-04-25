import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet(name = "GetPhoto")
public class GetPhoto extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        OutputStream os = response.getOutputStream();
        String id = request.getParameter("id");
        String position = request.getParameter("Position");
        String path = null;
        try {
            Connection connection = ConnectSQL.getConnection();
            String SQL = "select path from transactionphoto where Position = ? and id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setInt(1,Integer.parseInt(position));
            preparedStatement.setInt(2,Integer.parseInt(id));
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                path = resultSet.getString("path");
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FileInputStream fileInputStream = new FileInputStream(path);
        int size =fileInputStream.available();
        byte data[]=new byte[size];
        fileInputStream.read(data);
        fileInputStream.close();
        response.setContentType("image/jpg");
        os.write(data);
        os.flush();
        os.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
