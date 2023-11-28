package gui.views.fieldTree;

import gui.helper.TilePainter;
import lwt.LFlags;
import lwt.container.LContainer;
import lwt.container.LPanel;
import lwt.container.LScrollPanel;
import lwt.event.LControlEvent;
import lwt.event.listener.LControlListener;
import lwt.widget.LActionButton;
import lwt.widget.LLabel;

import project.Project;
import data.field.Field;
import data.field.FieldNode;
import data.field.Layer;
import gson.editor.GDefaultObjectEditor;

public class FieldEditor extends GDefaultObjectEditor<Field> {

	public static FieldEditor instance;
	public FieldCanvas canvas;

	private LScrollPanel scrolledComposite;

	/**
	 * @wbp.parser.constructor
	 * @wbp.eval.method.parameter parent new lwt.dialog.LShell()
	 */
	public FieldEditor(LContainer parent) {
		super(parent, 2, false, true);
		instance = this;
		
		FieldToolBar toolBar = new FieldToolBar(this);
		toolBar.setAlignment(LFlags.CENTER);
		toolBar.setExpand(true, false);
		
		LLabel lblId = new LLabel(this, "ID: 9999");
		
		scrolledComposite = new LScrollPanel(this, true);
		scrolledComposite.setExpand(true, true);
		scrolledComposite.setSpread(2, 1);
		
		canvas = new FieldCanvasOpenGL(scrolledComposite);
		canvas.lblId = lblId;
		addChild(canvas);
		scrolledComposite.setContent(canvas);

		LPanel bottom = new LPanel(this, 2, false);
		bottom.setAlignment(LFlags.CENTER);
		bottom.setSpread(2, 1);
		
		LLabel tileCoord = new LLabel(bottom, "(-99, -99, -99)");
		canvas.lblCoords = tileCoord;
		
		LPanel scale = new LPanel(bottom, 3, true);
		scale.setExpand(true, false);
		scale.setAlignment(LFlags.RIGHT | LFlags.CENTER);
		
		LActionButton btn50 = new LActionButton(scale, "1:2");
		btn50.addModifyListener(new LControlListener<Object>() {
			@Override
			public void onModify(LControlEvent<Object> event) {
				canvas.rescale(0.5f);
				scrolledComposite.setMinSize(canvas.getSize());
			}
		});

		LActionButton btn100 = new LActionButton(scale, "1:1");
		btn100.addModifyListener(new LControlListener<Object>() {
			@Override
			public void onModify(LControlEvent<Object> event) {
				canvas.rescale(1);
				scrolledComposite.setMinSize(canvas.getSize());
			}
		});
		
		LActionButton btn200 = new LActionButton(scale, "2:1");
		btn200.addModifyListener(new LControlListener<Object>() {
			@Override
			public void onModify(LControlEvent<Object> event) {
				canvas.rescale(2);
				scrolledComposite.setMinSize(canvas.getSize());
			}
		});
		
	}
	
	public void selectLayer(Layer layer) {
		canvas.setCurrentLayer(layer);
	}
	
	public void selectField(Field field) {
		canvas.setField(field);
		scrolledComposite.setMinSize(canvas.getSize());
	}
	
	@Override
	public void setObject(Object obj) {
		if (obj != null) {
			FieldNode node = (FieldNode) obj;
			int id = Project.current.fieldTree.getData().getFieldID(node);
			if (canvas.field == null || canvas.field.id != id) {
				TilePainter.reload();
				Field field = Project.current.fieldTree.loadField(node);
				selectField(field);
				super.setObject(field);
			}
		} else {
			selectField(null);
			super.setObject(null);
		}
	}

}
