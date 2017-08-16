package maineditor;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class MainEditor
{
    public static void main(String[] args)
    {  
        JFrame frame = new JFrame("Circuit Editor");
        //Creates a frame with a title of "Paint it"
       
        Container content = frame.getContentPane();
        //Creates a new container
        content.setLayout(new BorderLayout());
        //sets the layout
       
        final PadDraw drawPad = new PadDraw();
        //creates a new padDraw, which is pretty much the paint program
       
        content.add(drawPad, BorderLayout.CENTER);
        //sets the padDraw in the center
       
        //for the task bar
        final JTextArea taskbar = new JTextArea();
        
        taskbar.setVisible(true);
        taskbar.setBackground(Color.lightGray);
        taskbar.setEditable(false);
        
        //adding the taskbar to the bottom-part
        content.add(taskbar,BorderLayout.SOUTH);
        
        
        JPanel panel = new JPanel();
        //creates a JPanel
        panel.setPreferredSize(new Dimension(32, 68));
        panel.setMinimumSize(new Dimension(32, 68));
        panel.setMaximumSize(new Dimension(32, 68));
        //This sets the size of the panel
       
        JButton clearButton = new JButton("CLEAR");
        clearButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                drawPad.clear();
                taskbar.setText(null);
            }
        });
       
        JButton orButton = new JButton("OR");
        orButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                drawPad.addOR();
                taskbar.setText("Click to add an OR-Gate");
            }
        });
        
        JButton wireButton = new JButton("WIRE");
        wireButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                drawPad.wire();
                taskbar.setText("Right Click to stop adding Wires.");
            }
        });
             
        panel.add(clearButton);
        panel.add(wireButton); 
        panel.add(orButton);
        //adds the buttons to the panel
       
        content.add(panel, BorderLayout.NORTH);
        //sets the panel to the upper portion
       
        frame.setSize(1000, 700);
        //sets the size of the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //makes it so you can close
        frame.setResizable(false);
        //so that u can't change the dafault size
        frame.setVisible(true);
        //makes it so you can see it
    }
}


class PadDraw extends JComponent
{
    Image image;
    
    Graphics2D graphics2D;
    //this is what we'll be using to draw on
    
    //Graphics or;
    int currentX, currentY, oldX, oldY,tmpX,tmpY;
    int xpos,ypos;
    boolean mousedragActv, desn=true, orclkactv=false;
    
    MouseListener line_connct = new MouseAdapter(){
            
            //When mouse is clicked we detect if it's right-click.
            //for right click we reset the cursor but the image unchanged
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if(e.getButton()== MouseEvent.BUTTON3)
                {
                    reset();
                    System.out.println("Right Button Clicked");
                }
                System.out.println(oldX+","+oldY+"--"+currentX+","+currentY+"=Click");
            }
            
            //At Moues-Press with Left-Click we only set the starting point
            @Override            
            public void mousePressed(MouseEvent e)
            {
                mousedragActv=false;
                if(desn && e.getButton()==MouseEvent.BUTTON1)
                {
                    oldX = e.getX();
                    oldY = e.getY();
                    desn=false;
                    System.out.println(oldX+","+oldY+"--"+currentX+","+currentY+"=Press");
                }
            }
            
            //At Mouse-Release we draw the line actually
            @Override
            public void mouseReleased (MouseEvent e) 
            {
                mousedragActv=false;
                currentX=e.getX();
                currentY=e.getY();
                System.out.println(oldX+","+oldY+"--"+currentX+","+currentY+"=Release");
                if(graphics2D != null && desn==false && e.getButton()==MouseEvent.BUTTON1)
                {
                    graphics2D.drawLine(oldX, oldY, currentX, currentY);
                    oldX=currentX;
                    oldY=currentY;
                    desn=false;
                }
                repaint();
            }
        };
    MouseMotionListener line_show= new MouseMotionAdapter(){
            @Override
            public void mouseDragged(MouseEvent e)
            {
                tmpX = e.getX();
                tmpY = e.getY();
                mousedragActv=true;
                repaint();
            }
            @Override
            public void mouseMoved(MouseEvent e)
            {
                mousedragActv=false;
            }
        };
    
    MouseListener add_or_ms= new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e)
            {
                xpos=e.getX();
                ypos=e.getY();
                System.out.println("OR CLICKED");
                graphics2D.drawArc(xpos-60, ypos-20, 120, 40, 270, 180);
                graphics2D.drawArc(xpos-30, ypos-20, 55, 40, 270, 180);
                graphics2D.drawLine(xpos+60, ypos, xpos+90, ypos);
                repaint();
            }
        };
    //Now for the constructors
    public PadDraw()
    {        
        setDoubleBuffered(false);
    }

    @Override
    public void paintComponent(Graphics g)
    {
        //System.out.println(oldX+","+oldY+"--"+currentX+","+currentY);
        if(image == null)
        {
            image = createImage(getSize().width, getSize().height);
            graphics2D = (Graphics2D)image.getGraphics();
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            clear();
        }
        g.drawImage(image, 0, 0, null);
        if(mousedragActv) g.drawLine(oldX, oldY, tmpX, tmpY);        
    }
    //this is the painting bit
    //if it has nothing on it then
    //it creates an image the size of the window
    //sets the value of Graphics as the image
    //sets the rendering
    //runs the clear() method
    //then it draws the image

    public void clear()
    {
        graphics2D.setPaint(Color.white);
        graphics2D.fillRect(0, 0, getSize().width, getSize().height);
        graphics2D.setColor(Color.black);
        reset();
        defaultmouse();
        repaint();
    }
    //this is the clear
    //it sets the colors as white
    //then it fills the window with white
    //thin it sets the color back to black
    
    public void addOR()
    {
        reset();
        this.addMouseListener(add_or_ms);
        System.out.println("In the ADD_OR");
        //this.removeMouseListener(addorbyms);
    }
    
    public void wire()
    {
        defaultmouse();
        this.addMouseListener(line_connct);
        this.addMouseMotionListener(line_show);
    }
    
    public void reset()
    {
        oldX=oldY=tmpX=tmpY=currentX=currentY=0;
        desn=true;
        defaultmouse();
    }
    
    public void defaultmouse()
    {
        this.removeMouseListener(line_connct);
        this.removeMouseMotionListener(line_show);
        this.removeMouseListener(add_or_ms);
    }
}