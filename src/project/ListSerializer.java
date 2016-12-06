package project;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import lwt.dataestructure.LDataList;
import lwt.dataserialization.LDefaultSerializer;

public class ListSerializer extends LDefaultSerializer {

	private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private static JsonParser parser = new JsonParser();
	private LDataList<Object> list;
	
	public ListSerializer(String path, Class<?> type) {
		super(path + ".json", type);
	}
	
	public LDataList<Object> getList() {
		return list;
	}

	@Override
	protected byte[] serialize() {
		JsonArray array = new JsonArray();
		for (Object obj : this.list) {
			JsonElement je = gson.toJsonTree(obj, type);
			array.add(je);
		}
		return gson.toJson(array).getBytes();
	}

	@Override
	protected void deserialize(byte[] bytes) {
		String string = new String(bytes);
		JsonArray array = parser.parse(string).getAsJsonArray();
		list = new LDataList<>();
		for (JsonElement je : array) {
			Object obj = gson.fromJson(je, type);
			list.add(obj);
		}
	}

}
