import java.sql.ResultSet;

public class Account {
    String weChat = "";
    String account = "";
    String password = "";
    String name = "";
    String college = "";

    public Account(ResultSet resultSet) throws Exception{
        weChat = resultSet.getString("wechat");
        account = resultSet.getString("Account");
        password = resultSet.getString("password");
        name = resultSet.getString("name");
        college = resultSet.getString("college");
    }

    public Account(String weChat, String account, String password, String name, String college) {
        this.weChat = weChat;
        this.account = account;
        this.password = password;
        this.name = name;
        this.college = college;
    }
}
