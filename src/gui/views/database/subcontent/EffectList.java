package gui.views.database.subcontent;

import gui.shell.EffectShell;
import gui.views.SimpleEditableList;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import data.Skill.Effect;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;

public class EffectList extends SimpleEditableList<Effect> {
	
	public EffectList(Composite parent, int style) {
		super(parent, style);
		type = Effect.class;
		attributeName = "effects";
		setIncludeID(false);
		setShellFactory(new LShellFactory<Effect>() {
			@Override
			public LObjectShell<Effect> createShell(Shell parent) {
				return new EffectShell(parent);
			}
		});
	}

}
