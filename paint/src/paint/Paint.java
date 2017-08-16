package paint;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

public class Paint
{
    public static void main(String[] args)
    {
        new Paint();
    }
    Paint()
    {
       try 
       {
        UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName() );
       } 
       catch (Exception e) 
       {
           e.printStackTrace();
       }
   
       final JFrame frame = new JFrame("MY PAINT APPLICATION");
       Container content = frame.getContentPane();
    
       //object for drawing pad
       final PadDraw drawPad = new PadDraw();
       // sets the padDraw in the center
       content.add(drawPad, BorderLayout.CENTER);
      
       // This sets the size of the panel(side panel where rubber and clear is present)
        final JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(85, 85));
        panel.setMinimumSize(new Dimension(85, 85));
        panel.setMaximumSize(new Dimension(85, 85));
        panel.setBackground(Color.LIGHT_GRAY);
        
        //menu bar
        JMenu fileMenu,editMenu;
        JMenuItem newFile, openFile, saveFile, saveAsFile,printFile, exit;
        JMenuItem undoEdit, redoEdit, selectAll, copy, paste, cut;
        UndoManager undo;
        
        JMenuBar menuBar = new JMenuBar();
        
        fileMenu=new JMenu("FILE");
        fileMenu.setPreferredSize(new Dimension(100,25));
        editMenu=new JMenu("EDIT");
        editMenu.setPreferredSize(new Dimension(100,25));
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        
        newFile=new JMenuItem("New");
        openFile=new JMenuItem("Open");
        saveFile=new JMenuItem("Save");
        saveAsFile=new JMenuItem("Save As");
        printFile=new JMenuItem("Print");
        exit=new JMenuItem("Exit");
        
        newFile.setPreferredSize(new Dimension(100, 25));
        openFile.setPreferredSize(new Dimension(100, 25));
        saveFile.setPreferredSize(new Dimension(100, 25));
        saveAsFile.setPreferredSize(new Dimension(100, 25));
        printFile.setPreferredSize(new Dimension(100, 25));
        exit.setPreferredSize(new Dimension(100, 25));
        
        fileMenu.add(newFile);
        fileMenu.add(openFile);
        fileMenu.add(saveFile);
        fileMenu.add(saveAsFile);
        fileMenu.add(printFile);
        fileMenu.add(exit);
        
        undoEdit=new JMenuItem("Undo");
        redoEdit=new JMenuItem("Redo");
        
        undoEdit.setPreferredSize(new Dimension(100, 25));
        redoEdit.setPreferredSize(new Dimension(100, 25));
        
        editMenu.add(undoEdit);
        editMenu.add(redoEdit);
        
        frame.setJMenuBar(menuBar);
        
        newFile.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                new Paint();
            }
        });
        openFile.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                JFileChooser open=new JFileChooser();
                open.showOpenDialog(null);
                File filename=open.getSelectedFile();
                drawPad.open(filename);
                frame.setTitle("MY PAINT APPLICATION - "+filename.getName());
            }
        });
        saveFile.addActionListener(new ActionListener() 
        {
           @Override
           public void actionPerformed(ActionEvent e) 
           {
                JFileChooser save= new JFileChooser();
                save.showSaveDialog(null);
                File filename = save.getSelectedFile();
                int confirmationResult;
                if(filename.exists()) 
                {
                    confirmationResult = JOptionPane.showConfirmDialog(save, "Replace existing file?");
                    if(confirmationResult == JOptionPane.YES_OPTION) 
                    {
                        drawPad.save(panel,filename); 
                        frame.setTitle("MY PAINT APPLICATION - "+filename.getName());
                    }
                } 
                else 
                {
                    drawPad.save(panel,filename);
                    frame.setTitle("MY PAINT APPLICATION - "+filename.getName());
                }
          }
        });
        
        saveAsFile.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                JFileChooser saveAs = new JFileChooser();
                saveAs.showSaveDialog(null);
                File filename = saveAs.getSelectedFile();
                int confirmationResult;
                if(filename.exists()) 
                {
                    confirmationResult = JOptionPane.showConfirmDialog(saveAs, "Replace existing file?");
                    if(confirmationResult == JOptionPane.YES_OPTION) 
                    {
                        drawPad.save(panel,filename); 
                        frame.setTitle("MY PAINT APPLICATION - "+filename.getName());
                    }
                } 
                else 
                {
                    drawPad.save(panel,filename);
                    frame.setTitle("MY PAINT APPLICATION - "+filename.getName());
                }
            }
        });
        
        printFile.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                PrinterJob job;
                //to coordinate the printing
                job = PrinterJob.getPrinterJob();
                // displays a dialog box, giving the user the opportunity to specify the printer to use and the number of copies to print.
                if(job.printDialog()) 
                {
                    try 
                    {
                        job.print();
                    }
                    catch (PrinterException err) 
                    {
                        err.printStackTrace();
                    }
                }
            }
        });
        
        exit.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        });
        
        
        
        JButton clearButton = new JButton("Clear");
        clearButton.setForeground(Color.BLACK);
        //not to set blue ring around CLEAR 
        clearButton.setFocusPainted(false);
        clearButton.setSize(135,20);
        clearButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                drawPad.clear();
            }
        });
        
        ImageIcon rubber=new ImageIcon("src/resources/rubber.jpg");
        JButton Rubber = new JButton(rubber);
        Rubber.setFocusPainted(false);
        Rubber.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                drawPad.Rubber();
            }
        });
    
        ImageIcon square=new ImageIcon("src/resources/square.jpg");
        JButton drawrect = new JButton(square);
        drawrect.setFocusPainted(false);
        drawrect.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                drawPad.rectangle();
            }
        });
    
        ImageIcon circle=new ImageIcon("src/resources/circle.jpg");
        JButton drawcircle = new JButton(circle);
        drawcircle.setFocusPainted(false);
        drawcircle.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                drawPad.circle();
            }
        });
    
        ImageIcon line=new ImageIcon("src/resources/line.jpg");
        JButton drawline = new JButton(line);
        drawline.setFocusPainted(false);
        drawline.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                drawPad.line();
            }
        });
    
        ImageIcon pencil=new ImageIcon("src/resources/pencil.jpg");
        JButton Pencil = new JButton(pencil);
        Pencil.setFocusPainted(true);
        Pencil.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                drawPad.pencil();
            }
        });
    
        //for color
        JButton redButton = new JButton();
        redButton.setBackground(Color.red);
        redButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                drawPad.red();
            }
        });
    
        JButton darkRedButton = new JButton();
        darkRedButton.setBackground(new Color(176,23,31));
        darkRedButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                drawPad.darkred();
            }
        });

        JButton lightRedButton = new JButton();
        lightRedButton.setBackground(new Color(238,99,99));
        lightRedButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                drawPad.lightred();
            }
        });

        JButton blackButton = new JButton();
        blackButton.setBackground(Color.black);
        blackButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                drawPad.black();
            }
        });

        JButton greyButton = new JButton();
        greyButton.setBackground(new Color(139,137,137));
        greyButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                drawPad.grey();
            }
        });

        JButton magentaButton = new JButton();
        magentaButton.setBackground(Color.magenta);
        magentaButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                drawPad.magenta();
            }
        });

        JButton blueButton = new JButton();
        blueButton.setBackground(Color.blue);
        blueButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                drawPad.blue();
            }
        });

        JButton midBlueButton = new JButton();
        midBlueButton.setBackground(new Color(49,132,226));
        midBlueButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                drawPad.midblue();
            }
        });

        JButton darkGreenButton = new JButton();
        darkGreenButton.setBackground(new Color(36,158,6));
        darkGreenButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                drawPad.darkgreen();
            }
        });

        JButton greenButtonTwo = new JButton();
        greenButtonTwo.setBackground(new Color(170,255,130));
        greenButtonTwo.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                drawPad.secondgreen();
            }
        });

        JButton greenButton = new JButton();
        greenButton.setBackground(Color.green);
        greenButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                drawPad.green();
            }
        });

        JButton orangeButton = new JButton();
        orangeButton.setBackground(Color.orange);
        orangeButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                drawPad.orange();
            }
        });

        JButton midOrangeButton = new JButton();
        midOrangeButton.setBackground(new Color(248,148,21));
        midOrangeButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                drawPad.midorange();
            }
        });

        JButton darkOrangeButton = new JButton();
        darkOrangeButton.setBackground(new Color(206,112,11));
        darkOrangeButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                drawPad.darkorange();
            }
        });

        JButton yellowButton = new JButton();
        yellowButton.setBackground(Color.yellow);
        yellowButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                drawPad.yellow();
            }
        });

        JButton cyanButton = new JButton();
        cyanButton.setBackground(Color.cyan);
        cyanButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                drawPad.cyan();
            }
        });

        JButton pinkButton = new JButton();
        pinkButton.setBackground(Color.pink);
        pinkButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                drawPad.pink();
            }
        });

        //Brush size buttons
        JButton one = new JButton("1");
        one.setFocusPainted(false);
        one.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                drawPad.oneset();
            }
        });

        JButton two = new JButton("2");
        two.setFocusPainted(false);
        two.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                drawPad.twoset();
            }
        });

        JButton three = new JButton("3");
        three.setFocusPainted(false);
        three.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                drawPad.threeset();
            }
        });

        JButton four = new JButton("4");
        four.setFocusPainted(false);
        four.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                drawPad.fourset();
            }
        });

        JButton five = new JButton("5");
        five.setFocusPainted(false);
        five.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                drawPad.fiveset();
            }
        });

        JButton six = new JButton("6");
        six.setFocusPainted(false);
        six.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                drawPad.sixset();
            }
        });

        JButton seven = new JButton("7");
        seven.setFocusPainted(false);
        seven.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                drawPad.sevenset();
            }
        });
    
        JButton eight = new JButton("8");
        eight.setFocusPainted(false);
        eight.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                drawPad.eightset();
            }
        });

        JButton nine = new JButton("9");
        nine.setFocusPainted(false);
        nine.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                drawPad.nineset();
            }
        });

        JButton ten = new JButton("10");
        ten.setFocusPainted(false);
        ten.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                drawPad.tenset();
            }
        });

        JButton eleven = new JButton("11");
        eleven.setFocusPainted(false);
        eleven.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                drawPad.elevenset();
            }
        });

        JButton twelve = new JButton("12");
        twelve.setFocusPainted(false);
        twelve.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                drawPad.twelveset();
            }
        });

        JButton thirteen = new JButton("13");
        thirteen.setFocusPainted(false);
        thirteen.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                drawPad.thirteenset();
            }
        });

        JButton fourteen = new JButton("14");
        fourteen.setFocusPainted(false);
        fourteen.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                drawPad.fourteenset();
            }
        });

        JButton fifteen = new JButton("15");
        fifteen.setFocusPainted(false);
        fifteen.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                drawPad.fifteenset();
            }
        });

        JButton sixteen = new JButton("16");
        sixteen.setFocusPainted(false);
        sixteen.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                drawPad.sixteenset();
            }
        });

        JButton seventeen = new JButton("17");
        seventeen.setFocusPainted(false);
        seventeen.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                drawPad.seventeenset();
            }
        });

        JButton eighteen = new JButton("18");
        eighteen.setFocusPainted(false);
        eighteen.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                drawPad.eighteenset();
            }
        });
    
        JButton nineteen = new JButton("19");
        nineteen.setFocusPainted(false);
        nineteen.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                drawPad.nineteenset();
            }   
        });
    
        JButton tweenty = new JButton("20");
        tweenty.setFocusPainted(false);
        tweenty.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                drawPad.twentyset();
            }
        });
    
        //fill butons
        JButton fillBlack = new JButton();
        fillBlack.setBackground(Color.black);
        fillBlack.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                drawPad.fillBlack();
            }
        });

        JButton fillMagenta = new JButton();
        fillMagenta.setBackground(Color.magenta);
        fillMagenta.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                drawPad.fillMagenta();
            }
        });

        JButton fillRed = new JButton();
        fillRed.setBackground(Color.red);
        fillRed.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                drawPad.fillRed();
            }
        });

        JButton fillBlue = new JButton();
        fillBlue.setBackground(Color.blue);
        fillBlue.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                drawPad.fillBlue();
            }
        });

        JButton fillGreen = new JButton();
        fillGreen.setBackground(Color.green);
        fillGreen.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                drawPad.fillGreen();
            }
        });

    JButton fillOrange = new JButton();
    fillOrange.setBackground(Color.orange);
    fillOrange.addActionListener(new ActionListener() 
    {
        public void actionPerformed(ActionEvent e) 
        {
            drawPad.fillOrange();
        }
    });

    JButton fillYellow = new JButton();
    fillYellow.setBackground(Color.yellow);
    fillYellow.addActionListener(new ActionListener() 
    {
        public void actionPerformed(ActionEvent e) 
        {
            drawPad.fillYellow();
        }
    });

    JButton fillCyan = new JButton();
    fillCyan.setBackground(Color.cyan);
    fillCyan.addActionListener(new ActionListener() 
    {
        public void actionPerformed(ActionEvent e) 
        {
            drawPad.fillCyan();
        }
    });

    JButton fillPink = new JButton();
    fillPink.setBackground(Color.pink);
    fillPink.addActionListener(new ActionListener() 
    {
        public void actionPerformed(ActionEvent e) 
        {
            drawPad.fillPink();
        }
    });

    

    JLabel colourSign = new JLabel("COLOR:");
    JLabel sizeSign = new JLabel(" STROKE:");
    JLabel fillSign = new JLabel("FILL:");
    JLabel spacer = new JLabel(" ");
    
    colourSign.setPreferredSize(new Dimension(70, 22));
    
    blackButton.setPreferredSize(new Dimension(34, 30));
    greyButton.setPreferredSize(new Dimension(34, 30));
    magentaButton.setPreferredSize(new Dimension(34, 30));
    darkRedButton.setPreferredSize(new Dimension(34, 30));
    redButton.setPreferredSize(new Dimension(34, 30));
    lightRedButton.setPreferredSize(new Dimension(34, 30));
    blueButton.setPreferredSize(new Dimension(34, 30));
    midBlueButton.setPreferredSize(new Dimension(34, 30));
    darkGreenButton.setPreferredSize(new Dimension(34, 30));
    greenButton.setPreferredSize(new Dimension(34, 30));
    greenButtonTwo.setPreferredSize(new Dimension(34, 30));
    darkOrangeButton.setPreferredSize(new Dimension(34, 30));
    midOrangeButton.setPreferredSize(new Dimension(34, 30));
    orangeButton.setPreferredSize(new Dimension(34, 30));
    yellowButton.setPreferredSize(new Dimension(34, 30));
    cyanButton.setPreferredSize(new Dimension(34, 30));
    pinkButton.setPreferredSize(new Dimension(34, 30));


    sizeSign.setPreferredSize(new Dimension(70,22));
    
    one.setPreferredSize(new Dimension(70,25));
    two.setPreferredSize(new Dimension(70,25));
    three.setPreferredSize(new Dimension(70,25));
    four.setPreferredSize(new Dimension(70,25));
    five.setPreferredSize(new Dimension(70,25));
    six.setPreferredSize(new Dimension(70,25));
    seven.setPreferredSize(new Dimension(70,25));
    eight.setPreferredSize(new Dimension(70,25));
    nine.setPreferredSize(new Dimension(70,25));
    ten.setPreferredSize(new Dimension(70,25));
    eleven.setPreferredSize(new Dimension(70,25));
    twelve.setPreferredSize(new Dimension(70,25));
    thirteen.setPreferredSize(new Dimension(70,25));
    fourteen.setPreferredSize(new Dimension(70,25));
    fifteen.setPreferredSize(new Dimension(70,25));
    sixteen.setPreferredSize(new Dimension(70,25));
    seventeen.setPreferredSize(new Dimension(70,25));
    eighteen.setPreferredSize(new Dimension(70,25));
    nineteen.setPreferredSize(new Dimension(70,25));
    tweenty.setPreferredSize(new Dimension(70,25));



    fillSign.setPreferredSize(new Dimension(70, 22));
    
    fillBlack.setPreferredSize(new Dimension(34, 30));
    fillMagenta.setPreferredSize(new Dimension(34, 30));
    fillRed.setPreferredSize(new Dimension(34, 30));
    fillBlue.setPreferredSize(new Dimension(34, 30));
    fillGreen.setPreferredSize(new Dimension(34, 30));
    fillOrange.setPreferredSize(new Dimension(34, 30));
    fillYellow.setPreferredSize(new Dimension(34, 30));
    fillCyan.setPreferredSize(new Dimension(34, 30));
    fillPink.setPreferredSize(new Dimension(34, 30));


    Rubber.setPreferredSize(new Dimension(34,30));
    Pencil.setPreferredSize(new Dimension(34,30));
    drawrect.setPreferredSize(new Dimension(34,30));
    drawcircle.setPreferredSize(new Dimension(34,30));
    drawline.setPreferredSize(new Dimension(34,30));
    
    clearButton.setPreferredSize(new Dimension(75,50));
    spacer.setPreferredSize(new Dimension(80,5));
    
    //left side panel
    
    panel.add(clearButton);
    panel.add(Rubber);
    panel.add(Pencil);
    panel.add(drawrect);
    panel.add(drawcircle);
    panel.add(drawline);
    
    panel.add(colourSign);
    
    panel.add(blackButton);
    panel.add(greyButton);
    panel.add(darkGreenButton);
    panel.add(greenButton);
    panel.add(greenButtonTwo);
    panel.add(blueButton);
    panel.add(midBlueButton);
    panel.add(cyanButton);
    panel.add(darkOrangeButton);
    panel.add(midOrangeButton);
    panel.add(orangeButton);
    panel.add(magentaButton);
    panel.add(pinkButton);
    panel.add(darkRedButton);
    panel.add(redButton);
    panel.add(lightRedButton);

    panel.add(fillSign);
    
    panel.add(fillBlack);
    panel.add(fillMagenta);
    panel.add(fillRed);
    panel.add(fillBlue);
    panel.add(fillPink);
    panel.add(fillGreen);
    panel.add(fillOrange);
    panel.add(fillCyan);
    
    
   
    content.add(panel,BorderLayout.WEST);
    
    //right side panel//
  
    JPanel fourthpanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    fourthpanel.setPreferredSize(new Dimension(80,70));
    fourthpanel.setMinimumSize(new Dimension(40, 28));
    fourthpanel.setMaximumSize(new Dimension(40, 28));
    fourthpanel.setBackground(Color.LIGHT_GRAY);
    content.add(fourthpanel, BorderLayout.EAST);

    fourthpanel.add(spacer);
    fourthpanel.add(sizeSign);
    
    fourthpanel.add(one);
    fourthpanel.add(two);
    fourthpanel.add(three);
    fourthpanel.add(four);
    fourthpanel.add(five);
    fourthpanel.add(six);
    fourthpanel.add(seven);
    fourthpanel.add(eight);
    fourthpanel.add(nine);
    fourthpanel.add(ten);
    fourthpanel.add(eleven);
    fourthpanel.add(twelve);
    fourthpanel.add(thirteen);
    fourthpanel.add(fourteen);
    fourthpanel.add(fifteen);
    fourthpanel.add(sixteen);
    fourthpanel.add(seventeen);
    fourthpanel.add(eighteen);
    fourthpanel.add(nineteen);
    fourthpanel.add(tweenty);
    
    //set default button
    JRootPane root=frame.getRootPane();
    root.setDefaultButton(Pencil);
    
    //frame size
    frame.setSize(1365,725);
    //so as to close
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //make it visible
    frame.setVisible(true);
    //so that maximize nd minimize button works
    frame.setResizable(true);
    frame.setLocationRelativeTo(null);
   }
 }

 class PadDraw extends JComponent 
 {
        //this is the image that we draw on
        Image image;
        //this is what we'll be using to draw on
        Graphics2D graphics2D;
        //for mouse coordinates
        int currentX, currentY, oldX, oldY,tmpX,tmpY;
        
        public void open(File filename)
        {
            BufferedImage image=null;
            try
            {
                image=ImageIO.read(filename);
                if(image!=null)
                {
                   graphics2D.drawImage(image,0,0,null);
                }
            }
            catch (IOException e) 
            {
                System.out.println("Image could not be read");
            }
        }
        public void save(JPanel panel,File filename)
        {
            BufferedImage img=new BufferedImage(panel.getWidth(),panel.getHeight(), BufferedImage.TYPE_INT_RGB);
            try
            {
               ImageIO.write(img, "jpg",filename);
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
        }
            
        MouseListener pressing=new MouseAdapter()
        {
            public void MouseClicked(MouseEvent e)
            {
                oldX=oldY=tmpX=tmpY=currentX=currentY=0;
            }
            public void mousePressed(MouseEvent e)
            {
               oldX = e.getX();
               oldY = e.getY();
            }
            public void mouseReleased(MouseEvent e)
            {
               tmpX = e.getX();
               tmpY = e.getY();
            }
        };
        
        MouseMotionListener dragging=new MouseMotionAdapter()
        {
            public void mouseDragged(MouseEvent e)
            {
                currentX = e.getX();
                currentY = e.getY();
                if(graphics2D != null)
                   graphics2D.drawLine(oldX, oldY, currentX, currentY);
                repaint();
                oldX = currentX;
                oldY = currentY;
            }
        };
           
       //brush colors//
        //it creates an image sets the value of Graphics as the image sets the rendering and runs the clear() method then it draws image
        public void paintComponent(Graphics g)
        {
            // This stuff is standard, and should be in any paintComponent method.
            if(image == null)
            {
                image = createImage(getSize().width, getSize().height);
                graphics2D = (Graphics2D)image.getGraphics();
                graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                clear();
            }
            g.drawImage(image, 0, 0, null);
        }
        
        //method for clear
        //it sets the colors as white then it fills the window with white then it sets the color back to black
        public void clear()
        {
            graphics2D.setPaint(Color.white);
            graphics2D.fillRect(0, 0, getSize().width, getSize().height);
            graphics2D.setPaint(Color.black);
            //repaint() is used to do drawing that calls update() which calls paint()
            repaint();
        }

       //method for rubber
        public void Rubber()
        {
            this.addMouseListener(pressing);
            graphics2D.setPaint(Color.WHITE);
            graphics2D.setStroke(graphics2D.getStroke());
            this.addMouseMotionListener(dragging);
            repaint();
        }
       
       public void rectangle()
       {
           this.removeMouseMotionListener(dragging);
           this.addMouseListener(pressing);
           graphics2D.setPaint(graphics2D.getColor());
           graphics2D.drawRect(oldX,oldY,tmpX-oldX,tmpY-oldY);
           repaint();
       }
       
        public void circle()
        {
            this.removeMouseMotionListener(dragging);
            this.addMouseListener(pressing);
            graphics2D.setPaint(graphics2D.getColor());
            graphics2D.drawOval(oldX,oldY,tmpX-oldX,tmpY-oldY);
            repaint();
        }
        public void pencil()
        {
            this.addMouseListener(pressing);
            graphics2D.setPaint(graphics2D.getColor());
            this.addMouseMotionListener(dragging);
        }
        
        public void line()
        {
           this.removeMouseMotionListener(dragging);
           this.addMouseListener(pressing);
           graphics2D.setPaint(graphics2D.getColor());
           graphics2D.drawLine(oldX,oldY,tmpX,tmpY);
           repaint();
        }
        
        //method for color change
        public void red()
        {
            graphics2D.setPaint(Color.red);
            repaint();
        }
        public void darkred()
        {
            graphics2D.setPaint(new Color(176,23,31));
            repaint();
        }
        public void lightred()
        {
            graphics2D.setPaint(new Color(238,99,99));
            repaint();
        }

        public void black()
        {
            graphics2D.setPaint(Color.black);
            repaint();
        }
        
        public void grey()
        {
            graphics2D.setPaint(new Color(139,137,137));
            repaint();
        }
        
        public void lightgrey()
        {
            graphics2D.setPaint(new Color(205,201,201));
            repaint();
        }
        
        public void magenta()
        {
            graphics2D.setPaint(Color.magenta);
            repaint();
        }
        
        public void blue()
        {
            graphics2D.setPaint(Color.blue);
            repaint();
        }
        
        public void midblue()
        {
            graphics2D.setPaint(new Color(49,132,226));
            repaint();
        }

        public void darkgreen()
        {
            graphics2D.setPaint(new Color(36,158,6));
            repaint();
        }
        
        public void secondgreen()
        {
            graphics2D.setPaint(new Color(170,255,130));
            repaint();
        }

        public void green()
        {
            graphics2D.setPaint(Color.green);
            repaint();
        }

        public void orange()
        {
            graphics2D.setPaint(Color.orange);
            repaint();
        }

        public void midorange()
        {
            graphics2D.setPaint(new Color(248,148,21));
            repaint();
        }
        
        public void darkorange()
        {
            graphics2D.setPaint(new Color(206,112,11));
            repaint();
        }
        
        public void yellow()
        {
            graphics2D.setPaint(Color.yellow);
            repaint();
        }
        
        public void cyan()
        {
            graphics2D.setPaint(Color.cyan);
            repaint();
        }
        
        public void pink()
        {
            graphics2D.setPaint(Color.pink);
            repaint();
        }
        
        public void lightpink()
        {
            graphics2D.setPaint(new Color(255,204,229));
            repaint();
        }
        
        //brush size//
        public void oneset()
        {
            graphics2D.setStroke(new BasicStroke(1));
            repaint();
        }

        public void twoset()
        {
            graphics2D.setStroke(new BasicStroke(2));
            repaint();
        }

        public void threeset()
        {
            graphics2D.setStroke(new BasicStroke(3));
            repaint();
        }

        public void fourset()
        {
            graphics2D.setStroke(new BasicStroke(4));
            repaint();
        }

        public void fiveset()
        {
            graphics2D.setStroke(new BasicStroke(5));
            repaint();
        }

        public void sixset()
        {
            graphics2D.setStroke(new BasicStroke(6));
            repaint();
        }

        public void sevenset()
        {
            graphics2D.setStroke(new BasicStroke(7));
            repaint();
        }

        public void eightset()
        {
            graphics2D.setStroke(new BasicStroke(8));
            repaint();
        }

        public void nineset()
        {
            graphics2D.setStroke(new BasicStroke(9));
            repaint();
        }

        public void tenset()
        {
            graphics2D.setStroke(new BasicStroke(10));
            repaint();
        }

        public void elevenset()
        {
            graphics2D.setStroke(new BasicStroke(11));
            repaint();
        }
        
        public void twelveset()
        {
            graphics2D.setStroke(new BasicStroke(12));
            repaint();
        }
        
        public void thirteenset()
        {
            graphics2D.setStroke(new BasicStroke(13));
            repaint();
        }
        
        public void fourteenset()
        {
            graphics2D.setStroke(new BasicStroke(14));
            repaint();
        }
        
        public void fifteenset()
        {
            graphics2D.setStroke(new BasicStroke(15));
            repaint();
        }
        
        public void sixteenset()
        {
            graphics2D.setStroke(new BasicStroke(16));
            repaint();
        }
        
        public void seventeenset()
        {
            graphics2D.setStroke(new BasicStroke(17));
            repaint();
        }
        
        public void eighteenset()
        {
            graphics2D.setStroke(new BasicStroke(18));
            repaint();
        }
        
        public void nineteenset()
        {
            graphics2D.setStroke(new BasicStroke(19));
            repaint();
        }
        
        public void twentyset()
        {
            graphics2D.setStroke(new BasicStroke(20));
            repaint();
        }

        //fill colours//
        public void fillBlack()
        {
            graphics2D.setPaint(Color.black);
            graphics2D.fillRect(0, 0, getSize().width, getSize().height);
            repaint();
        }
        
        public void fillMagenta()
        {
            graphics2D.setPaint(Color.magenta);
            graphics2D.fillRect(0, 0, getSize().width, getSize().height);
            repaint();
        }
        
        public void fillRed()
        {
            graphics2D.setPaint(Color.red);
            graphics2D.fillRect(0, 0, getSize().width, getSize().height);
            repaint();
        }
        
        public void fillBlue()
        {
            graphics2D.setPaint(Color.blue);
            graphics2D.fillRect(0, 0, getSize().width, getSize().height);
            repaint();
        }
        
        public void fillGreen()
        {
            graphics2D.setPaint(Color.green);
            graphics2D.fillRect(0, 0, getSize().width, getSize().height);
            repaint();
        }
        
        public void fillOrange()
        {
            graphics2D.setPaint(Color.orange);
            graphics2D.fillRect(0, 0, getSize().width, getSize().height);
            repaint();
        }
        
        public void fillYellow()
        {
            graphics2D.setPaint(Color.yellow);
            graphics2D.fillRect(0, 0, getSize().width, getSize().height);
            repaint();
        }
        
        public void fillCyan()
        {
            graphics2D.setPaint(Color.cyan);
            graphics2D.fillRect(0, 0, getSize().width, getSize().height);
            repaint();
        }
        
        public void fillPink()
        {
            graphics2D.setPaint(Color.pink);
            graphics2D.fillRect(0, 0, getSize().width, getSize().height);
            repaint();
        }
    }
