import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.CSS;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet(name = "Transaction")
public class Transaction extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        OutputStreamWriter out = new OutputStreamWriter(response.getOutputStream());
        String goodsName = request.getParameter("GoodsName");
        String tag = request.getParameter("Tag");
        String description = request.getParameter("Description");
        String photoNum = request.getParameter("PhotoNum");
        String price = request.getParameter("Price");
        String seller = request.getParameter("Seller");
        String tel = request.getParameter("Tel");
        String date = request.getParameter("Date");
        try {
            Connection connection = ConnectSQL.getConnection();
            String SQL = "insert into transaction (goods, tag, description, photoNum, price, seller, tel, date) values(?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, goodsName);
            preparedStatement.setString(2, tag);
            preparedStatement.setString(3, description);
            preparedStatement.setInt(4, Integer.parseInt(photoNum));
            preparedStatement.setDouble(5, Double.parseDouble(price));
            preparedStatement.setString(6, seller);
            preparedStatement.setString(7, tel);
            preparedStatement.setString(8, date);
            preparedStatement.executeUpdate();
            connection.close();
            out.write("success");

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
