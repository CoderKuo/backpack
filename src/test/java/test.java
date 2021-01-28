import com.alibaba.fastjson.JSONObject;
import com.dakuo.backpack.service.version;

import java.util.ArrayList;
import java.util.List;

public class test {
    public static void main(String[] args) {
        JSONObject jsonObject = new JSONObject(false);
        JSONObject jsonObject1 = new JSONObject(false);
        jsonObject1.put("2",2);
        jsonObject.put("1",jsonObject1);

    }
}
