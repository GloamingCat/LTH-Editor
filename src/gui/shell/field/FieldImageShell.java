package gui.shell.field;

import gui.Vocab;
import lwt.container.LPanel;
import lwt.container.LSashPanel;
import lwt.dataestructure.LDataTree;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShell;
import lwt.event.LControlEvent;
import lwt.event.listener.LControlListener;
import lwt.widget.LCheckBox;
import lwt.widget.LImage;
import lwt.widget.LLabel;
import lwt.widget.LNodeSelector;
import lwt.widget.LText;

import data.Animation;
import data.field.FieldImage;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

import project.Project;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.wb.swt.SWTResourceManager;

public class FieldImageShell extends LObjectShell<FieldImage> {
	
	protected LNodeSelector<Object> tree;
	protected LImage image;
	protected int col, row;
	private ScrolledComposite scroll;
	private LText txtName;
	private LCheckBox btnVisible;
	private LCheckBox btnForeground;
	private LCheckBox btnGlued;
	
	public FieldImageShell(LShell parent) {
		super(parent);
		GridData gridData = (GridData) content.getLayoutData();
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessVerticalSpace = true;
		setMinimumSize(400, 300);

		content.setLayout(new GridLayout(2, false));
		
		new LLabel(content, Vocab.instance.NAME);
		
		txtName = new LText(content);

		LSashPanel sashForm = new LSashPanel(content, true);
		sashForm.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		tree = new LNodeSelector<Object>(sashForm, false);
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

		LPanel options = new LPanel(content, 3, false);
		options.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		
		btnVisible = new LCheckBox(options);
		btnVisible.setText(Vocab.instance.VISIBLE);
		
		btnForeground = new LCheckBox(options);
		btnForeground.setText(Vocab.instance.FOREGROUND);
		
		btnGlued = new LCheckBox(options);
		btnGlued.setText(Vocab.instance.GLUED);
		
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
	
	public void open(FieldImage initial) {
		super.open(initial);
		txtName.setValue(initial.name);
		btnVisible.setValue(initial.visible);
		btnForeground.setValue(initial.foreground);
		btnGlued.setValue(initial.glued);
		tree.setValue(-1);
		if (initial.id >= 0) {
			col = initial.col;
			row = initial.row;
			LDataTree<Object> node = getTree().findNode(initial.id);
			if (node != null) {
				tree.setValue(node.id);
				setImage((Animation) node.data);
			}
		} else {
			col = row = 0;
		}
	}
	
	@Override
	protected FieldImage createResult(FieldImage initial) {
		FieldImage icon = new FieldImage();
		icon.name = txtName.getValue();
		icon.id = tree.getValue();
		icon.col = col;
		icon.row = row;
		icon.visible = btnVisible.getValue();
		icon.foreground = btnForeground.getValue();
		icon.glued = btnGlued.getValue();
		return icon;
	}
	
	protected LDataTree<Object> getTree() {
		return Project.current.animations.getTree();
	}
	
}
