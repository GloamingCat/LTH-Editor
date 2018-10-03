package gui.shell;

import gui.Vocab;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import project.Project;
import data.Animation;
import data.subcontent.Icon;
import lwt.dataestructure.LDataTree;
import lwt.dialog.LObjectShell;
import lwt.editor.LDefaultTreeEditor;
import lwt.event.LSelectionEvent;
import lwt.event.listener.LSelectionListener;
import lwt.widget.LImage;

import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.wb.swt.SWTResourceManager;

public class IconShell extends LObjectShell<Icon> {
	
	protected LDefaultTreeEditor<Object> tree;
	protected Button btnNull;
	protected LImage image;
	protected int col, row;
	
	public IconShell(Shell parent, boolean optional) {
		super(parent);
		setSize(600, 400);
		GridData gridData = (GridData) content.getLayoutData();
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessVerticalSpace = true;
		setMinimumSize(new Point(400, 300));
		
		content.setLayout(new FillLayout());
		
		SashForm sashForm = new SashForm(content, SWT.HORIZONTAL);
		
		Composite compTree = new Composite(sashForm, SWT.NONE);
		compTree.setLayout(new GridLayout());
		
		tree = new LDefaultTreeEditor<Object>(compTree, 0) {
			@Override
			public LDataTree<Object> getDataCollection() { return getTree(); }
			@Override
			protected Object createNewData() { return null; }
			@Override
			protected Object duplicateData(Object original) { return null; }
		};
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		tree.getCollectionWidget().addSelectionListener(new LSelectionListener() {
			@Override
			public void onSelect(LSelectionEvent event) {
				Animation anim = (Animation) getTree().getNode(event.path).data;
				image.setImage(anim.getImage());
				image.redraw();
			}
		});
		 		
		btnNull = new Button(compTree, 0);
		btnNull.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				tree.getCollectionWidget().select(null);
			}
		});
		btnNull.setText(Vocab.instance.NONE);
		btnNull.setEnabled(optional);
		
		image = new LImage(sashForm, SWT.NONE);
		image.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
		image.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				Animation anim = (Animation) tree.getCollectionWidget().getSelectedObject();
				if (anim != null && anim.cols > 0 && anim.rows > 0) {
					int w = anim.quad.width / anim.cols;
					int h = anim.quad.height / anim.rows;
					e.gc.drawRectangle(w * col, h * row, w, h);
				}
			}
		});
		image.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent arg0) {
				Animation anim = (Animation) tree.getCollectionWidget().getSelectedObject();
				if (anim != null) {
					col = (int) arg0.x / (anim.quad.width / anim.cols);
					row = (int) arg0.y / (anim.quad.height / anim.rows);
					image.redraw();
				}
			}
		});
		
		sashForm.setWeights(new int[] {1, 2});
	}
	
	public void open(Icon initial) {
		super.open(initial);
		tree.onVisible();
		tree.getCollectionWidget().select(null);
		if (initial.id >= 0) {
			col = initial.col;
			row = initial.row;
			LDataTree<Object> node = getTree().findNode(initial.id);
			if (node != null) {
				tree.getCollectionWidget().select(node.toPath());
				Animation anim = (Animation) node.data;
				image.setImage(anim.getImage());
				image.redraw();
			}
		} else {
			col = row = 0;
		}
	}
	
	@Override
	protected Icon createResult(Icon initial) {
		int id = getTree().getNode(tree.getCollectionWidget().getSelectedPath()).id;
		if (initial.id == id && col == initial.col && row == initial.row)
			return null;
		else {
			Icon icon = new Icon();
			icon.id = id;
			icon.col = col;
			icon.row = row;
			return icon;
		}
	}
	
	public void setOptional(boolean value) {
		btnNull.setEnabled(value);
	}
	
	protected LDataTree<Object> getTree() {
		return Project.current.animations.getTree();
	}
	
}
