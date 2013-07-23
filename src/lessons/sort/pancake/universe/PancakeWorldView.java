package lessons.sort.pancake.universe;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.ImageIcon;

import jlm.core.ui.ResourcesCache;
import jlm.core.ui.WorldView;
import jlm.universe.World;

public class PancakeWorldView extends WorldView {

	private static final long serialVersionUID = 1L;
		
	public PancakeWorldView(World w) {
		super(w);
	}

	/**
	   * Draws an arrow on the given Graphics2D context
	   * ( From http://www.bytemycode.com/snippets/snippet/82/ )
	   * @param g The Graphics2D context to draw on
	   * @param x The x location of the "tail" of the arrow
	   * @param y The y location of the "tail" of the arrow
	   * @param xx The x location of the "head" of the arrow
	   * @param yy The y location of the "head" of the arrow
	   */
	  private void drawArrow( Graphics2D g, int x, int y, int xx, int yy )
	  {
	    float arrowWidth = 10.0f ;
	    float theta = 0.423f ;
	    int[] xPoints = new int[ 3 ] ;
	    int[] yPoints = new int[ 3 ] ;
	    float[] vecLine = new float[ 2 ] ;
	    float[] vecLeft = new float[ 2 ] ;
	    float fLength;
	    float th;
	    float ta;
	    float baseX, baseY ;

	    xPoints[ 0 ] = xx ;
	    yPoints[ 0 ] = yy ;

	    // build the line vector
	    vecLine[ 0 ] = (float)xPoints[ 0 ] - x ;
	    vecLine[ 1 ] = (float)yPoints[ 0 ] - y ;

	    // build the arrow base vector - normal to the line
	    vecLeft[ 0 ] = -vecLine[ 1 ] ;
	    vecLeft[ 1 ] = vecLine[ 0 ] ;

	    // setup length parameters
	    fLength = (float)Math.sqrt( vecLine[0] * vecLine[0] + vecLine[1] * vecLine[1] ) ;
	    th = arrowWidth / ( 2.0f * fLength ) ;
	    ta = arrowWidth / ( 2.0f * ( (float)Math.tan( theta ) / 2.0f ) * fLength ) ;

	    // find the base of the arrow
	    baseX = ( (float)xPoints[ 0 ] - ta * vecLine[0]);
	    baseY = ( (float)yPoints[ 0 ] - ta * vecLine[1]);

	    // build the points on the sides of the arrow
	    xPoints[ 1 ] = (int)( baseX + th * vecLeft[0] );
	    yPoints[ 1 ] = (int)( baseY + th * vecLeft[1] );
	    xPoints[ 2 ] = (int)( baseX - th * vecLeft[0] );
	    yPoints[ 2 ] = (int)( baseY - th * vecLeft[1] );

	    g.drawLine( x, y, (int)baseX, (int)baseY ) ;
	    g.fillPolygon( xPoints, yPoints, 3 ) ;
	  }
	
	/**
	 * Draw the marker for the flipping
	 * @param g2 : the Graphics2D context to draw on
	 * @param xMin : where the arrows should start in the horizontal plan
	 * @param xMax : where the arrows should stop in the horizontal plan
	 * @param renderedX : where the lines should stop in the horizontal plan
	 */
	private void drawMarker(Graphics2D g2, int xMin, int xMax, int renderedX) {
		PancakeWorld myWorld = (PancakeWorld) this.world;
		
		int yTop = 248 - 8*myWorld.getStackSize();
		int yBottom = 240 - 8 * ( myWorld.getStackSize() - myWorld.getLastModifiedPancake()) ;
		
		int yArrow = Math.max(yTop, yBottom);
	
		Color colors[] = new Color[2];
		if ( myWorld.isFlipped())
		{
			colors[0]=Color.blue;
			colors[1]=Color.red;
		}
		else
		{
			colors[0]=Color.red;
			colors[1]=Color.blue;
		}
		g2.setColor(colors[0]);
		g2.drawLine(xMax, yTop, renderedX, yTop);
		drawArrow(g2,xMin,yTop,xMax,yTop);
		g2.setColor(colors[1]);
		g2.drawLine(xMax, yArrow, renderedX, yArrow);
		drawArrow(g2,xMin,yArrow,xMax,yArrow);
	}

	
	/**
	 * Draw a raw pancake
	 * @param g2 : an entity of the Graphics2D class
	 * @param xoffset : the horizontal offset 
	 * @param radius : pancake size
	 * @param rank : pancake position in the stack
	 */
	private void drawPancake(Graphics2D g2, double xoffset, int radius, int rank) {
		g2.setColor(Color.YELLOW);
		g2.fill(new Rectangle2D.Double( xoffset-radius*5-3, 236-(8.*(rank)),  radius*10+3, 8));
		g2.setColor(Color.black);
		g2.draw(new Rectangle2D.Double( xoffset-radius*5-3, 236-(8.*(rank)),  radius*10+3, 8));
	}
	
	/**
	 * Draw a pancake with a burned face
	 * @param g2 : an entity of the Graphics2D class
	 * @param xoffset : the horizontal offset 
	 * @param radius : pancake size
	 * @param rank : pancake position in the stack
	 */
	private void drawBurnedSide(Graphics2D g2, double xoffset,int radius, int rank, boolean upsideDown) {
		g2.setColor(new Color(91, 59, 17)); // it's brown
		
		if (upsideDown) 
			g2.fill(new Rectangle2D.Double( xoffset-radius*5-3, 236-(8.*(rank)),  radius*10+3, 3));
		else 
			g2.fill(new Rectangle2D.Double( xoffset-radius*5-3, 241-(8.*(rank)),  radius*10+3, 3));
	}
	
	/**
	 * Draw some plate
	 * @param g2 : an entity of the Graphics2D class
	 * @param xoffset : the horizontal offset 
	 * @param amountOfPancakes: the total amount of pancakes
	 */
	private void drawPlate(Graphics2D g2, double xoffset, int amountOfPancakes) {
		g2.draw(new Rectangle2D.Double( 0, 244., xoffset*2, 5));
	}
	
	/**
	 * Draw the plate and the stack of pancakes
	 * @param g2 : an entity of the Graphics2D class
	 * @param xoffset : the horizontal offset 
	 */
	private void drawStack(Graphics2D g2, double xoffset) {
		PancakeWorld w= (PancakeWorld) this.world;
		boolean burned = w.isBurnedPancake();
		int amountOfPancakes = w.getStackSize();

		g2.setColor(Color.black);
		drawPlate(g2,xoffset,amountOfPancakes);

		for (int rank = 0; rank<amountOfPancakes ;rank++) {
			drawPancake(g2,xoffset,w.getPancakeRadius(amountOfPancakes-rank-1),rank);
			if (burned)
				drawBurnedSide(g2,xoffset,w.getPancakeRadius(amountOfPancakes-rank-1),rank,w.isPancakeUpsideDown(amountOfPancakes-rank-1));
		}
		for (int rank = 0; rank<amountOfPancakes ;rank++) 
			if (amountOfPancakes-rank-2>=0 
			&& Math.abs(w.getPancakeRadius(amountOfPancakes-rank-1)-w.getPancakeRadius(amountOfPancakes-rank-2)) == 1  
			&& (!burned || w.isPancakeUpsideDown(amountOfPancakes-rank-1) == w.isPancakeUpsideDown(amountOfPancakes-rank-2))) {
				g2.setColor(Color.magenta);
				g2.fill(new Ellipse2D.Double(xoffset-4, 236-(8.*(rank)+3), 6,6) );
			}
	}
	
	/**
	 * Return the icon of the world
	 * @return the icon for the exercise selection
	 */
	// http://omgwtflols.deviantart.com/
	// http://fc06.deviantart.net/fs71/f/2012/118/5/7/pixel_art__pancakes_with_s236rup_b236_omgwtflols-d4xu72c.gif
	// http://omgwtflols.deviantart.com/art/Pixel-Art-Pancakes-with-syrup-298700868
	public ImageIcon getIcon() {
		return ResourcesCache.getIcon(this,"../img/world_pancake.png");
	}

	/**
	 * Draw the component of the world
	 * @param g : some Graphics
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		double renderedX = 310.;
		double rendered236 = 250.;		
		double ratio = Math.min(((double) getWidth()) / renderedX, ((double) getHeight()) / rendered236);
		g2.translate(Math.abs(getWidth() - ratio * renderedX)/2., Math.abs(getHeight() - ratio * rendered236)/2.);
		g2.scale(ratio, ratio);
		
		/* clear board */
		g2.setColor(Color.white);
		g2.fill(new Rectangle2D.Double(0., 0., renderedX, rendered236));
		
		drawMarker(g2, -20, 0,(int) renderedX);
		drawStack( g2,  155.);
	}
	
	
	
}
