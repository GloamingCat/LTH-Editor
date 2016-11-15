package project;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lwt.dataserialization.LTreeSerializer;

public abstract class TreeSerializer<T> extends LTreeSerializer<T> {

	private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	public TreeSerializer(String path, Class<?> type) {
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
