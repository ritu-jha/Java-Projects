/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package calculator;
import java.awt.Color;
import javax.swing.JFormattedTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Calculator extends javax.swing.JFrame {
    
    /** Creates new form Calculator */
    public Calculator() {
        
        try{
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            }catch(InstantiationException ex){
                ex.printStackTrace();
            }catch(UnsupportedLookAndFeelException ex) {
                ex.printStackTrace();
            }catch(ClassNotFoundException ex){
                ex.printStackTrace();
            }catch(IllegalAccessException ex){
                ex.printStackTrace();
            }
        getContentPane().setBackground(new Color(212,208,200));
        
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        txfOperand1 = new JFormattedTextField(new Float(0));
        txfOperand1.setText("");
        jPanel1 = new javax.swing.JPanel();
        btnPlus = new javax.swing.JButton();
        btnMinus = new javax.swing.JButton();
        btnMultiply = new javax.swing.JButton();
        btnDivide = new javax.swing.JButton();
        txfOperand2 = new JFormattedTextField(new Float(0));
        txfOperand2.setText("");
        btnEquals = new javax.swing.JButton();
        txfOutput = new JFormattedTextField(new Float(0));

        getContentPane().setLayout(new java.awt.FlowLayout());

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Simple Calculator");
        txfOperand1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txfOperand1.setPreferredSize(new java.awt.Dimension(200, 20));
        getContentPane().add(txfOperand1);

        jPanel1.setLayout(new java.awt.GridLayout(1, 4, 2, 2));

        getContentPane().add(jPanel1);

        btnPlus.setText("+");
        btnPlus.setName("btnPlus");
        btnPlus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlusActionPerformed(evt);
            }
        });

        getContentPane().add(btnPlus);

        btnMinus.setText("-");
        btnMinus.setName("btnMinus");
        btnMinus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMinusActionPerformed(evt);
            }
        });

        getContentPane().add(btnMinus);

        btnMultiply.setText("x");
        btnMultiply.setName("btnMultiply");
        btnMultiply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMultiplyActionPerformed(evt);
            }
        });

        getContentPane().add(btnMultiply);

        btnDivide.setText("/");
        btnDivide.setName("btnDivide");
        btnDivide.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDivideActionPerformed(evt);
            }
        });

        getContentPane().add(btnDivide);

        txfOperand2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txfOperand2.setName("txfOperand2");
        txfOperand2.setPreferredSize(new java.awt.Dimension(200, 20));
        getContentPane().add(txfOperand2);

        btnEquals.setText("=");
        btnEquals.setName("btnEquals");
        btnEquals.setPreferredSize(new java.awt.Dimension(200, 20));
        btnEquals.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEqualsActionPerformed(evt);
            }
        });

        getContentPane().add(btnEquals);

        txfOutput.setEditable(false);
        txfOutput.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txfOutput.setName("txfOutput");
        txfOutput.setPreferredSize(new java.awt.Dimension(200, 20));
        getContentPane().add(txfOutput);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-234)/2, (screenSize.height-164)/2, 234, 164);
    }// </editor-fold>//GEN-END:initComponents

    private void btnEqualsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEqualsActionPerformed
        float Operand1 = (Float)txfOperand1.getValue();
        float Operand2 = (Float)txfOperand2.getValue();
        
        if (temp==0){
            txfOutput.setValue(Operand1 + Operand2);
        }
        
        if (temp==1){
            txfOutput.setValue(Operand1 - Operand2);
        }
        
        if (temp==2){
            txfOutput.setValue(Operand1 * Operand2);
        }
        
        if (temp==3){
            txfOutput.setValue(Operand1 / Operand2);
        }
            
        
    }//GEN-LAST:event_btnEqualsActionPerformed

    private void btnDivideActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDivideActionPerformed
        temp=3;
    }//GEN-LAST:event_btnDivideActionPerformed

    private void btnMultiplyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMultiplyActionPerformed
        temp=2;
    }//GEN-LAST:event_btnMultiplyActionPerformed

    private void btnMinusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMinusActionPerformed
        temp=1;
    }//GEN-LAST:event_btnMinusActionPerformed

    private void btnPlusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlusActionPerformed
        temp=0;
    }//GEN-LAST:event_btnPlusActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Calculator().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDivide;
    private javax.swing.JButton btnEquals;
    private javax.swing.JButton btnMinus;
    private javax.swing.JButton btnMultiply;
    private javax.swing.JButton btnPlus;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JFormattedTextField txfOperand1;
    private javax.swing.JFormattedTextField txfOperand2;
    private javax.swing.JFormattedTextField txfOutput;
    // End of variables declaration//GEN-END:variables
    int temp;
}
