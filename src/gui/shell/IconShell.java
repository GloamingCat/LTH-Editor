package gui.shell;

import org.eclipse.swt.widgets.Shell;

public class IconShell extends ImageShell {

	public IconShell(Shell parent) {
		super(parent);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getFolder() {
		return "Icon";
	}
	
	protected void checkSubclass() { }

}
