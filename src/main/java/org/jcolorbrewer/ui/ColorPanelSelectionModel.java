package org.jcolorbrewer.ui;

import javax.swing.colorchooser.DefaultColorSelectionModel;

import org.jcolorbrewer.ColorBrewer;


public class ColorPanelSelectionModel extends DefaultColorSelectionModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ColorBrewer brewer;

	public void setColorBrewer(ColorBrewer brewer) {
		this.brewer = brewer;
		super.setSelectedColor(null);
	}

	public ColorBrewer getColorBrewer() {
		return brewer;
	}
}
