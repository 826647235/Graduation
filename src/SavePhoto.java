import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

@WebServlet(name = "SavePhoto")
public class SavePhoto extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        OutputStreamWriter out = new OutputStreamWriter(response.getOutputStream());
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (isMultipart) {
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            try {
                Connection connection = ConnectSQL.getConnection();
                PreparedStatement preparedStatement;
                String fileName;
                String type;
                String id;
                int position = 1;
                List<FileItem> items = upload.parseRequest(request);
                for (FileItem fileItem : items) {
                     fileName = fileItem.getName();
                    String[] message = fileName.split("_");
                    type = message[0];
                    id = message[1];
                    File file = new File("C:\\picture\\" + type, fileName);
                    fileItem.write(file);
                    String SQL = "insert into " + type + "Photo values (?, ?, ?)";
                    preparedStatement = connection.prepareStatement(SQL);
                    preparedStatement.setInt(1, Integer.parseInt(id));
                    preparedStatement.setInt(2, position);
                    preparedStatement.setString(3, file.getPath());
                    preparedStatement.executeUpdate();
                    position++;
                }
                connection.close();
                out.write("success");
            } catch (Exception e) {
                e.printStackTrace();
                out.write("false");
            }
            out.flush();
            out.close();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
