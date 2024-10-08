package views;

import controllers.classController;
import controllers.studentController;
import controllers.feedBackController;
import javax.swing.table.DefaultTableModel;
import models.Student;
import models.User;
import models.Feedback;
import quanlyktx.data.excelFile;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import util.ColorFrame;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.Timer;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Dimension;

public class managerFeedback extends javax.swing.JFrame {

    feedBackController fbCTL = new feedBackController();
    Feedback fb = new Feedback();
    Student studentM = new Student();

    //View
    public managerFeedback() {
        initComponents();
        setDetail();
    }

    public void setDetail() {
        setTitle("Quản lí sinh viên ký túc xá - Quản lí phản hổi của sinh viên");
        setMinimumSize(new Dimension(450, 450));
        // Can giua cua so chuong trinh
        setLocationRelativeTo(null);
        messageDLable.setText("");
    }

    // Sự kiện double click
    private void addDoubleClickEvent() {
        for (MouseListener listener : jTable1.getMouseListeners()) {
            if (listener instanceof MouseAdapter) {
                jTable1.removeMouseListener(listener);
            }
        }
        jTable1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedRow = jTable1.getSelectedRow();
                    if (selectedRow >= 0) {
                        // Lấy dữ liệu từ JTable
                        String idfb = jTable1.getValueAt(selectedRow, 0).toString();
                        String name = jTable1.getValueAt(selectedRow, 1).toString();
                        String msv = jTable1.getValueAt(selectedRow, 2).toString();
                        String classs = jTable1.getValueAt(selectedRow, 3).toString();
                        String phone = jTable1.getValueAt(selectedRow, 4).toString();
                        String comment = jTable1.getValueAt(selectedRow, 5).toString();
                        String status = jTable1.getValueAt(selectedRow, 6).toString();
                        // Cập nhật checkbox
                        boolean isChecked = status.equals("Duyệt");
                        jCheckBox1.setSelected(isChecked);

                        // Truyền dữ liệu vào input
                        idDField.setText(idfb);
                        msvDField.setText(name);
                        NameDField.setText(msv);
                        phoneDField.setText(classs);
                        classDField.setText(phone);
                        jTextArea1.setText(comment);

                    }
                }
            }
        });

    }
    
//Reset về mặc định
    private void resetInputFields() {
        idDField.setText("");
        msvDField.setText("");
        NameDField.setText("");
        phoneDField.setText("");
        classDField.setText("");
        jTextArea1.setText("");
    }

//Kiểm tra rỗng
    private boolean isNullField() {
        return idDField.getText().isEmpty()
                || msvDField.getText().isEmpty()
                || NameDField.getText().isEmpty()
                || phoneDField.getText().isEmpty()
                || classDField.getText().isEmpty()
                || jTextArea1.getText().isEmpty();
    }

    //Ham load csdl hiển thi ra table
    public void loadTable(boolean all) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        fbCTL.loadDBFeedback(jTable1, all);

        addDoubleClickEvent();
    }

    //Nút Lưu - Cập nhật
    private void updateFeedBackFunc() {
        String idfb = idDField.getText();
        String name = msvDField.getText();
        String msv = NameDField.getText();
        String classs = phoneDField.getText();
        String phone = classDField.getText();
        String comment = jTextArea1.getText();
        String status = jCheckBox1.isSelected() ? "Duyệt" : "Chưa duyệt";

        if (isNullField()) {
            messageDLable.setText("Không thành công! Có trường dữ liệu đang trống!");
            return;
        }else if(!idfb.isEmpty()) {
            int option = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn sửa không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                // Cập nhật csdl
                fbCTL.updateFeedbackStatus(Integer.parseInt(idfb), comment, status);

                messageDLable.setText("Dữ liệu đã được cập nhật.");
                messageDLable.setForeground(new java.awt.Color(0, 153, 0));
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một đánh giá", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
        resetInputFields();
    }

    // Nút xóa
    public void deleteFeedBackFunc() {
        if (isNullField()) {
            messageDLable.setText("Không thành công! Có trường dữ liệu đang trống!");
            return;
        }
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow >= 0) {
            int option = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                // Lấy dữ liệu
                String selectedID = jTable1.getValueAt(selectedRow, 0).toString();
                // Xóa từ CSDL
                fbCTL.deleteFeedback(Integer.parseInt(selectedID));
                // Xóa khỏi bảng
                DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                model.removeRow(selectedRow);

                resetInputFields();

                String message = "Phản hồi có ID " + selectedID + " đã được xóa thành công!";
                messageDLable.setText(message);
                messageDLable.setForeground(new java.awt.Color(204, 0, 0));
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một đánh giá", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        reloadBTN = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        idDField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        classDField = new javax.swing.JTextField();
        phoneDField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        msvDField = new javax.swing.JTextField();
        NameDField = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        messageDLable = new javax.swing.JLabel();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Họ tên sinh viên", "Mã sinh viên", "Lớp", "Số điện thoại", "Đánh giá của sinh viên", "Trạng thái"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jTable1.setCellSelectionEnabled(true);
        jScrollPane1.setViewportView(jTable1);

        reloadBTN.setBackground(new java.awt.Color(0, 51, 51));
        reloadBTN.setForeground(new java.awt.Color(255, 255, 255));
        reloadBTN.setText("Tải lại");
        reloadBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reloadBTNActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Danh sách sinh viên");

        jLabel2.setText("ID");

        idDField.setEnabled(false);
        idDField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idDFieldActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel3.setText("Phản hồi của sinh viên");

        jLabel8.setText("Mã sinh viên");

        jLabel4.setText("Họ tên");

        jLabel6.setText("Số điện thoại");

        jLabel10.setText("Lớp");

        classDField.setEditable(false);
        classDField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                classDFieldActionPerformed(evt);
            }
        });

        phoneDField.setEditable(false);
        phoneDField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                phoneDFieldActionPerformed(evt);
            }
        });

        jLabel5.setText("Đánh giá của sinh viên");

        jLabel7.setText("Trạng thái");

        jCheckBox1.setText("Duyệt");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statusDFieldActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(0, 102, 255));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Sửa");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateFBDFieldActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(255, 0, 0));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Xóa");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteFBDFieldActionPerformed(evt);
            }
        });

        msvDField.setEditable(false);
        msvDField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                msvDFieldActionPerformed1(evt);
            }
        });

        NameDField.setEditable(false);
        NameDField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NameDFieldActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                commentDFieldActionPerformed(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jScrollPane2.setViewportView(jTextArea1);

        messageDLable.setForeground(new java.awt.Color(204, 0, 0));
        messageDLable.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        messageDLable.setText("Thông báo");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(50, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(35, 35, 35)
                                .addComponent(jCheckBox1))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(NameDField))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(msvDField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(idDField, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(42, 42, 42)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(phoneDField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(classDField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel5)
                            .addComponent(jScrollPane2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButton1)
                                .addGap(73, 73, 73)
                                .addComponent(jButton2)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addComponent(messageDLable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(idDField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(phoneDField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(msvDField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel10)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(classDField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(NameDField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jCheckBox1))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(messageDLable)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(reloadBTN))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(reloadBTN)
                    .addComponent(jLabel1))
                .addGap(15, 15, 15)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void idDFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idDFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idDFieldActionPerformed

    private void reloadBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reloadBTNActionPerformed
        // TODO add your handling code here:
        loadTable(true);
    }//GEN-LAST:event_reloadBTNActionPerformed

    private void phoneDFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_phoneDFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_phoneDFieldActionPerformed

    private void msvDFieldActionPerformed1(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_msvDFieldActionPerformed1
        // TODO add your handling code here:
    }//GEN-LAST:event_msvDFieldActionPerformed1

    private void NameDFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NameDFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NameDFieldActionPerformed

    private void classDFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_classDFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_classDFieldActionPerformed

    private void commentDFieldActionPerformed(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_commentDFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_commentDFieldActionPerformed

    private void statusDFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statusDFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_statusDFieldActionPerformed

    private void updateFBDFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateFBDFieldActionPerformed
        // TODO add your handling code here:
        updateFeedBackFunc();
    }//GEN-LAST:event_updateFBDFieldActionPerformed

    private void deleteFBDFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteFBDFieldActionPerformed
        // TODO add your handling code here:
        deleteFeedBackFunc();
    }//GEN-LAST:event_deleteFBDFieldActionPerformed

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(managerFeedback.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(managerFeedback.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(managerFeedback.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(managerFeedback.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new managerFeedback().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField NameDField;
    private javax.swing.JTextField classDField;
    private javax.swing.JTextField idDField;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel messageDLable;
    private javax.swing.JTextField msvDField;
    private javax.swing.JTextField phoneDField;
    private javax.swing.JButton reloadBTN;
    // End of variables declaration//GEN-END:variables
}
