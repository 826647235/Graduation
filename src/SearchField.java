import com.google.gson.Gson;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SearchField{
    public static String searchByField (String SQL) throws Exception {
        Connection connection = ConnectSQL.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL);
        ResultSet resultSet = preparedStatement.executeQuery();
        TranFormat tranFormat = new TranFormat(resultSet);
        connection.close();
        return new Gson().toJson(tranFormat);
    }
}
