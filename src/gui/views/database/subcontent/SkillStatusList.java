package gui.views.database.subcontent;

import gui.shell.database.SkillStatusShell;
import gui.widgets.SimpleEditableList;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import data.Skill.SkillStatus;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;

public class SkillStatusList extends SimpleEditableList<SkillStatus> {
	
	public SkillStatusList(Composite parent, int style) {
		super(parent, style);
		type = SkillStatus.class;
		setIncludeID(false);
		setShellFactory(new LShellFactory<SkillStatus>() {
			@Override
			public LObjectShell<SkillStatus> createShell(Shell parent) {
				return new SkillStatusShell(parent);
			}
		});
	}

}
