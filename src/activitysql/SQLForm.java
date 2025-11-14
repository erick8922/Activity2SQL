package activitysql;

import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class SQLForm extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(SQLForm.class.getName());
     
     Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    
    
     
     
    public SQLForm() {
        initComponents();
          connect();
           loadTable();
    }
    
    public void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3307/students",
                    "root",
                    ""
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Database Connection Failed: " + e.getMessage());
        }
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        studentId = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        name = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        age = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        grade = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        add = new javax.swing.JButton();
        search = new javax.swing.JButton();
        delete = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        studentId.setText("Student_id");
        getContentPane().add(studentId, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 9, -1, -1));
        getContentPane().add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(88, 6, 242, -1));

        name.setText("Name");
        getContentPane().add(name, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 37, -1, -1));
        getContentPane().add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(86, 34, 242, -1));

        age.setText("Age");
        getContentPane().add(age, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 65, -1, -1));
        getContentPane().add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(86, 62, 242, -1));

        grade.setText("Grade");
        getContentPane().add(grade, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 90, -1, -1));

        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });
        getContentPane().add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(86, 90, 242, -1));

        add.setText("ADD");
        add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addActionPerformed(evt);
            }
        });
        getContentPane().add(add, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 20, -1, -1));

        search.setText("SEARCH");
        search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchActionPerformed(evt);
            }
        });
        getContentPane().add(search, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 80, -1, -1));

        delete.setText("DELETE");
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });
        getContentPane().add(delete, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 50, -1, -1));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Student ID", "Name", "Age", "Grade"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 159, 415, 402));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchActionPerformed
      String keyword = jTextField1.getText();

        try {
            String sql = "SELECT * FROM students WHERE StudentID = ? OR Name LIKE ?";
            pst = conn.prepareStatement(sql);

            pst.setString(1, keyword);
            pst.setString(2, "%" + keyword + "%");

            rs = pst.executeQuery();

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("StudentID"),
                    rs.getString("Name"),
                    rs.getString("Age"),
                    rs.getString("Grade")
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Search Error: " + e.getMessage());
        }
    }//GEN-LAST:event_searchActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addActionPerformed
       String id = jTextField1.getText();
        String n = jTextField2.getText();
        String a = jTextField3.getText();
        String g = jTextField4.getText();

        if (id.isEmpty() || n.isEmpty() || a.isEmpty() || g.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }

        try {
            String sql = "INSERT INTO students (StudentID, Name, Age, Grade) VALUES (?, ?, ?, ?)";
            pst = conn.prepareStatement(sql);

            pst.setString(1, id);
            pst.setString(2, n);
            pst.setString(3, a);
            pst.setString(4, g);

            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Record Added Successfully!");
            loadTable(); // refresh table

            // clear input fields
            jTextField1.setText("");
            jTextField2.setText("");
            jTextField3.setText("");
            jTextField4.setText("");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Insert Error: " + e.getMessage());
        }
    }//GEN-LAST:event_addActionPerformed

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteActionPerformed
       String id = jTextField1.getText();

        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Student ID to delete.");
            return;
        }

        try {
            String sql = "DELETE FROM students WHERE StudentID = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, id);

            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this record?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                pst.executeUpdate();
                JOptionPane.showMessageDialog(this, "Record Deleted Successfully!");
                loadTable(); // refresh table

                // clear input fields
                jTextField1.setText("");
                jTextField2.setText("");
                jTextField3.setText("");
                jTextField4.setText("");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Delete Error: " + e.getMessage());
        }
    }//GEN-LAST:event_deleteActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new SQLForm().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton add;
    private javax.swing.JLabel age;
    private javax.swing.JButton delete;
    private javax.swing.JLabel grade;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JLabel name;
    private javax.swing.JButton search;
    private javax.swing.JLabel studentId;
    // End of variables declaration//GEN-END:variables

  

   private void loadTable() {
    try {
        String sql = "SELECT * FROM students";
        pst = conn.prepareStatement(sql);
        rs = pst.executeQuery();

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); // clear previous rows

        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getString("StudentID"),
                rs.getString("Name"),
                rs.getString("Age"),
                rs.getString("Grade")
            });
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Load Table Error: " + e.getMessage());
        e.printStackTrace();
    }
}

}
