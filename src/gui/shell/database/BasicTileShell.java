package gui.shell.database;

import java.util.ArrayList;

import gui.Vocab;
import gui.views.database.subcontent.TagList;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Shell;

import lwt.dataestructure.LDataList;
import lwt.dialog.LObjectShell;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

import data.Tileset.BasicTile;
import data.subcontent.Tag;

public class BasicTileShell<T extends BasicTile> extends LObjectShell<T> {
	
	protected Combo cmbID;
	protected TagList lstTags;
	protected LDataList<Tag> tags;

	public BasicTileShell(Shell parent) {
		super(parent);
		
		content.setLayout(new GridLayout(2, false));
		
		Label lblID = new Label(content, SWT.NONE);
		lblID.setText(Vocab.instance.ID);
		
		cmbID = new Combo(content, SWT.BORDER | SWT.READ_ONLY);
		cmbID.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblTags = new Label(content, SWT.NONE);
		lblTags.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1));
		lblTags.setText(Vocab.instance.TAGS);
		
		lstTags = new TagList(content, SWT.NONE) {
			public LDataList<Tag> getDataCollection() {
				return tags;
			}
		};
		lstTags.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1));
		
		pack();
	}
	
	public void open(T initial) {
		super.open(initial);
		tags = new LDataList<Tag>();
		for(Tag tag : initial.tags) {
			tags.add(new Tag(tag));
		}
		cmbID.setItems(getItems(getArray()));
		cmbID.select(initial.id);
		lstTags.onVisible();
	}

	@Override
	protected T createResult(T initial) {
		if (cmbID.getSelectionIndex() == initial.id && tags.equals(initial.tags)) {
			return null;
		} else {
			return createNew();
		}
	}
	
	protected ArrayList<?> getArray() { return null; }
	protected T createNew() { return null; }
	
}
