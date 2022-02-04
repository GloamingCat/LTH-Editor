package gui.widgets;

import gui.shell.AudioShell;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.widget.LObjectButton;
import lwt.widget.LText;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;


import data.subcontent.Audio;

public class AudioButton extends LObjectButton<Audio> {
	
	private LText text;
	
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public AudioButton(Composite parent, int optional) {
		super(parent);
		setShellFactory(new LShellFactory<Audio>() {
			@Override
			public LObjectShell<Audio> createShell(Shell parent) {
				return new AudioShell(parent, optional);
			}
		});
	}
	
	public void setTextWidget(LText text) {
		this.text = text;
	}
	
	@Override
	public void setValue(Object value) {
		if (value != null) {
			button.setEnabled(true);
			Audio s = (Audio) value;
			if (text != null) {
				text.setValue(s.toString());
			}
			currentValue = s;
		} else {
			button.setEnabled(false);
			if (text != null) {
				text.setValue("");
			}
			currentValue = null;
		}
	}

}