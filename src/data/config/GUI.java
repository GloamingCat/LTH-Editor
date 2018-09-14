package data.config;

import lwt.dataestructure.LDataList;

import org.eclipse.swt.graphics.Rectangle;

import data.subcontent.Quad;
import data.subcontent.Tag;

public class GUI {
	
	public static class Prefs {
		public String name = "New GUI";
		public String script = "GUI";
		public String background = "";
		public LDataList<Tag> tags = new LDataList<>();
	}
	
	public static class Component {
		public String name = "New Component";
		public Rectangle bounds = new Rectangle(0, 0, 10, 10);
		public LDataList<Component> children = new LDataList<>();

		public Button buttonData = null;
		public Quad imageData = null;
		public String textData = null;
	}
	
	public static class Window {
		public String name = "New Window";
		public Rectangle bounds = new Rectangle(0, 0, 10, 10);
		public LDataList<Component> children = new LDataList<>();
		
		public boolean vertical = false;
		public int itensPerLine = 1;
		public ComponentGenerator buttonGenerator = null;
	}
	
	public static class Button {
		public String script = "Button";
		public String okMethod = "onOK";
		public String cancelMethod = "onCancel";
		public String moveMethod = "onMove";
	}
	
	public static class ComponentGenerator {
		public String sizeMethod = "getComponentCount";
		public String setMethod = "setComponentProperties";
	}
	
	public Prefs prefs = new Prefs();
	public LDataList<Window> windows = new LDataList<>();
	
	public String toString() {
		return prefs.name;
	}
	
}
