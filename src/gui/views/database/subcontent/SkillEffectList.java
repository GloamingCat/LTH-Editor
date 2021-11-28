package gui.views.database.subcontent;

import gui.shell.database.SkillEffectShell;
import gui.widgets.SimpleEditableList;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import data.Skill.Effect;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;

public class SkillEffectList extends SimpleEditableList<Effect> {
	
	public SkillEffectList(Composite parent, int style) {
		super(parent, style);
		type = Effect.class;
		setIncludeID(false);
		setShellFactory(new LShellFactory<Effect>() {
			@Override
			public LObjectShell<Effect> createShell(Shell parent) {
				return new SkillEffectShell(parent);
			}
		});
	}

}
