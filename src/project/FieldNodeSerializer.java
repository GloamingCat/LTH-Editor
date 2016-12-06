package project;

import lwt.dataserialization.LObjectSerializer;

import com.google.gson.Gson;

import data.Field;

public class FieldNodeSerializer extends LObjectSerializer<Field> {

	private static Gson uglyGson = new Gson();
	//private static Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
	
	public FieldNodeSerializer() {
		super("", Field.class);
	}

	public boolean saveField(Field field) {
		path = Project.current.dataPath() + "/fields/" + field.id + ".json";
		setData(field);
		return save();
	}
	
	public Field loadField(int id) {
		path = Project.current.dataPath() + "/fields/" + id + ".json";
		if (load()) {
			return data;
		} else {
			return null;
		}
	}
	
	/*
	@Override
	protected byte[] toByteArray(Field field) {
		JsonObject obj = new JsonObject();
		JsonElement prefs = prettyGson.toJsonTree(field.prefs, Prefs.class);
		obj.add("id", prettyGson.toJsonTree(field.id));
		obj.add("sizeX", prettyGson.toJsonTree(field.sizeX));
		obj.add("sizeY", prettyGson.toJsonTree(field.sizeY));
		obj.add("prefs", prefs);
		JsonArray arr = new JsonArray();
		for(Layer l : field.layers) {
			JsonObject objl = new JsonObject();
			JsonElement info = prettyGson.toJsonTree(l.info, Layer.Info.class);
			objl.add("info", info);
			objl.addProperty("visible", l.visible);
			JsonArray grid = new JsonArray();
			for(int i = 0; i < field.sizeX; i++) {
				JsonArray line = new JsonArray();
				for(int j = 0; j < field.sizeY; j++) {
					line.add(uglyGson.toJsonTree(l.grid[i][j]));
				}
				grid.add(uglyGson.toJsonTree(line));
			}
			objl.add("grid", prettyGson.toJsonTree(grid));
			arr.add(prettyGson.toJsonTree(objl));
		}
		obj.add("layers", arr);
		return obj.toString().getBytes();
	}*/
	
	@Override
	protected byte[] toByteArray(Field data) {
		return uglyGson.toJson(data, type).getBytes();
	}

	@Override
	protected Field fromByteArray(byte[] bytes) {
		return uglyGson.fromJson(new String(bytes), Field.class);
	}
	
}
