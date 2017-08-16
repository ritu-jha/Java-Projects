package musicalsystem;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

public class MusicalSystem extends javax.swing.JFrame 
{
    public MusicalSystem() 
    {
        try 
        {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }
        catch (InstantiationException ex) 
        {
            ex.printStackTrace();
        } 
        catch (UnsupportedLookAndFeelException ex) 
        {
            ex.printStackTrace();
        } 
        catch (ClassNotFoundException ex) 
        {
            ex.printStackTrace();
        } 
        catch (IllegalAccessException ex) 
        {
            ex.printStackTrace();
        }
        getContentPane().setBackground(new Color(212,208,200));
        initComponents();
        rootNode = new DefaultMutableTreeNode("Music");
        dtmChild = new DefaultTreeModel(rootNode);
        treOperatingSystem.setModel(dtmChild);
        
        for(int i=0; i < music.length ; i++)
        {
            dtmMusic[i] = new DefaultMutableTreeNode(music[i]);
            rootNode.add(dtmMusic[i]);
        }
        
        for (int i = 0; i < ClassicalType.length; ++i) 
        {
            dtmMusic[0].add(new DefaultMutableTreeNode(ClassicalType[i]));
        }
        
        for (int i = 0; i < JazzType.length; ++i) 
        {
            dtmMusic[1].add(new DefaultMutableTreeNode(JazzType[i]));
        }
    }
    
    
    private void treOperatingSystemMouseClicked(java.awt.event.MouseEvent evt)
    {//GEN-FIRST:event_treOperatingSystemMouseClicked
// TODO add your handling code here:
          TreePath tp = treOperatingSystem.getPathForLocation(evt.getX(), evt.getY());
        
        if (tp != null) {
	String str = tp.toString();
        int index = str.lastIndexOf(",");
	if (index != -1)
	{
		str = str.substring(index + 1,str.length()-1);
		lblStatus.setText(str);
	}
	else {
     
	 str = str.substring(1,str.length()-1);
            lblStatus.setText(str);
	}
        
 }
    }//GEN-LAST:event_treOperatingSystemMouseClicked
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MusicalSystem().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblStatus;
    private javax.swing.JScrollPane scpOperatingSystem;
    private javax.swing.JTree treOperatingSystem;
    // End of variables declaration//GEN-END:variables
    DefaultMutableTreeNode rootNode;
    DefaultMutableTreeNode dtmMusic[]  = new DefaultMutableTreeNode[4];
    DefaultTreeModel dtmChild;
    String music[] = {"Classical","Jazz","Rock"};
    String ClassicalType[] = {"Bethoven","Brahms","Mozart"};
    String JazzType[] = {"Albert Ayler","Chet Barker","Johm Coltrone","Miles Davis"};
}
