package circles;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
/*
* Kyle Hewitt
* CS 2050
* Project 3
*/
/**
*This program gives the user the choice of three radio butttons; One for drawing
*circles, one for drawing rectangles, and one for drawing lines. It also has a 
*button to clear and a button to close. 
*/
public class MouseTest {

	private JFrame frame; //Creates Frame
        private JPanel top, bottom; //JPanels for holding buttons
        private JButton clear,close;//Top JButtons
        private MyPanel pane;//Instance to the MyPanel inner class
        private Container cp; //container 
	private boolean tracking = false; //Conditional for graphics(lines)
        private boolean clicked = false; //Conditional for graphics(circles)
        private boolean rectangle = false;//Conditional for graphics(rectangles)
	private int xstart;//The starting x coordinates for drawing rectangles				
	private int ystart;//The starting y coordinates for rectangles				
	private int xend;//The ending x coordinates for drawing rectangles			
	private int yend;//The ending y coordinates for drawing rectangles				
	private  int borderWidth = 5;//setting borderwidth value
        private int x,y;//Coordinates for drawing circles
        private int index = 0;//Index counter
        private ArrayList <Circles> c = new ArrayList();//ArrayList to hold Circles objects
        private ArrayList <Point>  myLines= new ArrayList();//ArrayList to hold Lines objects
        private ButtonGroup group = new ButtonGroup();//Button group for radio buttons
        private JRadioButton myCircles = new JRadioButton("Draw Circles");//Radio button for circles
        private JRadioButton rectangles = new JRadioButton("Draw Rectangles");//Radio button for rectangles
        private JRadioButton lines = new JRadioButton("Draw Lines");//Radio button for lines
        private int trackX, trackY;//used to track the x and y coordinates of the cruiser
        private int trackX2 = 0;//used to track the x and y coordinates of the cruiser
        private int trackY2 = 0;//used to track the x and y coordinates of the cruiser
        private Circles object = new Circles();
        
/////////////////////////////////////////////////////////////////////////////        
        
    public static void main (String [] arg) {
            MouseTest first = new MouseTest();
    }//main

/////////////////////////////////////////////////////////////////////////////        
    /**
     * Constructor takes no parameters and builds GUI, adding all of the components
     * to the frame.
     */
    public MouseTest() {
            frame = new JFrame();
            frame.setBounds(500, 10, 1000, 1000);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setTitle("Making Shapes");
            cp = frame.getContentPane();

            top = new JPanel();
            bottom = new JPanel();
            pane = new MyPanel();

            clear = new JButton("Clear");
            close = new JButton("Close");

            top.add(clear);
            top.add(close);
            bottom.add(myCircles);
            bottom.add(rectangles);
            bottom.add(lines);

            group.add(myCircles);
            group.add(rectangles);
            group.add(lines);
            group.add(clear);

            cp.add(top,BorderLayout.NORTH);
            cp.add(bottom,BorderLayout.SOUTH);
            cp.add(pane,BorderLayout.CENTER);
            mouseClicks();
    }//constructor
    
////////////////////////////////////////////////////////////////////////////    
    /**
     * mouseClicks adds listeners to each of the buttons and sets the 
     * paint component conditional statement that is needed to true and calls
     * the method that performs the desired function.
     */
    public void mouseClicks(){

            myCircles.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    clicked = true;//sets to true so it runs the circles graphic
                    tracking = false;
                    rectangle = false;
                    myCircles();
                }
            });

            rectangles.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    rectangle = true;//sets to true so it runs the rectangle graphic
                    tracking = false;
                    clicked = false;
                    rectangle();
                }
            });

            lines.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    clicked = false;
                    rectangle = false;
                    tracking = true;//sets to true so it runs the lines graphic
                    tracking();
                }
            });
            //clear resets all the conditional statements to false causing 
            //the graphics to clear.
            clear.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        tracking = false;
                        clicked = false;
                        rectangle = false;
                        c.removeAll(c);
                        myLines.removeAll(myLines);
                        frame.repaint();
                    }
            });
            //closes the program
            close.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                            System.exit(0);
                    }
            });
        frame.setVisible(true);
    }        

/////////////////////////////////////////////////////////////////////////////       

    /**
     * Uses a mouseListener to listen for a mousePressed and mouseReleased
     * event. When pressed, the x and y values are recorded and when released
     * the x and y values are recorded. The method then uses the values to 
     * create a graphic rectangle starting when the mousePressed was activated
     * and ending where the mouseRelease was activated.
     */
    public void rectangle(){
       
        pane.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {

                        xstart = e.getX();
                        ystart = e.getY();
                }
                public void mouseReleased(MouseEvent e) {
                        xend = e.getX();
                        yend = e.getY();
                        if (xend < xstart) { int tmp = xstart; xstart = xend; xend = tmp; }
                        if (yend < ystart) { int tmp = ystart; ystart = yend; yend = tmp; }
                        frame.repaint();
                }
        });
    }

////////////////////////////////////////////////////////////////////////////        
    /**
     * Uses a mouseListener to listen for a mouseClicked event. The x and y values
     * of the location of the click are recorded and used to create a Circles object
     * at random.
     */
    public void myCircles(){

        pane.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                x = e.getX();
                y = e.getY();
                object = new Circles(x,y);//Creates Circles object
                c.add(object);//Adds Circles object to ArrayList
                System.out.println("x: "+x+" y: "+y);
                frame.repaint();
            }  
        });

    }

///////////////////////////////////////////////////////////////////////////////            
    /**
     * Uses a mouseMotionListener to track the location of the cruiser.
     */
    public void tracking(){

        pane.addMouseMotionListener(new MouseMotionAdapter() {
                public void mouseMoved(MouseEvent e) {
                    if (tracking) {
                                y = e.getY();
                                x = e.getX();
                                Point myPoint = new Point(x,y);
                                myLines.add(myPoint);
                                msg("(" + x + ", " + y + ")");
                                frame.repaint();
                        }
                }
        });
    }

///////////////////////////////////////////////////////////////////////////////
    /**
     * Takes a String and prints it to the console on a new line.
     * 
     * @param s A String that is to be printed on a new line. 
     */
    public void msg(String s) {
            System.out.println(s);
    }

//////////////////////////////////////////////////////////////////////////////
       
public class MyPanel extends JPanel {
    
    /**
     * Called when told to repaint, paintComponent checks the boolean conditional
     * statements rectangle, clicked, and lines in the MouseTest class and creates
     * a shape dependant on the uses choice using radio buttons. 
     * 
     * @param g Graphics object used to draw.
     */
	public void paintComponent(Graphics g) {
		int width  = this.getWidth();
		int height = this.getHeight();
		msg("H = " + height + ",  w = " + width);
		g.setColor(Color.BLACK);
		for (int delta = 0; delta < borderWidth; delta++) {
			g.drawRect(delta,delta,width-(2*delta),height-(2*delta));
		}
		if (xstart != xend || ystart != yend) {
                    if(rectangle){
			int red		= (int)(256*Math.random());
			int green	= (int)(256*Math.random());
			int blue	= (int)(256*Math.random());
			g.setColor(new Color(red, green, blue));
			msg("Colors are:  " + red + " - " + green + " - " + blue );
			msg("Drawing from: (" + xstart + ", " + ystart + ") to (" + xend + ", " + yend + ")");
			msg("Width is " + (xend-xstart) + " -  Height is " + (yend-ystart));
			g.fillRect(xstart,ystart,xend-xstart,yend-ystart);
                    }
                }
                
                
                if (clicked){
                       
                    for (Circles p : c){    
                               p.draw(g);                        
                    }
                }
               
                if(tracking){
                   g.setColor(Color.BLACK);
                   Point last = null;
                   for (Point p : myLines){
                       if(last != null){
                           g.drawLine(last.x, last.y, p.x, p.y);
                       }
                    last = p;
                   }
                  
                }
            
            }
	}
}