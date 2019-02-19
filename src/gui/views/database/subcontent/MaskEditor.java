package gui.views.database.subcontent;

import gui.shell.database.MaskShell;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import data.Skill.Mask;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;
import lwt.editor.LObjectEditor;
import lwt.widget.LObjectButton;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

public class MaskEditor extends LObjectEditor {

	protected static final int cellSize = 8;
	protected static final int border = 2;
	
	public Color falseColor = SWTResourceManager.getColor(SWT.COLOR_BLACK);
	public Color trueColor = SWTResourceManager.getColor(SWT.COLOR_GREEN);
	public Color centerColor = SWTResourceManager.getColor(SWT.COLOR_RED);

	public MaskEditor(Composite parent, int style) {
		super(parent, style);
		
		setLayout(new GridLayout(2, false));
		Composite mask = new Composite(this, SWT.NONE);
		mask.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		mask.addPaintListener(new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				if (currentObject == null) return;
				Mask m = (Mask) currentObject;
				boolean[][] middle = m.grid[m.centerH];
				e.gc.drawRectangle(new Rectangle(0, 0, 
						middle.length * cellSize, 
						middle[0].length * cellSize));
				for (int i = 0; i < middle.length; i++) {
					for (int j = 0; j < middle[i].length; j++) {
						e.gc.setBackground(middle[i][j] ? trueColor : falseColor);
						e.gc.fillRectangle(border + i * cellSize, border + j * cellSize,
									cellSize - border, cellSize - border);
						e.gc.drawRectangle(i * cellSize, j * cellSize,
									cellSize, cellSize);
					}
				}
				e.gc.setForeground(centerColor);
				e.gc.drawRectangle(border + m.centerX * cellSize, border + m.centerY * cellSize,
						cellSize - border - 2, cellSize - border - 2);
			}
		});
		LObjectButton<Mask> button = new LObjectButton<Mask>(this, SWT.NONE) {
			public void setValue(Object value) {
				super.setValue(value);
				currentObject = value;
				mask.redraw();
			}
		};
		button.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, true, 1, 1));
		button.setShellFactory(new LShellFactory<Mask>() {
			@Override
			public LObjectShell<Mask> createShell(Shell parent) {
				MaskShell shell = new MaskShell(parent);
				shell.trueColor = trueColor;
				shell.falseColor = falseColor;
				shell.centerColor = centerColor;
				return shell;
			}
		});
		
		addControl(button, "");
		
	}

}
