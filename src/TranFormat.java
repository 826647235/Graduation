import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TranFormat {
    List<Tran> tranList = new ArrayList<>();

    class Tran {
        int id = 0;
        String goodName = "";
        String tag = "";
        String description = "";
        int photoNum = 0;
        double price = 0;
        String seller = "";
        String tel = "";
        String date = "";
        int isFinish = 0;
        int visitTime = 0;
    }

    public TranFormat(ResultSet resultSet, int quantity) throws Exception {
        int num = 0;
        while(resultSet.next() && num < quantity) {
            Tran tran = new Tran();
            tran.id = resultSet.getInt("id");
            tran.goodName = resultSet.getString("goods");
            tran.tag = resultSet.getString("tag");
            tran.description = resultSet.getString("description");
            tran.photoNum = resultSet.getInt("photoNum");
            tran.price = resultSet.getDouble("price");
            tran.seller = resultSet.getString("seller");
            tran.tel = resultSet.getString("tel");
            tran.date = resultSet.getString("date");
            tran.isFinish = resultSet.getInt("isFinish");
            tran.visitTime = resultSet.getInt("visitTime");
            tranList.add(tran);
            num++;
        }
    }
}
