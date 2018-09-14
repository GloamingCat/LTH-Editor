package gui.shell;


import gui.Vocab;
import gui.views.QuadButton;
import lwt.dialog.LObjectShell;
import lwt.event.LControlEvent;
import lwt.event.listener.LControlListener;
import lwt.widget.LImage;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;

import data.GameCharacter.Portrait;
import data.subcontent.Quad;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;

public class PortraitShell extends LObjectShell<Portrait> {
	
	private Text txtName;
	private QuadButton btnQuad;
	private LImage imgQuad;
	
	public PortraitShell(Shell parent) {
		super(parent);
		setSize(296, 300);
		content.setLayout(new GridLayout(2, false));
		
		Label lblName = new Label(content, SWT.NONE);
		lblName.setText(Vocab.instance.NAME);
		
		txtName = new Text(content, SWT.BORDER);
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblQuad = new Label(content, SWT.NONE);
		lblQuad.setText(Vocab.instance.GRAPHICS);
		
		btnQuad = new QuadButton(content, SWT.NONE);
		btnQuad.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		btnQuad.addModifyListener(new LControlListener<Quad>() {
			@Override
			public void onModify(LControlEvent<Quad> event) {
				imgQuad.setImage(event.newValue.path, event.newValue.getRectangle());
			}
		});
		
		imgQuad = new LImage(content, SWT.NONE);
		GridData gd_lblImg = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		gd_lblImg.widthHint = 160;
		gd_lblImg.heightHint = 100;
		imgQuad.setLayoutData(gd_lblImg);
		imgQuad.setVerticalAlign(SWT.CENTER);
		imgQuad.setHorizontalAlign(SWT.CENTER);
		
		pack();
	}
	
	public void open(Portrait initial) {
		super.open(initial);
		btnQuad.setValue(initial.quad.clone());
		txtName.setText(initial.name);
	}
	
	@Override
	protected Portrait createResult(Portrait initial) {
		if (txtName.getText().equals(initial.name) && btnQuad.getValue().equals(initial.quad)) {
			return null;
		}
		Portrait p = new Portrait();
		p.name = txtName.getText();
		p.quad = btnQuad.getValue();
		return p;
	}
	
}
