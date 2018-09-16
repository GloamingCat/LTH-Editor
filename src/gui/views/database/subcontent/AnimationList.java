package gui.views.database.subcontent;

import java.util.HashMap;

import gui.shell.NodeShell;
import gui.views.SimpleEditableList;

import org.eclipse.swt.widgets.Composite;

import project.Project;
import data.subcontent.Node;
import lwt.dataestructure.LDataList;
import lwt.dataestructure.LDataTree;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;

public class AnimationList extends SimpleEditableList<Node> {

	public String folderName = "Default";
	
	public AnimationList(Composite parent, int style) {
		super(parent, style);
		type = Node.class;
		setIncludeID(true);
		setShellFactory(new LShellFactory<Node>() {
			@Override
			public LObjectShell<Node> createShell(
					org.eclipse.swt.widgets.Shell parent) {
				return new NodeShell(parent) {
					public LDataTree<Object> getTree() {
						return getDataArray();
					}
				};
			}
		});
	}
	
	protected LDataTree<Object> getDataArray() { 
		return Project.current.animations.getTree(); 
	}
	
	@Override
	public void setObject(Object object) {
		if (object == null || attributeName == null || 
				attributeName.isEmpty()) {
			super.setObject(object);
			return;
		}
		Object value = getFieldValue(object, attributeName);
		if (folderName != null) {
			@SuppressWarnings("unchecked")
			HashMap<String, LDataList<Node>> map = (HashMap<String, LDataList<Node>>) value;
			value = map.get(folderName);
			if (value == null) {
				map.put(folderName, new LDataList<Node>());
				value = map.get(folderName);
			}
		}
		String name = attributeName;
		attributeName = null;
		super.setObject(value);
		attributeName = name;
	}

}
