package gui.shell;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import project.Project;
import lwt.dialog.LObjectShell;

public abstract class ImageShell extends LObjectShell<String> {

	private List list;
	private Label label;
	
	public ImageShell(Shell parent) {
		super(parent);
		
		SashForm sashForm = new SashForm(content, SWT.NONE);
		sashForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		list = new List(sashForm, SWT.BORDER | SWT.V_SCROLL);
		list.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				int i = list.getSelectionIndex();
				if (i >= 0) {
					label.setText(getFolder() + "/" + list.getItem(i));
					label.setImage(SWTResourceManager.getImage(Project.current.imagePath() + label.getText()));
				}
			}
		});
		list.setItems(getItems(getFolder() + "/"));
		
		label = new Label(sashForm, SWT.NONE);
		label.setImage(SWTResourceManager.getImage(Project.current.imagePath() + result));
		
		sashForm.setWeights(new int[] {1, 1});
	}

	@Override
	protected String createResult(String initial) {
		int i = list.getSelectionIndex();
		if (i >= 0) {
			String newValue = getFolder() + "/" + list.getItem(i);
			if (newValue.equals(initial)) {
				return null;
			} else {
				return newValue;
			}
		} else {
			return null;
		}
	}
	
	protected abstract String getFolder();
	
	private String[] getItems(String folder) {
		ArrayList<String> list = new ArrayList<String>();
		readFiles(folder, list, "");
		String[] array = new String[list.size()];
		for(int i = 0; i < array.length; i++) {
			array[i] = list.get(i);
		}
		return array;
	}
	
	private void readFiles(String folder, ArrayList<String> items, String path) {
		File f = new File(Project.current.imagePath() + folder + "/" + path);
		if (!f.exists())
			return;
		for (File entry : f.listFiles()) {
			if (entry.isDirectory()) {
				readFiles(folder, items, path + entry.getName() + "/");
			} else {
				try {
				    Image image = ImageIO.read(entry);
				    if (image == null) {
				    	continue;
				    }
				    image.flush();
				} catch(IOException ex) {
				    continue;
				}
				items.add(path + entry.getName());
			}
		}
	}

}
