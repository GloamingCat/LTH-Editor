package data;

import lwt.dataestructure.LDataList;

public class DialogNode extends Node {
	
	public static class Dialog {
		public Quad portrait = new Quad();
		public String name = "John Smith";
		public String msg = "Hi, how are you?";
	}
	
	public LDataList<Dialog> dialogs = new LDataList<>();
	
}
