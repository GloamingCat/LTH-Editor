package gui.shell.database;

import gui.Vocab;
import lwt.dataestructure.LDataTree;
import lwt.dialog.LObjectShell;
import lwt.event.LControlEvent;
import lwt.event.listener.LControlListener;
import lwt.widget.LImage;
import lwt.widget.LLabel;
import lwt.widget.LNodeSelector;
import lwt.widget.LText;

import data.Animation;
import data.GameCharacter.Portrait;

import project.Project;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

public class PortraitShell extends LObjectShell<Portrait> {
	
	protected LNodeSelector<Object> tree;
	protected LImage image;
	protected int col, row;
	private ScrolledComposite scroll;
	private LText txtName;
	
	public PortraitShell(Shell parent, int style) {
		super(parent);
		GridData gridData = (GridData) content.getLayoutData();
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessVerticalSpace = true;
		setMinimumSize(600, 400);
		setSize(800, 800);

		content.setLayout(new GridLayout(2, false));
		
		new LLabel(content, Vocab.instance.NAME);
		
		txtName = new LText(content);
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		SashForm sashForm = new SashForm(content, SWT.HORIZONTAL);
		sashForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		tree = new LNodeSelector<Object>(sashForm, style);
		tree.setCollection(getTree());
		tree.addModifyListener(new LControlListener<Integer>() {
			@Override
			public void onModify(LControlEvent<Integer> event) {
				Animation anim = (Animation) tree.getSelectedObject();
				setImage(anim);
			}
		});
		
		scroll = new ScrolledComposite(sashForm, SWT.NONE);
		scroll.setExpandVertical(true);
		scroll.setExpandHorizontal(true);
		scroll.setLayout(new FillLayout());
		
		image = new LImage(scroll, SWT.NONE);
		image.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));
		image.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				Animation anim = (Animation) tree.getSelectedObject();
				if (anim != null && anim.cols > 0 && anim.rows > 0) {
					int w = anim.quad.width / anim.cols;
					int h = anim.quad.height / anim.rows;
					e.gc.drawRectangle(anim.quad.x + w * col, anim.quad.y + h * row, w, h);
				}
			}
		});
		image.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent arg0) {
				Animation anim = (Animation) tree.getSelectedObject();
				if (anim != null) {
					col = (int) (arg0.x - anim.quad.x) / (anim.quad.width / anim.cols);
					row = (int) (arg0.y - anim.quad.y) / (anim.quad.height / anim.rows);
					image.redraw();
				}
			}
		});
		scroll.setContent(image);		
		sashForm.setWeights(new int[] {1, 2});

		pack();
	}
	
	private void setImage(Animation anim) {
		if (anim == null)
			return;
		Image img = anim.quad.getImage();
		image.setImage(img);
		scroll.setMinSize(anim.quad.width, anim.quad.height);
		image.redraw();
	}
	
	public void open(Portrait initial) {
		super.open(initial);
		txtName.setValue(initial.name);
		if (initial.id >= 0) {
			col = initial.col;
			row = initial.row;
			LDataTree<Object> node = getTree().findNode(initial.id);
			if (node != null) {
				tree.setValue(node.id);
				setImage((Animation) node.data);
				return;
			}
		} else {
			col = row = 0;
		}
		tree.setValue(-1);
	}
	
	@Override
	protected Portrait createResult(Portrait initial) {
		Portrait icon = new Portrait();
		icon.name = txtName.getValue();
		icon.id = tree.getValue();
		icon.col = col;
		icon.row = row;
		return icon;
	}
	
	protected LDataTree<Object> getTree() {
		return Project.current.animations.getTree();
	}
	
}
