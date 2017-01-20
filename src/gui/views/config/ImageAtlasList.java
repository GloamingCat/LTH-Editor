package gui.views.config;

import gui.shell.ImageAtlasShell;
import gui.views.common.SimpleEditableList;

import org.eclipse.swt.widgets.Composite;

import data.ImageAtlas;
import lwt.dialog.LObjectShell;
import lwt.dialog.LShellFactory;

public class ImageAtlasList extends SimpleEditableList<ImageAtlas> {

	public ImageAtlasList(Composite parent, int style) {
		super(parent, style);
		attributeName = "atlases";
		type = ImageAtlas.class;
		setIncludeID(true);
		setShellFactory(new LShellFactory<ImageAtlas>() {
			@Override
			public LObjectShell<ImageAtlas> createShell(
					org.eclipse.swt.widgets.Shell parent) {
				return new ImageAtlasShell(parent);
			}
		});
	}

}
