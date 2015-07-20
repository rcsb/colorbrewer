/*
 * BioJava development code
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence. This should
 * be distributed with the code. If you do not have a copy,
 * see:
 *
 * http://www.gnu.org/copyleft/lesser.html
 *
 * Copyright for this code is held jointly by the individual
 * authors. These should be listed in @author doc comments.
 *
 * For more information on the BioJava project and its aims,
 * or to join the biojava-l mailing list, visit the home page
 * at:
 *
 * http://www.biojava.org/
 *
 * This code was contributed from the Molecular Biology Toolkit
 * (MBT) project at the University of California San Diego.
 *
 * Please reference J.L. Moreland, A.Gramada, O.V. Buzko, Qing
 * Zhang and P.E. Bourne 2005 The Molecular Biology Toolkit (MBT):
 * A Modular Platform for Developing Molecular Visualization
 * Applications. BMC Bioinformatics, 6:21.
 *
 * The MBT project was funded as part of the National Institutes
 * of Health PPG grant number 1-P01-GM63208 and its National
 * Institute of General Medical Sciences (NIGMS) division. Ongoing
 * development for the MBT project is managed by the RCSB
 * Protein Data Bank(http://www.pdb.org) and supported by funds
 * from the National Science Foundation (NSF), the National
 * Institute of General Medical Sciences (NIGMS), the Office of
 * Science, Department of Energy (DOE), the National Library of
 * Medicine (NLM), the National Cancer Institute (NCI), the
 * National Center for Research Resources (NCRR), the National
 * Institute of Biomedical Imaging and Bioengineering (NIBIB),
 * the National Institute of Neurological Disorders and Stroke
 * (NINDS), and the National Institute of Diabetes and Digestive
 * and Kidney Diseases (NIDDK).
 *
 * Created on 2007/02/08
 *
 */ 
package org.jcolorbrewer.ui;


// GUI
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.colorchooser.AbstractColorChooserPanel;

import org.jcolorbrewer.ColorBrewer;


/**
 *  A ColorPaletteChooserDialog shows a dialog window for selecting a color palette.
 *  
 *  @author	Peter Rose, code adopted from ColorChooserDialog
 *  @see	ColorChooserDialog
 */
public class ColorPaletteChooserDialog
	extends JDialog
{
	private static final long serialVersionUID = -5953604049348574107L;

//----------------------------------------------------------------------
//  Fields
//----------------------------------------------------------------------
	/**
	 * The parent window.
	 */
	protected Window parent = null;

	/**
	 * The inner panel containing everything.
	 */
	protected JPanel innerPanel = null;

	/**
	 * The style editor panel.
	 */
	protected JColorChooser colorChooser = null;

	/**
	 * Starting color, set by setColor, and the color we return
	 * to on a reset.
	 */
	protected Color startingColor = Color.white;
	
	/**
	 * Starting color, set by setColor, and the color we return
	 * to on a reset.
	 */
	protected ColorBrewer startingColorBrewer = ColorBrewer.BrBG;

	/**
	 * True if OK was pressed; false otherwise.
	 */
	protected boolean okWasPressed = false;





//----------------------------------------------------------------------
//  Constructors / Initializers
//----------------------------------------------------------------------
	/**
	 * Create a non-modal color chooser dialog to select a color.
	 * <P>
	 * This constructor does not require a parent argument to specify
	 * a parent window or dialog for this dialog.  As a result, this
	 * dialog is always non-modal - it does not block interaction with
	 * the rest of the application.
	 */
	public ColorPaletteChooserDialog( )
	{
		super( );			// Non-modal
		this.parent = null;

		this.initialize( );
	}

	/**
	 * Create a modal color chooser dialog to select a color.
	 *
	 * @param	parent		the parent frame for this dialog
	 */
	public ColorPaletteChooserDialog( final Frame parent )
	{
		super( parent, true );		// Always modal

		this.parent = parent;

		this.initialize( );
	}

	/**
	 * Create a modal color chooser dialog to select a color.
	 *
	 * @param	parent		the parent dialog for this dialog
	 */
	public ColorPaletteChooserDialog( final Dialog parent )
	{
		super( parent, true );		// Always modal

		this.parent = parent;

		this.initialize( );
	}


	ColorBlindAwareColorChooserPanel[] panels = new ColorBlindAwareColorChooserPanel[3];

	/**
	 * Initializes the GUI for the window.  That GUI
	 * includes a JColorChooser panel, framed with an empty border.
	 */
	protected void initialize( )
	{
		//
		// Configure the window.
		//
		this.setTitle( "Select color scheme" );
		this.setDefaultCloseOperation( WindowConstants.HIDE_ON_CLOSE );
		this.setResizable( false );


		//
		// Fill the window.
		//	An inner panel provides an empty border.  The
		//	JColorChooser panel is inside the inner panel.
		//
		final Container pane = this.getContentPane();
		pane.setLayout( new BorderLayout( ) );


		// Inner panel
		this.innerPanel = new JPanel( );
		this.innerPanel.setLayout( new BorderLayout( ) );
		this.innerPanel.setBorder( new CompoundBorder(
			new BevelBorder( BevelBorder.LOWERED ),
			new EmptyBorder( new Insets( 10, 10, 10, 10 ) ) ) );
		pane.add(this.innerPanel, BorderLayout.CENTER);

		// Colors Tab
		final JPanel colorsTab = new JPanel( );
		colorsTab.setBorder(new EmptyBorder(10, 10, 10, 10));
		//colorsTab.setLayout( new GridLayout(3, 1, 5, 0) );
		colorsTab.setLayout(new BoxLayout(colorsTab, BoxLayout.Y_AXIS));
		this.innerPanel.add(colorsTab);

		// set custom colorSelectionModel
		ColorPanelSelectionModel model = new ColorPanelSelectionModel();
		this.colorChooser = new JColorChooser(model);
	
		// overwrite the color chooser panels

		panels[0] = new SequentialColorPalettePanel();
		panels[1] = new DivergingColorPalettePanel();
		panels[2] = new QualitativeColorPalettePanel();
		colorChooser.setChooserPanels(panels);
		
		// overwrite the default preview panel
		colorChooser.setPreviewPanel(new JPanel()); 
		colorsTab.add( this.colorChooser, BorderLayout.CENTER );


		// color blind friendly checkbox
		final JPanel cbFriendlyPanel = new JPanel( );
		cbFriendlyPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		cbFriendlyPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
		colorsTab.add(cbFriendlyPanel, BorderLayout.SOUTH);

		final JPanel cbFriendlyGridPanel = new JPanel( );

		cbFriendlyPanel.add(cbFriendlyGridPanel);


		final JCheckBox colorBlindOnly = new JCheckBox("show only colorblind-friendly");
		colorBlindOnly.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JCheckBox source = (JCheckBox) e.getSource();
				for (ColorBlindAwareColorChooserPanel cbccp : panels) {

					cbccp.setShowColorBlindSave(source.isSelected());

					cbccp.updateChooser();

					colorChooser.repaint();

				}
			}
		});
		cbFriendlyGridPanel.add(colorBlindOnly);

		// OK, Cancel, and Reset buttons
		final JPanel buttonPanel = new JPanel( );
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		buttonPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
		colorsTab.add(buttonPanel, BorderLayout.CENTER);

		final JPanel buttonGridPanel = new JPanel( );
		buttonGridPanel.setLayout(new GridLayout(1, 3, 5, 0));
		buttonPanel.add(buttonGridPanel);


		// Reset
		final JButton resetButton = new JButton( "Reset" );
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {

				setColorBrewer(ColorPaletteChooserDialog.this.startingColorBrewer);
			}
		});
		buttonGridPanel.add( resetButton );

		// OK
		final JButton okButton = new JButton( "OK" );
		okButton.setDefaultCapable(true);
		this.getRootPane( ).setDefaultButton(okButton);
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				System.out.println("CLICKED");

				ColorPaletteChooserDialog.this.okWasPressed = true;
				ColorPaletteChooserDialog.this.setVisible(false);
			}
		});
		buttonGridPanel.add( okButton );

		// Cancel
		final JButton cancelButton = new JButton( "Cancel" );
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				setColorBrewer(ColorPaletteChooserDialog.this.startingColorBrewer);
				ColorPaletteChooserDialog.this.okWasPressed = false;
				ColorPaletteChooserDialog.this.setVisible(false);
			}
		});
		buttonGridPanel.add( cancelButton );

		this.pack( );
		this.validate( );
	}


//----------------------------------------------------------------------
//  Overridden JDialog methods
//----------------------------------------------------------------------
	/**
	 * Show the dialog box.  The dialog is automatically centered
	 * on the parent window, or on the screen if there is no parent.
	 * <P>
	 * By default, the dialog box is modal and this method call
	 * blocks until the dialog box is closed by the user pressing
	 * OK or Cancel.  The wasOKPressed() method returns true if
	 * OK was pressed.
	 *
	 * @see	#wasOKPressed()
	 */
	
	public void show( )
	{
		// Center
		final Rectangle windowDim = this.getBounds( );
		int x, y;
		if ( this.parent == null )
		{
			// Center on the screen
			final Toolkit toolkit = Toolkit.getDefaultToolkit( );
			final Dimension screenDim = toolkit.getScreenSize( );
			x = screenDim.width /2 - windowDim.width /2;
			y = screenDim.height/2 - windowDim.height/2;
		}
		else
		{
			// Center on the parent window
			final Rectangle parentDim = this.parent.getBounds( );
			x = parentDim.x + parentDim.width /2 - windowDim.width /2;
			y = parentDim.y + parentDim.height/2 - windowDim.height/2;
		}
		if ( x < 0 ) {
			x = 0;
		}
		if ( y < 0 ) {
			y = 0;
		}
		this.setLocation( x, y );

		this.okWasPressed = false;
		//this.setVisible(true);
		super.show( );
		
	}

	/**
	 * Set the background color for the window.
	 *
	 * @param	background	the new background color
	 */
	
	public void setBackground( final Color background )
	{
		// Set the dialog's background color.
		super.setBackground( background );

		// Set the inner panel's background color.
		if ( this.innerPanel != null ) {
			this.innerPanel.setBackground( background );
		}
	}





//----------------------------------------------------------------------
//  Methods
//----------------------------------------------------------------------
	/**
	 * Shows the dialog box and waits for the user to press OK or
	 * Cancel.  When either is pressed, the dialog box is hidden.
	 * A true is returned if OK was pressed, and false otherwise.
	 * <P>
	 * This method blocks until the dialog is closed by the user,
	 * regardless of whether the dialog box is modal or not.
	 *
	 * @return			true if OK was pressed
	 */
	public boolean showDialog( )
	{
		if ( this.isModal( ) )
		{
			this.show( );
			return this.okWasPressed;
		}
		this.setModal( true );
		this.show( );
		final boolean status = this.okWasPressed;
		this.setModal( false );
		return status;
	}

	/**
	 * Returns true if the OK button was pressed to close the
	 * window, and false otherwise.
	 *
	 * @return			true if OK was pressed
	 */
	public boolean wasOKPressed( )
	{
		return this.okWasPressed;
	}

	/**
	 * Get the current color in the color chooser.
	 *
	 * @return			the current color
	 */
	public Color getColor( )
	{
		return this.colorChooser.getColor( );
	}

	/**
	 * Set the current color in the color chooser.
	 *
	 * @param	color		the new color
	 */
	public void setColor( final Color color )
	{
		this.colorChooser.setColor( color );
		this.startingColor = color;
	}

	/**
	 * Set the current color in the color chooser.
	 *
	 * @param	red		the red component of the new color
	 * @param	green		the green component of the new color
	 * @param	blue		the blue component of the new color
	 */
	public void setColor( final int red, final int green, final int blue )
	{
		this.startingColor = new Color( red, green, blue );
		this.colorChooser.setColor( this.startingColor );
	}
	
	/**
	 * Get the current color in the color chooser.
	 *
	 * @return			the current color
	 */
	public ColorBrewer getColorPalette( )
	{
		ColorPanelSelectionModel model = (ColorPanelSelectionModel)colorChooser.getSelectionModel();
		return model.getColorBrewer();
	}

	/**
	 * Set the current color in the color chooser.
	 *
	 * @param	color		the new color
	 */
	public void setColorBrewer(final ColorBrewer brewer)
	{
		ColorPanelSelectionModel model = (ColorPanelSelectionModel)colorChooser.getSelectionModel();
		model.setColorBrewer(brewer);
	}
	
	public static void main(String[] args){
		ColorPaletteChooserDialog d = new ColorPaletteChooserDialog();
		d.show();
	}
}
