package gui.views.dialogue;

import gui.Vocab;
import gui.views.QuadButton;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import lwt.editor.LObjectEditor;
import lwt.event.LControlEvent;
import lwt.event.listener.LControlListener;
import lwt.widget.LCombo;
import lwt.widget.LSpinner;
import lwt.widget.LText;
import lwt.widget.LTextBox;

public class SpeechEditor extends LObjectEditor {

	public SpeechEditor(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(2, false));
		
		Composite nameTop = new Composite(this, SWT.NONE);
		GridLayout gl_nameTop = new GridLayout(3, false);
		gl_nameTop.marginHeight = 0;
		gl_nameTop.marginWidth = 0;
		nameTop.setLayout(gl_nameTop);
		nameTop.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblCharname = new Label(nameTop, SWT.NONE);
		lblCharname.setText(Vocab.instance.CHARNAME);
		
		LText txtCharName = new LText(nameTop, SWT.NONE);
		txtCharName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addControl(txtCharName, "name");
		
		LCombo cmbNameSide = new LCombo(nameTop, SWT.NONE);
		cmbNameSide.setOptional(false);
		cmbNameSide.setIncludeID(false);
		cmbNameSide.setItems(new String[] {Vocab.instance.LEFT, Vocab.instance.RIGHT});
		addControl(cmbNameSide, "nameSide");
		
		Group grpPortrait = new Group(this, SWT.NONE);
		grpPortrait.setLayout(new GridLayout(1, false));
		grpPortrait.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 2));
		grpPortrait.setText(Vocab.instance.PORTRAIT);
		
		Composite portraitTop = new Composite(grpPortrait, SWT.NONE);
		portraitTop.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		GridLayout gl_portraitTop = new GridLayout(2, false);
		gl_portraitTop.marginHeight = 0;
		gl_portraitTop.marginWidth = 0;
		portraitTop.setLayout(gl_portraitTop);
		
		QuadButton btnQuad = new QuadButton(portraitTop, SWT.NONE);
		btnQuad.setFolder("Portrait");
		btnQuad.optional = true;
	
		LCombo cmbPortraitSide = new LCombo(portraitTop, SWT.NONE);
		cmbPortraitSide.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		cmbPortraitSide.setIncludeID(false);
		cmbPortraitSide.setOptional(false);
		cmbPortraitSide.setItems(new String[] {Vocab.instance.LEFT, Vocab.instance.RIGHT});
		addControl(cmbPortraitSide, "portraitSide");
		
		Label lblPortrait = new Label(grpPortrait, SWT.NONE);
		lblPortrait.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		btnQuad.setLabel(lblPortrait);
		addControl(btnQuad, "portrait");
		
		Group grpMessage = new Group(this, SWT.NONE);
		grpMessage.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpMessage.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpMessage.setText(Vocab.instance.MESSAGE);
		
		LTextBox txtMessage = new LTextBox(grpMessage, SWT.NONE);
		addControl(txtMessage, "message");
		
		Composite bottom = new Composite(this, SWT.NONE);
		GridLayout gl_bottom = new GridLayout(2, false);
		gl_bottom.marginHeight = 0;
		gl_bottom.marginWidth = 0;
		bottom.setLayout(gl_bottom);
		bottom.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		
		Group grpSize = new Group(bottom, SWT.NONE);
		grpSize.setLayout(new GridLayout(2, false));
		grpSize.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		grpSize.setText(Vocab.instance.SIZE);
		
		Label lblWidthType = new Label(grpSize, SWT.NONE);
		lblWidthType.setText("Width Type");
		
		LCombo cmbWidthType = new LCombo(grpSize, SWT.NONE);
		cmbWidthType.setIncludeID(false);
		cmbWidthType.setOptional(false);
		cmbWidthType.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		cmbWidthType.setItems(new String[] {
				Vocab.instance.MINWIDTH, Vocab.instance.SCREENWIDTH, Vocab.instance.CUSTOM});
		addControl(cmbWidthType, "widthType");
		
		Label lblHeightType = new Label(grpSize, SWT.NONE);
		lblHeightType.setText("Height Type");
		
		LCombo cmbHeightType = new LCombo(grpSize, SWT.NONE);
		cmbHeightType.setIncludeID(false);
		cmbHeightType.setOptional(false);
		cmbHeightType.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		cmbHeightType.setItems(new String[] {
				Vocab.instance.MINHEIGHT, Vocab.instance.MAXLINES, Vocab.instance.CUSTOM});
		addControl(cmbHeightType, "heightType");
		
		Label lblLines = new Label(grpSize, SWT.NONE);
		lblLines.setText(Vocab.instance.LINECOUNT);
		
		LSpinner spnLines = new LSpinner(grpSize, SWT.NONE);
		spnLines.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		spnLines.setMaximum(99);
		spnLines.setMinimum(-1);
		addControl(spnLines, "lineCount");
		
		Label lblCustomWidth = new Label(grpSize, SWT.NONE);
		lblCustomWidth.setText(Vocab.instance.CUSTOMWIDTH);
		
		LSpinner spnWidth = new LSpinner(grpSize, SWT.NONE);		
		spnWidth.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		spnWidth.setMaximum(9999);
		spnWidth.setMinimum(-1);
		addControl(spnWidth, "width");
		cmbWidthType.addModifyListener(new LControlListener<Integer>() {
			@Override
			public void onModify(LControlEvent<Integer> event) {
				spnWidth.setEnabled(event.newValue == 2);
			}
		});
		
		Label lblCustomHeight = new Label(grpSize, SWT.NONE);
		lblCustomHeight.setText(Vocab.instance.CUSTOMHEIGHT);
		
		LSpinner spnHeight = new LSpinner(grpSize, SWT.NONE);		
		spnHeight.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		spnHeight.setMaximum(9999);
		spnHeight.setMinimum(-1);
		addControl(spnHeight, "height");
		cmbHeightType.addModifyListener(new LControlListener<Integer>() {
			@Override
			public void onModify(LControlEvent<Integer> event) {
				spnHeight.setEnabled(event.newValue == 2);
			}
		});
		
		Group grpPos = new Group(bottom, SWT.NONE);
		grpPos.setLayout(new GridLayout(2, false));
		grpPos.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		grpPos.setText(Vocab.instance.POSITION);
		
		Label lblXType = new Label(grpPos, SWT.NONE);
		lblXType.setText("X Type");
		
		LCombo cmbXType = new LCombo(grpPos, SWT.NONE);
		cmbXType.setOptional(false);
		cmbXType.setIncludeID(false);
		cmbXType.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		cmbXType.setItems(new String[] {
				Vocab.instance.LEFT, Vocab.instance.CENTER, Vocab.instance.RIGHT, Vocab.instance.CUSTOM});
		addControl(cmbXType, "xType");
		
		Label lblYType = new Label(grpPos, SWT.NONE);
		lblYType.setText("Y Type");
		
		LCombo cmbYType = new LCombo(grpPos, SWT.NONE);
		cmbYType.setOptional(false);
		cmbYType.setIncludeID(false);
		cmbYType.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		cmbYType.setItems(new String[] {
				Vocab.instance.TOP, Vocab.instance.CENTER, Vocab.instance.BOTTOM, Vocab.instance.CUSTOM});
		addControl(cmbYType, "yType");
		
		Label lblCustomX = new Label(grpPos, SWT.NONE);
		lblCustomX.setText("Custom X");
		
		LSpinner spnX = new LSpinner(grpPos, SWT.NONE);		
		spnX.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		spnX.setMaximum(9999);
		spnX.setMinimum(-1);
		addControl(spnX, "x");
		cmbXType.addModifyListener(new LControlListener<Integer>() {
			@Override
			public void onModify(LControlEvent<Integer> event) {
				spnX.setEnabled(event.newValue == 3);
			}
		});
		
		Label lblCustomY = new Label(grpPos, SWT.NONE);
		lblCustomY.setText("Custom Y");
		
		LSpinner spnY = new LSpinner(grpPos, SWT.NONE);		
		spnY.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		spnY.setMaximum(9999);
		spnY.setMinimum(-1);
		addControl(spnY, "y");
		cmbYType.addModifyListener(new LControlListener<Integer>() {
			@Override
			public void onModify(LControlEvent<Integer> event) {
				spnY.setEnabled(event.newValue == 3);
			}
		});
		
	}

	
}
