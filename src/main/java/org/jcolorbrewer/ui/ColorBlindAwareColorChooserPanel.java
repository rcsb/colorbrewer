package org.jcolorbrewer.ui;

import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andreas on 7/19/15.
 */
public abstract class ColorBlindAwareColorChooserPanel extends AbstractColorChooserPanel  {

    boolean showColorBlindSave = false;

    List<JToggleButton> currentButtons = new ArrayList<JToggleButton>();

    public boolean isShowColorBlindSave() {
        return showColorBlindSave;
    }

    public void setShowColorBlindSave(boolean showColorBlindSave) {
        this.showColorBlindSave = showColorBlindSave;
        this.repaint();
    }

    public void updateChooser() {

        if ( currentButtons == null)
            currentButtons = new ArrayList<JToggleButton>();

        for (JToggleButton c: currentButtons) {
            remove(c);
        }
        currentButtons.clear();

        buildChooser();

    }


}
