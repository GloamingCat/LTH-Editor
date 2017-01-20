package gui.shell;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import project.Project;

public class ImageShell extends FileShell<String> {

	private Label label;
	
	/**
	 * @wbp.parser.constructor
	 */
	public ImageShell(Shell parent) {
		this(parent, "", true);
	}
	
	public ImageShell(Shell parent, String folder, boolean optional) {
		super(parent, folder, optional);

		list.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				int i = list.getSelectionIndex();
				if (i >= 0) {
					label.setText(folder + "\\" + list.getItem(i));
					label.setImage(SWTResourceManager.getImage(rootPath() + label.getText()));
				}
			}
		});
		
		label = new Label(sashForm, SWT.NONE);
		label.setImage(SWTResourceManager.getImage(rootPath() + result));
		
		sashForm.setWeights(new int[] {1, 1});
	}
	
	public void open(String initial) {
		super.open(initial);
		int i = indexOf(initial);
		list.select(i);
	}
	
	@Override
	protected String createResult(String initial) {
		int i = list.getSelectionIndex();
		if (i >= 0) {
			String newValue = folder + "\\" + list.getItem(i);
			if (newValue.equals(initial)) {
				return null;
			} else {
				return newValue;
			}
		} else {
			return null;
		}
	}

	protected boolean isValidFile(File entry) {
		try {
		    Image image = ImageIO.read(entry);
		    if (image != null) {
		    	image.flush();
		    } else {
		    	return false;
		    }
		    return true;
		} catch(IOException ex) {
		    return false;
		}
	}
	
	protected String rootPath() {
		return Project.current.imagePath();
	}

}
