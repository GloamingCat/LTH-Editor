package gui.views.database.subcontent;

import gui.shell.database.SkillEffectShell;
import gui.widgets.SimpleEditableList;

import data.Skill.Effect;
import lwt.container.LContainer;
import lwt.dialog.LObjectWindow;
import lwt.dialog.LWindow;
import lwt.dialog.LWindowFactory;

public class SkillEffectList extends SimpleEditableList<Effect> {
	
	public SkillEffectList(LContainer parent) {
		super(parent);
		type = Effect.class;
		setIncludeID(false);
		setShellFactory(new LWindowFactory<Effect>() {
			@Override
			public LObjectWindow<Effect> createWindow(LWindow parent) {
				return new SkillEffectShell(parent);
			}
		});
	}

}
