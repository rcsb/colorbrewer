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
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.jcolorbrewer.ColorBrewer;

public class GetColorGradient extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4954397192682351367L;

	private static Color[] getMyGradient() {
		boolean colorBlindSave = true;
		ColorBrewer[] sequentialPalettes = ColorBrewer.getSequentialColorPalettes(colorBlindSave);	


		ColorBrewer myBrewer = sequentialPalettes[0];

		System.out.println( "Name of this color brewer: " + myBrewer);

		// I want a gradient of 8 colors:
		Color[] myGradient = myBrewer.getColorPalette(8);

		// These are the color codes:
		for (Color color: myGradient){
			// convert to hex for web display:
			String hex = Integer.toHexString(color.getRGB() & 0xffffff);			
			System.out.println("#"+hex+";");
		}
		
		return myGradient;
	}
	
	Color[] colors ;
	public GetColorGradient(Color[] colors){
		
		super(new GridLayout(colors.length,1));  //3 rows, 1 column
		this.colors = colors;
		
		setPreferredSize(new Dimension(15,(colors.length+1)*15));
		
	}
	
	public static void main(String[] args){

		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				//Turn off metal's use of bold fonts
				UIManager.put("swing.boldMetal", Boolean.FALSE);

				createAndShowGUI();
			}
		});
	}
	
	/**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
    	
    	Color[] colors = getMyGradient();
    	
        //Create and set up the window.
        JFrame frame = new JFrame("Color Palette Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Add content to the window.
        frame.add(new GetColorGradient(colors));
       
        //Display the window.
        frame.pack();
        
        
        
        frame.setVisible(true);
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);


        int x = 5;
        int y = 7;
        int width = 15;
        int height = 15;

        int colorCount = colors.length;
        for (int i = 0; i < colorCount; i++) {
			g.setColor(colors[i]);
			g.fillRect(x, y+i*height, width, height);
			g.setColor(Color.BLACK);
			g.drawRect(x, y+i*height, width, height);
		}
      }	
}
