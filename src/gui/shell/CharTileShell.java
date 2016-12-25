package gui.shell;

import gui.Vocab;

import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

import lwt.dialog.LObjectShell;
import lwt.event.LControlEvent;
import lwt.event.listener.LControlListener;
import lwt.widget.LCombo;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

import project.Project;
import data.GameCharacter;
import data.Tileset.CharTile;

public class CharTileShell extends LObjectShell<CharTile> {
	
	private LCombo cmbID;
	private LCombo cmbAnim;
	private LCombo cmbDir;
	
	private LCombo cmbStart;
	private LCombo cmbCollision;
	private LCombo cmbInteract;

	public CharTileShell(Shell parent) {
		super(parent);
		
		GridLayout gridLayout = new GridLayout(2, true);
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		content.setLayout(gridLayout);
		
		Group grpGeneral = new Group(content, SWT.NONE);
		grpGeneral.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		grpGeneral.setText(Vocab.instance.GENERAL);
		grpGeneral.setLayout(new GridLayout(2, false));
		
		Label lblID = new Label(grpGeneral, SWT.NONE);
		lblID.setText(Vocab.instance.ID);
		
		cmbID = new LCombo(grpGeneral, SWT.READ_ONLY);
		cmbID.addModifyListener(new LControlListener<Integer>() {
			@Override
			public void onModify(LControlEvent<Integer> event) {
				int id = cmbID.getSelectionIndex();
				setCharID(id);
			}
		});
		cmbID.setIncludeID(true);
		cmbID.setOptional(false);
		cmbID.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblAnim = new Label(grpGeneral, SWT.NONE);
		lblAnim.setText(Vocab.instance.ANIMATION);
		
		cmbAnim = new LCombo(grpGeneral, SWT.READ_ONLY);
		cmbAnim.setIncludeID(false);
		cmbAnim.setOptional(false);
		cmbAnim.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblDir = new Label(grpGeneral, SWT.NONE);
		lblDir.setText(Vocab.instance.DIRECTION);
		
		cmbDir = new LCombo(grpGeneral, SWT.READ_ONLY);
		cmbDir.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		cmbDir.setIncludeID(false);
		cmbDir.setOptional(false);
		cmbDir.setItems(new String[] {"0°", "45°", "90°", "135°", "180°", "225°", "270°", "315°"});
		
		Group grpScript = new Group(content, SWT.NONE);
		grpScript.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		grpScript.setText(Vocab.instance.SCRIPT);
		grpScript.setLayout(new GridLayout(2, false));
		
		Label lblStartScript = new Label(grpScript, SWT.NONE);
		lblStartScript.setText(Vocab.instance.STARTLISTENER);
		
		cmbStart = new LCombo(grpScript, SWT.READ_ONLY);
		cmbStart.setIncludeID(false);
		cmbStart.setOptional(true);
		cmbStart.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblCollisionScript = new Label(grpScript, SWT.NONE);
		lblCollisionScript.setText(Vocab.instance.COLLISIONLISTENER);
		
		cmbCollision = new LCombo(grpScript, SWT.READ_ONLY);
		cmbCollision.setIncludeID(false);
		cmbCollision.setOptional(true);
		cmbCollision.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblInteractscript = new Label(grpScript, SWT.NONE);
		lblInteractscript.setText(Vocab.instance.INTERACTLISTENER);
		
		cmbInteract = new LCombo(grpScript, SWT.READ_ONLY);
		cmbInteract.setIncludeID(false);
		cmbInteract.setOptional(true);
		cmbInteract.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		pack();
	}
	
	private void setCharID(int id) {
		GameCharacter gc = (GameCharacter) Project.current.characters.getList().get(id);
		cmbAnim.setItems(gc.animations);
		cmbStart.setItems(gc.startListeners);
		cmbCollision.setItems(gc.collisionListeners);
		cmbInteract.setItems(gc.interactListeners);
	}
	
	public void open(CharTile initial) {
		super.open(initial);
		cmbID.setItems(Project.current.characters.getList());
		cmbID.setValue(initial.id);
		cmbDir.setValue(initial.direction / 45);
		
		setCharID(initial.id);
		cmbAnim.setValue(initial.animID);
		cmbStart.setValue(initial.startID);
		cmbCollision.setValue(initial.collisionID);
		cmbInteract.setValue(initial.interactID);
	}

	@Override
	protected CharTile createResult(CharTile initial) {
		if (cmbID.getSelectionIndex() == initial.id && cmbAnim.getSelectionIndex() == initial.animID &&
				cmbDir.getSelectionIndex() * 45 == initial.direction && cmbStart.getSelectionIndex() == initial.startID &&
				cmbCollision.getSelectionIndex() == initial.collisionID && cmbInteract.getSelectionIndex() == initial.interactID) {
			return null;
		} else {
			CharTile t = new CharTile(cmbID.getSelectionIndex(), cmbAnim.getSelectionIndex(), cmbDir.getSelectionIndex() * 45);
			t.startID = cmbStart.getSelectionIndex();
			t.collisionID = cmbCollision.getSelectionIndex();
			t.interactID = cmbInteract.getSelectionIndex();
			return t;
		}
	}
	
}
