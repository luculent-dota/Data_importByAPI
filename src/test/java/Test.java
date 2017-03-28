import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

public class Test {

	public static void main(String[] args) {
		Address add = new Address("12055","就哦打飞机的");
		Address add1 = new Address("122055","就哦打飞机的");
		List<Address> list = new ArrayList<Address>();
		list.add(add);
		list.add(add1);
		System.out.println(JSONObject.toJSON(list).toString());
		
		
	}
}
