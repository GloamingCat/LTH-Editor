package gui.shell.field;

import gui.Vocab;
import gui.views.ImageButton;
import gui.views.database.subcontent.TagList;
import gui.views.database.subcontent.TransitionList;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import lwt.dataestructure.LDataList;
import lwt.dialog.LObjectShell;
import lwt.widget.LCombo;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

import project.Project;
import data.Config;
import data.Field.Prefs;
import data.Tag;
import data.Transition;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Spinner;

public class FieldShell extends LObjectShell<Prefs> {
	
	private Text txtName;
	private Text txtBG;
	private LCombo cmbTileset;
	private LCombo cmbRegion;
	private ImageButton btnBG;
	
	protected TagList lstTags;
	protected TransitionList lstTransitions;
	
	protected LDataList<Tag> tags;
	protected LDataList<Transition> transitions;
	private Spinner spnParties;

	public FieldShell(Shell parent) {
		super(parent);
		GridData gridData = (GridData) content.getLayoutData();
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessVerticalSpace = true;
		setMinimumSize(new Point(400, 300));
		
		GridLayout gridLayout = new GridLayout(2, false);
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		content.setLayout(gridLayout);
		
		Group grpGeneral = new Group(content, SWT.NONE);
		grpGeneral.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
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
		
		Label lblBG = new Label(grpGeneral, SWT.NONE);
		lblBG.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblBG.setText(Vocab.instance.BACKGROUND);
		
		Composite compBG = new Composite(grpGeneral, SWT.NONE);
		GridLayout gl_compBG = new GridLayout(2, false);
		gl_compBG.marginWidth = 0;
		gl_compBG.marginHeight = 0;
		compBG.setLayout(gl_compBG);
		compBG.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		txtBG = new Text(compBG, SWT.BORDER | SWT.READ_ONLY);
		txtBG.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		btnBG = new ImageButton(compBG, SWT.NONE);
		btnBG.setFolder("Background");
		btnBG.optional = true;
		btnBG.setText(txtBG);
		
		Label lblTileset = new Label(grpGeneral, SWT.NONE);
		lblTileset.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblTileset.setText(Vocab.instance.TILESET);
		
		cmbTileset = new LCombo(grpGeneral, SWT.READ_ONLY);
		cmbTileset.setIncludeID(true);
		cmbTileset.setOptional(false);
		cmbTileset.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblDefaultRegion = new Label(grpGeneral, SWT.NONE);
		lblDefaultRegion.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblDefaultRegion.setText(Vocab.instance.DEFAULTREGION);
		
		cmbRegion = new LCombo(grpGeneral, SWT.READ_ONLY);
		cmbRegion.setIncludeID(true);
		cmbRegion.setOptional(true);
		cmbRegion.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblPartyCount = new Label(grpGeneral, SWT.NONE);
		lblPartyCount.setText(Vocab.instance.PARTYCOUNT);
		
		spnParties = new Spinner(grpGeneral, SWT.BORDER);
		spnParties.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Group grpTags = new Group(content, SWT.NONE);
		grpTags.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpTags.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 2));
		grpTags.setText(Vocab.instance.TAGS);
		
		lstTags = new TagList(grpTags, SWT.NONE) {
			public LDataList<Tag> getDataCollection() {
				return tags;
			}
		};
		
		Composite bottom = new Composite(content, SWT.NONE);
		bottom.setLayout(new FillLayout(SWT.HORIZONTAL));
		bottom.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		Group grpScript = new Group(bottom, SWT.NONE);
		grpScript.setText(Vocab.instance.STARTLISTENER);
		
		GridLayout gl_scriptComp = new GridLayout(2, false);
		gl_scriptComp.marginWidth = 0;
		gl_scriptComp.marginHeight = 0;
		grpScript.setLayout(gl_scriptComp);
		
		Group grpTransitions = new Group(bottom, SWT.NONE);
		grpTransitions.setText(Vocab.instance.TRANSITIONS);
		grpTransitions.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		lstTransitions = new TransitionList(grpTransitions, SWT.NONE) {
			public LDataList<Transition> getDataCollection() {
				return transitions;
			}
		};
		
		pack();
		
	}
	
	public void open(Prefs initial) {
		super.open(initial);
		Config conf = (Config) Project.current.config.getData();
		//cmbTileset.setItems(Project.current.tilesets.getList()); TODO
		cmbTileset.setValue(initial.tilesetID);
		cmbRegion.setItems(conf.regions);
		cmbRegion.setValue(initial.defaultRegion);
		txtName.setText(initial.name);
		btnBG.setValue(initial.background);
		spnParties.setSelection(initial.partyCount);
		
		tags = new LDataList<Tag>();
		for(Tag tag : initial.tags) {
			tags.add(new Tag(tag));
		}
		lstTags.onVisible();
		
		transitions = new LDataList<Transition>();
		for(Transition t : initial.transitions) {
			transitions.add(t.clone());
		}
		lstTransitions.onVisible();
	}

	@Override
	protected Prefs createResult(Prefs initial) {
		if (txtName.getText().equals(initial.name) && 
				txtBG.getText().equals(initial.background) &&
				cmbTileset.getSelectionIndex() == initial.tilesetID && 
				cmbRegion.getSelectionIndex() == initial.defaultRegion &&
				spnParties.getSelection() == initial.partyCount &&
				tags.equals(initial.tags) && 
				transitions.equals(initial.transitions)) {
			return null;
		}
		Prefs p = new Prefs();
		p.name = txtName.getText();
		p.background = txtBG.getText();
		p.defaultRegion = cmbRegion.getSelectionIndex();
		p.tilesetID = cmbTileset.getSelectionIndex();
		p.partyCount = spnParties.getSelection();
		p.tags = tags;
		p.transitions = transitions;
		return p;
	}
	
}
