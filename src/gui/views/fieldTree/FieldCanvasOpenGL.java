package gui.views.fieldTree;

import java.util.ArrayList;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Composite;

import batching.Batch;
import batching.BatchIterator;
import batching.Scene;
import data.subcontent.Point;
import gui.helper.FieldHelper;
import gui.helper.SceneHelper;
import rendering.Renderer;
import rendering.Screen;
import rendering.ShaderProgram;
import rendering.Texture;
import rendering.VertexArray;

public class FieldCanvasOpenGL extends FieldCanvas {
	
	protected Scene scene = null;
	protected Screen screen = null;
	protected ShaderProgram shader = null;
	protected Renderer renderer = null;
	protected VertexArray vertexArray;
	protected int nFloats;
	
	public FieldCanvasOpenGL(Composite parent, int style) {
		super(parent, style);
		SceneHelper.initContext();
		renderer = new Renderer();
		renderer.setBackgroundColor(128, 128, 255, 255);
		shader = new ShaderProgram("vertShader.glsl", "fragShader.glsl");
	}
	
	//////////////////////////////////////////////////
	// {{ Draw
	
	public void redrawBuffer() {
		if (buffer != null)
			buffer.dispose();
		SceneHelper.context.bind();
		shader.bind();
		screen.bind(shader);
		renderer.fillBackground();
		drawScene();
		buffer = SceneHelper.toImage(screen.texture);
	}
	
	protected void drawScene() {
		BatchIterator batches = scene.getBatchIterator();
		while(!batches.done()) {
			Batch batch = batches.next();
			Texture texture = SceneHelper.getTexture(batch.texturePath);
			if (texture == null)
				continue;
			int texWidth = texture.width;
			int texHeight = texture.height;
			int n = batch.getSize() * 4;
			texture.bind();
			float[] data = batch.getQuadVertices(texWidth, texHeight, nFloats);
			vertexArray.set(data, n);
			vertexArray.updateVAO(data);
			vertexArray.bind();
			renderer.drawQuads(vertexArray.getVaoId(), n);
			vertexArray.unbind();
		}
		renderer.resetBindings();
	}

	public void drawCursor(GC gc, Color color, Point point) {
		gc.setForeground(color);
		int[] p = FieldHelper.getTilePolygon(
				Math.round(scale * (point.x + x0)),
				Math.round(scale * (point.y + y0)),
				scale * 0.75f);
		gc.drawPolygon(p);
	}

	// }}
	
	//////////////////////////////////////////////////
	// {{ Callbacks

	public void onTileChange(int x, int y) {
		refresh();
	}
	
	public void onTileChange(ArrayList<Point> tiles) {
		refresh();
	}
	
	public void onVisible() {
		SceneHelper.reload();
		renderer.resetBindings();
		shader.bind();
		super.onVisible();
	}
	
	public void onDispose() {
		screen.dispose();
		shader.dispose();
		SceneHelper.reload();
	}
	
	// }}
	
	//////////////////////////////////////////////////
	// {{ Visualization
	
	public void refresh() {
		SceneHelper.createTileTextures(renderer, shader);
		scene = SceneHelper.createScene(field, x0, y0, showGrid, currentLayer, currentChar);
		if (vertexArray != null)
			vertexArray.dispose();
		vertexArray = new VertexArray(scene.allObjects().size() * 4);
		vertexArray.initVAO(shader.attributes, shader.vertexSize);
		nFloats = shader.vertexSize / 4;
		Point size = FieldHelper.math.pixelSize(field.sizeX, field.sizeY);
		int w = size.x + x0 * 2;
		int h = size.y + (FieldHelper.math.pixelDisplacement(field.sizeY) + 200 + 
				FieldHelper.config.grid.pixelsPerHeight * field.layers.maxHeight());
		if (screen != null)
			screen.dispose();
		screen = new Screen(w, h, false);
		super.refresh();
	}
	
	// }}	

}
