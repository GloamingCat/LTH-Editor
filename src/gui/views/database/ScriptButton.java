package gui.views.database;

import gui.shell.ScriptShell;
import lwt.LVocab;
import lwt.action.LControlAction;
import lwt.dialog.LObjectDialog;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.event.LControlEvent;
import lwt.widget.LControl;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.SelectionEvent;

import data.Script;

public class ScriptButton extends LControl {
	
	private Button button;
	private LObjectDialog<Script> dialog;
	private String folder;
	
	private Text pathText;
	private StyledText paramText;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ScriptButton(Composite parent, int style) {
		super(parent, style);
		LControl self = this;
		button = new Button(this, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Script newValue = dialog.open((Script) currentValue);
				if (newValue != null) {
					LControlEvent event = new LControlEvent(currentValue, newValue);
					newAction(new LControlAction(self, event));
					notifyListeners(event);
					setValue(newValue);
				}
			}
		});
		button.setText(LVocab.instance.SELECT);
		
		LObjectDialog<Script> dialog = new LObjectDialog<Script>(getShell(), getShell().getStyle());
		dialog.setFactory(new LShellFactory<Script>() {
			@Override
			public LObjectShell<Script> createShell(Shell parent) {
				return new ScriptShell(parent, folder);
			}
		});
		this.dialog = dialog;
		
	}
	
	public void setPathText(Text text) {
		pathText = text;
	}
	
	public void setParamText(StyledText text) {
		paramText = text;
	}
	
	public void setFolder(String folder) {
		this.folder = folder;
	}

	@Override
	public void setValue(Object value) {
		if (value != null) {
			button.setEnabled(true);
			Script s = (Script) value;
			if (pathText != null) {
				pathText.setText(s.path);
			}
			if (paramText != null) {
				paramText.setText(s.param);
			}
		} else {
			button.setEnabled(false);
			if (pathText != null) {
				pathText.setText("");
			}
			if (paramText != null) {
				paramText.setText("");
			}
		}
		currentValue = value;
	}

}
