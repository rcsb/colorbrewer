/*
 *                    BioJava development code
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  If you do not have a copy,
 * see:
 *
 *      http://www.gnu.org/copyleft/lesser.html
 *
 * Copyright for this code is held jointly by the individual
 * authors.  These should be listed in @author doc comments.
 *
 * For more information on the BioJava project and its aims,
 * or to join the biojava-l mailing list, visit the home page
 * at:
 *
 *      http://www.biojava.org/
 *
 * Created on Mar 9, 2014
 * Author: andreas 
 *
 */

package demo;


import java.awt.Color;

import org.jcolorbrewer.ui.ColorPaletteChooserDialog;



public class ShowColorChooserDialog {
	public static void main(String[] args){
		final ColorPaletteChooserDialog dialog = new ColorPaletteChooserDialog();
		dialog.show();
		System.out.println(dialog.getColor());
		if(dialog.wasOKPressed()) {
			Color c = dialog.getColor();
			System.out.println(c);
		}



	}
}
