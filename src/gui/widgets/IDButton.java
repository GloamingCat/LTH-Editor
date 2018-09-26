package gui.widgets;

import gui.shell.IDShell;
import lwt.dataestructure.LDataTree;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.widget.LObjectButton;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public abstract class IDButton extends LObjectButton<Integer> {
	
	protected Text txtName;
	public boolean optional = false;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public IDButton(Composite parent, int style) {
		super(parent, style);
		setShellFactory(new LShellFactory<Integer>() {
			@Override
			public LObjectShell<Integer> createShell(Shell parent) {
				IDShell shell = new IDShell(parent) {
					protected LDataTree<Object> getTree() { 
						return getDataTree(); 
					}
				};
				shell.setOptional(optional);
				return shell;
			}
		});
	}
	
	public abstract LDataTree<Object> getDataTree();
	
	public void setNameText(Text text) {
		txtName = text;
	}

	@Override
	public void setValue(Object value) {
		if (value != null) {
			button.setEnabled(true);
			Integer id = (Integer) value;
			if (txtName != null) {
				if (id < 0)
					txtName.setText("");
				else {
					Object obj = getDataTree().get(id);
					txtName.setText(obj.toString());
				}
			}
			currentValue = id;
		} else {
			button.setEnabled(false);
			if (txtName != null) {
				txtName.setText("");
			}
			currentValue = null;
		}
	}

}