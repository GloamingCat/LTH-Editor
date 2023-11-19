package gui.views.database.subcontent;

import gui.shell.database.SkillEffectShell;
import gui.widgets.SimpleEditableList;

import data.Skill.Effect;
import lwt.container.LContainer;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShell;
import lwt.dialog.LShellFactory;

public class SkillEffectList extends SimpleEditableList<Effect> {
	
	public SkillEffectList(LContainer parent) {
		super(parent);
		type = Effect.class;
		setIncludeID(false);
		setShellFactory(new LShellFactory<Effect>() {
			@Override
			public LObjectShell<Effect> createShell(LShell parent) {
				return new SkillEffectShell(parent);
			}
		});
	}

}
