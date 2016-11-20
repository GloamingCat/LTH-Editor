package gui.views.config;

import gui.Vocab;
import lwt.action.LActionStack;
import lwt.editor.LObjectEditor;
import lwt.widget.LCheckButton;
import lwt.widget.LSpinner;
import lwt.widget.LText;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import project.Project;

public class ConfigEditor extends LObjectEditor {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public ConfigEditor(Composite parent, int style) {
		super(parent, style);

		actionStack = new LActionStack(this);
		
		setLayout(new GridLayout(3, true));
		
		Group grpGeneral = new Group(this, SWT.NONE);
		grpGeneral.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		grpGeneral.setText(Vocab.instance.GENERAL);
		grpGeneral.setLayout(new GridLayout(2, false));
		
		Label lblProjectName = new Label(grpGeneral, SWT.NONE);
		lblProjectName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblProjectName.setText(Vocab.instance.PROJECTNAME);
		
		LText txtName = new LText(grpGeneral, SWT.NONE);
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(txtName, "name");
		
		Group grpGrid = new Group(this, SWT.NONE);
		grpGrid.setLayout(new GridLayout(5, false));
		grpGrid.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 3, 1));
		grpGrid.setText(Vocab.instance.GRID);
		
		Label lblTileWidth = new Label(grpGrid, SWT.NONE);
		lblTileWidth.setText(Vocab.instance.TILEWIDTH);
		
		LSpinner spnTileW = new LSpinner(grpGrid, SWT.NONE);
		spnTileW.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnTileW, "tileW");
		
		Label lblTileBase = new Label(grpGrid, SWT.NONE);
		lblTileBase.setText(Vocab.instance.TILEBASE);
		
		LSpinner spnTileB = new LSpinner(grpGrid, SWT.NONE);
		spnTileB.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnTileB, "tileB");
		
		LCheckButton btnPixelMovement = new LCheckButton(grpGrid, SWT.CHECK);
		btnPixelMovement.setText(Vocab.instance.PIXELMOV);
		addControl(btnPixelMovement, "pixelMovement");
		
		Label lblTileHeight = new Label(grpGrid, SWT.NONE);
		lblTileHeight.setText(Vocab.instance.TILEHEIGHT);
		
		LSpinner spnTileH = new LSpinner(grpGrid, SWT.NONE);
		spnTileH.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnTileH, "tileH");
		
		Label lblTileSide = new Label(grpGrid, SWT.NONE);
		lblTileSide.setText(Vocab.instance.TILESIDE);
		
		LSpinner spnTileS = new LSpinner(grpGrid, SWT.NONE);
		spnTileS.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(spnTileS, "tileS");
		
		LCheckButton btnNeighbors = new LCheckButton(grpGrid, SWT.CHECK);
		btnNeighbors.setText(Vocab.instance.ALLNEIGHBORS);
		addControl(btnNeighbors, "allNeighbors");
		
		Label lblPixelsheight = new Label(grpGrid, SWT.NONE);
		lblPixelsheight.setText(Vocab.instance.PIXELHEIGHT);
		
		LSpinner spnPPH = new LSpinner(grpGrid, SWT.NONE);
		spnPPH.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		addControl(spnPPH, "pixelsPerHeight");
		
		Group grpElements = new Group(this, SWT.NONE);
		grpElements.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpElements.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));
		grpElements.setText(Vocab.instance.ELEMENTS);
		
		NameList lstElements = new NameList(grpElements, SWT.NONE) {
			public String attributeName() {
				return "elements";
			}
		};
		lstElements.setIncludeID(true);		
		addChild(lstElements);
		
		Group grpFieldTypes = new Group(this, SWT.NONE);
		grpFieldTypes.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpFieldTypes.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));
		grpFieldTypes.setText(Vocab.instance.FIELDTYPES);
		
		NameList lstFieldTypes = new NameList(grpFieldTypes, SWT.NONE) {
			public String attributeName() {
				return "fieldTypes";
			}
		};
		lstFieldTypes.setIncludeID(true);
		addChild(lstFieldTypes);
		
		Group grpAttributes = new Group(this, SWT.NONE);
		grpAttributes.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpAttributes.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));
		grpAttributes.setText(Vocab.instance.ATTRIBUTES);

		AttributeList lstAttributes = new AttributeList(grpAttributes, SWT.NONE);
		lstAttributes.setIncludeID(true);
		addChild(lstAttributes);
	}
	
	public void onVisible() {
		setObject(Project.current.config.getData());
	}
	
}
