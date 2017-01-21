package circles;

/*
*Kyle Hewitt
*CS 2050
*Program 3
*This creates a Circles object with a random radius and color.
*/

import javax.swing.*;
import java.awt.*;
import java.util.Random;
/**
 * Creates a new circle object with a random radius and random color values for 
 * red green and blue.
 *
 * @author Kyle
 */
public class Circles extends JPanel {
     Random rand = new Random();
     int r = rand.nextInt(500)+10;
     int red = rand.nextInt(255);
     int green = rand.nextInt(255);
     int blue = rand.nextInt(255);
     int x,y;
     /**
      * Takes location and sets class variables to their value.
      * 
      * @param x x coordinates
      * @param y y coordinates
      */
    public Circles(int x, int y){
       this.x = x;
       this.y = y;
    }
    
    public Circles(){
    }
    /**
     * Takes the randomly generated values for radius and color and sets them
     * to the fillOval command. The x and y values are altered to make the cirlce's 
     * center at the x and y location instead of the upper left corner.
     * 
     * @param g Graphics object
     */
    public void draw(Graphics g) {
        g.setColor(new Color(red,green,blue));
        g.fillOval((x-(r/2)), (y-(r/2)), r, r);
         
        }
}
