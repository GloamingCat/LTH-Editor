package gui.shell;

import gui.Vocab;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import lwt.dialog.LObjectShell;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

import data.Config;
import data.ImageAtlas;
import data.ImageAtlas.Entry;
import project.Project;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class AtlasEntryShell extends LObjectShell<Entry> {
	
	private static int lastID = 0;
	private Combo cmbID;
	private int x;
	private int y;
	private int nc;
	private int nl;
	private ImageAtlas atlas;
	private Config conf;
	private Label lblAtlas;
	
	public AtlasEntryShell(Shell parent) {
		super(parent);
		GridData gridData = (GridData) content.getLayoutData();
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessVerticalSpace = true;
		setMinimumSize(new Point(300, 300));
		
		content.setLayout(new GridLayout(2, false));
		
		Label lblID = new Label(content, SWT.NONE);
		lblID.setText(Vocab.instance.ID);
		
		cmbID = new Combo(content, SWT.BORDER | SWT.READ_ONLY);
		cmbID.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Composite labelHolder = new Composite(content, SWT.NONE);
		labelHolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 2, 1));
		
		lblAtlas = new Label(labelHolder,  SWT.NONE);
		lblAtlas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if (atlas != null) {
					x = e.x / atlas.width;
					y = e.y / atlas.height;
					lblAtlas.redraw();
				}
			}
		});
		lblAtlas.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				if (atlas == null)
					return;
				Rectangle bounds = lblAtlas.getImage().getBounds();
				nc = bounds.width / atlas.width;
				nl = bounds.height / atlas.height;
				for (int i = 1; i < nc; i++) {
					e.gc.drawLine(i * atlas.width, 0, i * atlas.width, bounds.height);
				}
				for (int i = 1; i < nl; i++) {
					e.gc.drawLine(0, i * atlas.height, bounds.height, i * atlas.height);
				}
				bounds.x = x * atlas.width + 2;
				bounds.y = y * atlas.height + 2;
				bounds.width = atlas.width - 4;
				bounds.height = atlas.height - 4;
				e.gc.drawRectangle(bounds);
			}
		});
		lblAtlas.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 2, 1));
		
		cmbID.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				atlas = conf.atlases.get(cmbID.getSelectionIndex());
				resetImage();
			}
		});
		
		pack();
	}
	
	public void open(Entry initial) {
		super.open(initial);
		conf = (Config) Project.current.config.getData();
		cmbID.setItems(getItems(conf.atlases));
		if (lastID < conf.atlases.size()) {
			cmbID.select(lastID);
			atlas = conf.atlases.get(lastID);
			resetImage();
		}
	}

	@Override
	protected Entry createResult(Entry initial) {
		Entry e = new Entry();
		lastID = cmbID.getSelectionIndex();
		e.atlas = atlas;
		e.x = x;
		e.y = y;
		return e;
	}
	
	private void resetImage() {
		Image img = SWTResourceManager.getImage(
				Project.current.imagePath() + atlas.imagePath);
		lblAtlas.setImage(img);
		lblAtlas.setSize(img.getBounds().width, img.getBounds().height);
	}

}
