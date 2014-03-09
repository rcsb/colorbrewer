# Colorbrewer


> Create color blind friendly color palettes in Java.

The color palettes provided by this library are based on the colors provided by the [color brewer project] (http://colorbrewer.org/)

## How to Use

### Show a user Dialog

```java
	final ColorPaletteChooserDialog dialog = new ColorPaletteChooserDialog();
	dialog.show();
	if(dialog.wasOKPressed()) {
		Color c = dialog.getColor();
	}
```

will display this dialog:

![Screenshot of a ColorPaletteChooserDIalog](https://raw.github.com/rcsb/colorbrewer/master/doc/img/dialog.png)


### Create a palette programmatically

Get a color palette with a specific number of color:

```java
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
	
	
```

![The 'Blues' color palette](https://raw.github.com/rcsb/colorbrewer/master/doc/img/blues.png)



## Example Application

Here we are coloring the various components of the virus structure of the human poliovirus ( PDB ID [3J69](http://www.rcsb.org/pdb/explore/explore.do?structureId=3J69) ) .

![PDB ID 3J69](https://raw.github.com/rcsb/colorbrewer/master/doc/img/3J69.png)



