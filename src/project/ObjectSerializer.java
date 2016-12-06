package project;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lwt.dataserialization.LObjectSerializer;

public class ObjectSerializer extends LObjectSerializer<Object> {
	
	private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	public ObjectSerializer(String path, Class<?> type) {
		super(path + ".json", type);
	}

	@Override
	protected byte[] toByteArray(Object data) {
		return gson.toJson(data, type).getBytes();
	}

	@Override
	protected Object fromByteArray(byte[] bytes) {
		return gson.fromJson(new String(bytes), type);
	}
	
}
