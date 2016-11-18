package gui.views.database.content;

import gui.Vocab;
import gui.shell.ImageShell;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.AttributeEditor;
import gui.views.database.subcontent.TagEditor;

import java.util.ArrayList;

import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.editor.LComboView;
import lwt.widget.LCheckButton;
import lwt.widget.LSpinner;
import lwt.widget.LStringButton;
import lwt.widget.LStyledText;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import project.ListSerializer;
import project.Project;

public class ItemTab extends DatabaseTab {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ItemTab(Composite parent, int style) {
		super(parent, style);
		
		Label lblDescription = new Label(grpGeneral, SWT.NONE);
		lblDescription.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1));
		lblDescription.setText(Vocab.instance.DESCRIPTION);
		
		LStyledText txtDescription = new LStyledText(grpGeneral, SWT.NONE);
		txtDescription.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		contentEditor.addControl(txtDescription, "description");
		
		Label lblIcon = new Label(grpGeneral, SWT.NONE);
		lblIcon.setText(Vocab.instance.ICON);
		
		Composite compositeIcon = new Composite(grpGeneral, SWT.NONE);
		GridLayout gl_compositeIcon = new GridLayout(2, false);
		gl_compositeIcon.marginWidth = 0;
		gl_compositeIcon.marginHeight = 0;
		compositeIcon.setLayout(gl_compositeIcon);
		compositeIcon.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		LStringButton btnSelect = new LStringButton(compositeIcon, SWT.NONE) {
			@Override
			protected String getImagePath() {
				return Project.current.imagePath();
			}
		};
		
		Label imgIcon = new Label(compositeIcon, SWT.NONE);
		imgIcon.setImage(SWTResourceManager.getImage(ItemTab.class, "/javax/swing/plaf/basic/icons/image-delayed.png"));
		GridData gd_imgIcon = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_imgIcon.widthHint = 48;
		gd_imgIcon.heightHint = 48;
		imgIcon.setLayoutData(gd_imgIcon);
		
		btnSelect.setLabel(imgIcon);
		btnSelect.setShellFactory(new LShellFactory<String>() {
			@Override
			public LObjectShell<String> createShell(Shell parent) {
				return new ImageShell(parent, "Icon");
			}
		});
		
		contentEditor.addControl(btnSelect, "icon");
		
		LCheckButton btnCanSell = new LCheckButton(grpGeneral, SWT.NONE);
		btnCanSell.setText(Vocab.instance.CANSELL);
		contentEditor.addControl(btnCanSell, "canSell");
		
		LCheckButton btnCanUse = new LCheckButton(grpGeneral, SWT.NONE);
		btnCanUse.setText(Vocab.instance.CANUSE);
		contentEditor.addControl(btnCanUse, "canUse");
		
		LCheckButton btnCanConsume = new LCheckButton(grpGeneral, SWT.NONE);
		btnCanConsume.setText(Vocab.instance.CANCONSUME);
		contentEditor.addControl(btnCanConsume, "canConsume");
		
		LCheckButton btnCanEquip = new LCheckButton(grpGeneral, SWT.NONE);
		btnCanEquip.setText(Vocab.instance.CANEQUIP);
		contentEditor.addControl(btnCanEquip, "canEquip");
		
		Label lblSkill = new Label(grpGeneral, SWT.NONE);
		lblSkill.setText(Vocab.instance.ITEMSKILL);
		
		LComboView cmbSkillID = new LComboView(grpGeneral, SWT.NONE) {
			@Override
			public ArrayList<Object> getArray() {
				return Project.current.skills.getList();
			}
		};
		cmbSkillID.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		cmbSkillID.setOptional(true);
		cmbSkillID.setIncludeID(true);
		contentEditor.addControl(cmbSkillID, "skillID");
		
		Label lblStatus = new Label(grpGeneral, SWT.NONE);
		lblStatus.setText(Vocab.instance.ITEMSTATUS);
		
		LComboView cmbStatusID = new LComboView(grpGeneral, SWT.NONE) {
			@Override
			public ArrayList<Object> getArray() {
				return Project.current.status.getList();
			}
		};
		cmbStatusID.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		cmbStatusID.setIncludeID(true);
		cmbStatusID.setOptional(true);
		contentEditor.addControl(cmbStatusID, "statusID");
		
		Label lblPrice = new Label(grpGeneral, SWT.NONE);
		lblPrice.setText(Vocab.instance.PRICE);
		
		LSpinner spnPrice = new LSpinner(grpGeneral, SWT.NONE);
		spnPrice.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		contentEditor.addControl(spnPrice, "price");
		
		Label lblWeight = new Label(grpGeneral, SWT.NONE);
		lblWeight.setText(Vocab.instance.WEIGHT);
		
		LSpinner spnWeight = new LSpinner(grpGeneral, SWT.NONE);
		spnWeight.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		contentEditor.addControl(spnWeight, "weight");
		
		Composite lists = new Composite(contentEditor, SWT.NONE);
		GridLayout gl_lists = new GridLayout(2, false);
		gl_lists.marginWidth = 0;
		gl_lists.marginHeight = 0;
		lists.setLayout(gl_lists);
		lists.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		AttributeEditor attEditor = new AttributeEditor(lists, SWT.NONE);
		attEditor.setColumns(4);
		attEditor.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		contentEditor.addChild(attEditor);
		
		Group grpTags = new Group(lists, SWT.NONE);
		grpTags.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		grpTags.setLayout(new FillLayout());
		grpTags.setText(Vocab.instance.TAGS);
		
		TagEditor tagEditor = new TagEditor(grpTags, SWT.NONE);
		contentEditor.addChild(tagEditor);
	}

	@Override
	protected ListSerializer getSerializer() {
		return Project.current.items;
	}

}
