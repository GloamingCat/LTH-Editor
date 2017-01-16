package gui.shell;

import gui.Vocab;
import gui.views.database.subcontent.TagList;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

import lwt.dataestructure.LDataList;
import lwt.dialog.LObjectShell;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

import data.Layer.Info;
import data.Tag;

import org.eclipse.swt.layout.FillLayout;

public class LayerShell extends LObjectShell<Info> {
	
	private Text txtName;
	private Combo cmbType;
	private Spinner spnHeight;
	
	protected TagList lstTags;
	protected LDataList<Tag> tags;

	public LayerShell(Shell parent) {
		super(parent);
		GridData gridData = (GridData) content.getLayoutData();
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessVerticalSpace = true;

		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		content.setLayout(gridLayout);
		
		Group grpGeneral = new Group(content, SWT.NONE);
		grpGeneral.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpGeneral.setText(Vocab.instance.GENERAL);
		GridLayout gl_grpGeneral = new GridLayout(2, false);
		gl_grpGeneral.marginWidth = 0;
		gl_grpGeneral.marginHeight = 0;
		grpGeneral.setLayout(gl_grpGeneral);
		
		Label lblName = new Label(grpGeneral, SWT.NONE);
		lblName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblName.setText(Vocab.instance.NAME);
		
		txtName = new Text(grpGeneral, SWT.BORDER);
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblType = new Label(grpGeneral, SWT.NONE);
		lblType.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblType.setText(Vocab.instance.TYPE);
		
		cmbType = new Combo(grpGeneral, SWT.BORDER | SWT.READ_ONLY);
		cmbType.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		cmbType.setItems(new String[] { Vocab.instance.TERRAIN, Vocab.instance.OBSTACLE, 
				Vocab.instance.CHARACTER, Vocab.instance.REGION,
				Vocab.instance.BATTLERTYPE, Vocab.instance.PARTY});
		
		Label lblHeight = new Label(grpGeneral, SWT.NONE);
		lblHeight.setText(Vocab.instance.HEIGHT);
		lblHeight.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		spnHeight = new Spinner(grpGeneral, SWT.BORDER);
		spnHeight.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Group grpTags = new Group(content, SWT.NONE);
		grpTags.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpTags.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpTags.setText(Vocab.instance.TAGS);
		
		lstTags = new TagList(grpTags, SWT.NONE) {
			public LDataList<Tag> getDataCollection() {
				return tags;
			}
		};
		
		pack();
	}
	
	public void open(Info initial) {
		super.open(initial);
		cmbType.select(initial.type);
		spnHeight.setSelection(initial.height);
		txtName.setText(initial.name);
		
		tags = new LDataList<Tag>();
		for(Tag tag : initial.tags) {
			tags.add(new Tag(tag));
		}
		lstTags.onVisible();
	}

	@Override
	protected Info createResult(Info initial) {
		if (txtName.getText().equals(initial.name) && cmbType.getSelectionIndex() == initial.type &&
				tags.equals(initial.tags) && spnHeight.getSelection() == initial.height) {
			return null;
		}
		Info p = new Info();
		p.name = txtName.getText();
		p.type = cmbType.getSelectionIndex();
		p.height = spnHeight.getSelection();
		p.tags = tags;
		return p;
	}
	
}
