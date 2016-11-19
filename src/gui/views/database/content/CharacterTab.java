package gui.views.database.content;

import java.util.ArrayList;

import gui.Vocab;
import gui.views.database.DatabaseTab;
import gui.views.database.subcontent.NodeList;
import gui.views.database.subcontent.TagList;
import lwt.event.LSelectionEvent;
import lwt.event.listener.LSelectionListener;
import lwt.widget.LSpinner;
import lwt.widget.LStringButton;
import lwt.widget.LTextBox;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import data.Animation;
import data.Node;
import project.ListSerializer;
import project.Project;

public class CharacterTab extends DatabaseTab {

	public CharacterTab(Composite parent, int style) {
		super(parent, style);
		GridLayout gridLayout = (GridLayout) contentEditor.getLayout();
		gridLayout.verticalSpacing = 0;
		
		Composite stuff = new Composite(grpGeneral, SWT.NONE);
		stuff.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 3));
		GridLayout gl_stuff = new GridLayout(4, false);
		gl_stuff.marginHeight = 0;
		gl_stuff.marginWidth = 0;
		stuff.setLayout(gl_stuff);
		
		Label lblColliderSize = new Label(stuff, SWT.NONE);
		lblColliderSize.setText(Vocab.instance.COLLIDERSIZE);
		
		LSpinner spnSize = new LSpinner(stuff, SWT.NONE);
		spnSize.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnSize, "colliderSize");
		
		Label lblColliderHeight = new Label(stuff, SWT.NONE);
		lblColliderHeight.setText(Vocab.instance.COLLIDERHEIGHT);
		
		LSpinner spnHeight = new LSpinner(stuff, SWT.NONE);
		spnHeight.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnHeight, "colliderHeight");

		Label lblEventsheet = new Label(stuff, SWT.NONE);
		lblEventsheet.setText(Vocab.instance.EVENTSHEET);
		
		Text txtEventsheet = new Text(stuff, SWT.BORDER | SWT.READ_ONLY);
		txtEventsheet.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		LStringButton btnSelect = new LStringButton(stuff, SWT.NONE);
		btnSelect.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		addScriptButton(btnSelect, txtEventsheet, "eventsheet", "eventsheet");
		
		Label lblParams = new Label(stuff, SWT.NONE);
		lblParams.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		lblParams.setText(Vocab.instance.PARAM);
		
		LTextBox txtParam = new LTextBox(stuff, SWT.NONE);
		GridData gd_txtParam = new GridData(SWT.FILL, SWT.TOP, true, true, 3, 1);
		gd_txtParam.heightHint = 128;
		txtParam.setLayoutData(gd_txtParam);
		addControl(txtParam, "param");
		
		SashForm bottom = new SashForm(contentEditor, SWT.NONE);
		bottom.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		Group grpAnimations = new Group(bottom, SWT.NONE);
		GridLayout gl_grpAnimations = new GridLayout(2, false);
		gl_grpAnimations.verticalSpacing = 0;
		gl_grpAnimations.marginHeight = 0;
		gl_grpAnimations.marginWidth = 0;
		grpAnimations.setLayout(gl_grpAnimations);
		grpAnimations.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpAnimations.setText(Vocab.instance.ANIMATIONS);
		
		NodeList lstAnim = new NodeList(grpAnimations, SWT.NONE) {
			@Override
			protected ArrayList<?> comboArray() {
				return Project.current.animCharacter.getList();
			}
			@Override
			protected String attributeName() {
				return "animations";
			}
		};
		lstAnim.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		addChild(lstAnim);
		
		Label image = new Label(grpAnimations, SWT.NONE);
		GridData gd_image = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_image.widthHint = 128;
		image.setLayoutData(gd_image);
		lstAnim.collection.addSelectionListener(new LSelectionListener() {
			@Override
			public void onSelect(LSelectionEvent event) {
				Node node = (Node) event.data;
				Animation anim = (Animation) Project.current.animCharacter.getList().get(node.id);
				image.setImage(SWTResourceManager.getImage(Project.current.imagePath() + anim.imagePath));
			}
		});
		
		Group grpTags = new Group(bottom, SWT.NONE);
		grpTags.setLayout(new FillLayout());
		grpTags.setText(Vocab.instance.TAGS);
		
		TagList tagEditor = new TagList(grpTags, SWT.NONE);
		addChild(tagEditor);
		
		bottom.setWeights(new int[] {2, 1});
	}

	@Override
	protected ListSerializer getSerializer() {
		return Project.current.characters;
	}


}
