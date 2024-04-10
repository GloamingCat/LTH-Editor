package gui.views.database.subcontent;

import gui.shell.database.SkillEffectDialog;
import gui.widgets.SimpleEditableList;

import data.Skill.Effect;
import lui.container.LContainer;
import lui.dialog.LObjectDialog;
import lui.dialog.LWindow;
import lui.dialog.LWindowFactory;

public class SkillEffectList extends SimpleEditableList<Effect> {
	
	public SkillEffectList(LContainer parent) {
		super(parent);
		type = Effect.class;
		setIncludeID(false);
		setShellFactory(new LWindowFactory<Effect>() {
			@Override
			public LObjectDialog<Effect> createWindow(LWindow parent) {
				return new SkillEffectDialog(parent);
			}
		});
	}

}
