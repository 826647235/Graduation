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
    }

    public TranFormat(ResultSet resultSet) throws Exception {
        addTran(resultSet);
    }

    private void addTran(ResultSet resultSet) throws Exception {
        while(resultSet.next()) {
            Tran tran = new Tran();
            tran.id = resultSet.getInt("id");
            tran.goodName = resultSet.getString("goods");
            tran.tag = resultSet.getString("tag");
            tran.description = resultSet.getString("description");
            tran.photoNum = resultSet.getInt("photoNum");
            tran.price = resultSet.getDouble("pirce");
            tran.seller = resultSet.getString("seller");
            tran.tel = resultSet.getString("tel");
            tran.date = resultSet.getString("date");
            tranList.add(tran);
        }
    }
}
