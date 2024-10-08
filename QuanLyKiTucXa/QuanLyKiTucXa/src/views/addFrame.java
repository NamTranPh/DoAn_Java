package views;

import java.awt.Dimension;
import javax.swing.JOptionPane;
import controllers.classController;
import controllers.studentController;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JComboBox;
import models.Student;

public class addFrame extends javax.swing.JFrame {

    classController classCTL = new classController();
    studentController stuCTL = new studentController();
    Student studentM = new Student();
    String stID;
    String stMSV;
    String stName;
    String stBirth;
    String stGender;
    String stPhone;
    String stClass;
    String stPlace;
    String stRoom;

//Cài đặt View
    public addFrame() {
        setTitle("Quản lí sinh viên ký túc xá - Thêm thông tin sinh viên");
        initComponents();
        inforBonus();
        setMinimumSize(new Dimension(450, 450));
        // Can giua cua so chuong trinh
        setLocationRelativeTo(null);
        messageDLable1.setText("");
    }

//Ham cai dat bo sung
    public void inforBonus() {
        classCTL.setModelCBB(editClassComboBox);
    }

//Ham dua ve trang thai ban dau
    public void refresh() {
        stID = String.valueOf(studentM.getStID());
        stMSV = studentM.getStMSV();
        stName = studentM.getStName();
        stBirth = studentM.getStBirth();
        stGender = String.valueOf(studentM.getStGender());
        stPhone = String.valueOf(studentM.getStPhone());
        stClass = String.valueOf(classCTL.getstClassInfor(studentM.getStClass()));
        stPlace = studentM.getStPlace();
        stRoom = String.valueOf(studentM.getStRoom());
        messageDLable.setForeground(Color.red);
        idDField.setText(stID);
        birthDField.setText(stBirth);
    }

//Ham gan gia tri cho model
    public void studentMUpdate() {
        try {
            studentM.setStName(NameDField2.getText());
            studentM.setStMSV(NameDField.getText());
            studentM.setStBirth(birthDField.getText());
            String gender = editGenderComboBox.getSelectedItem().toString();
            studentM.setStGender(stuCTL.convertGenderToF(gender));
            studentM.setStPhone(phoneDField.getText());
            studentM.setStClass(String.valueOf(editClassComboBox.getSelectedItem()));
            studentM.setStPlace(pointDField.getText());
            studentM.setStRoom(Integer.parseInt(jTextField1.getText()));

        } catch (Exception e) {
            messageDLable1.setText("Thông tin này không chính xác!");
        }
    }

//Ham checkNull
    public boolean isNullField() {
        if (stMSV.isEmpty() || stName.isEmpty() || stBirth.isEmpty() || stPhone.isEmpty() || stClass.isEmpty() || stPlace.isEmpty() || stRoom.isEmpty()) {
            return true;
        }
        return false;
    }

//Ham xu ly nut ADD
    public void addNewStudent() {
        studentMUpdate();
        refresh();
        if (isNullField()) {
            messageDLable1.setText("Không thành công! Có trường dữ liệu đang trống!");
            return;
        } else {
            int option = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn với dữ liệu đã nhập?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                try {
                    int stRoom = Integer.parseInt(jTextField1.getText());
                    if (stMSV.isEmpty() || stName.isEmpty() || stBirth.isEmpty() || stPhone.isEmpty() || stClass.isEmpty() || stPlace.isEmpty()) {
                        messageDLable1.setText("Không thành công! Có trường dữ liệu đang trống!");
                    } else {
                        // Thực hiện thêm sinh viên vào cơ sở dữ liệu
                        stuCTL.insertDB(stID, stMSV, stName, stBirth, stGender, stPhone, Integer.parseInt(stClass), stPlace, stRoom);
                        Color customColor = new Color(0, 153, 0);
                        messageDLable1.setText("Sinh viên " + stName + " đã được thêm vào cơ sở dữ liệu!");
                        messageDLable1.setForeground(customColor);
                        // Cập nhật ID cho sinh viên tiếp theo
                        int idNext = Integer.parseInt(stID) + 1;
                        stID = String.valueOf(idNext);
                        idDField.setText(stID);
                        // Xóa dữ liệu trên các trường nhập liệu
                        NameDField.setText("");
                        birthDField.setText("");
                        phoneDField.setText("");
                        pointDField.setText("");
                        jTextField1.setText("1");
                        //Thông báo
                        JOptionPane.showMessageDialog(this, "Sinh viên " + stName + " đã được thêm vào cơ sở dữ liệu!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        this.dispose();
                    }
                } catch (NumberFormatException e) {
                    messageDLable1.setText("Lỗi khi chuyển đổi dữ liệu sang số nguyên!");
                } catch (Exception e) {
                    // Xử lý lỗi khác
                    messageDLable1.setText("Lỗi khi thêm sinh viên vào cơ sở dữ liệu!");
                    e.printStackTrace();
                }
            }
        }
    }
//View lístRoom
    public void listRoomFunc() {
        try {
            listRoomFrame listRoomFrame = new listRoomFrame();
            listRoomFrame.setParentFrame(this); // Thiết lập parentFrame của listRoomFrame là addFrame
            listRoomFrame.setVisible(true);
            // Truyền giá trị số phòng và input
            listRoomFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    String selectedRoom = listRoomFrame.getSelectedRoom(); // Lấy số phòng đã chọn
                    if (selectedRoom != null) {
                        try {
                            int stRoom = Integer.parseInt(selectedRoom);
                            setTextField(stRoom); // Cập nhật số phòng
                        } catch (NumberFormatException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setTextField(int stRoom) {
        jTextField1.setText(String.valueOf(stRoom));
    }

    public void setStRoom(int stRoom) {
        jTextField1.setText(String.valueOf(stRoom));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        detailLabel = new javax.swing.JLabel();
        NameDField = new javax.swing.JTextField();
        birthDField = new javax.swing.JTextField();
        phoneDField = new javax.swing.JTextField();
        pointDField = new javax.swing.JTextField();
        editGenderComboBox = new javax.swing.JComboBox<>();
        idDField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        saveBTN = new javax.swing.JButton();
        messageDLable = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        editClassComboBox = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        NameDField2 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        messageDLable1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        detailLabel.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        detailLabel.setForeground(new java.awt.Color(0, 51, 51));
        detailLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        detailLabel.setText("THÊM THÔNG TIN SINH VIÊN");

        birthDField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                birthDFieldActionPerformed(evt);
            }
        });

        phoneDField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                phoneDFieldActionPerformed(evt);
            }
        });

        editGenderComboBox.setEditable(true);
        editGenderComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nam", "Nữ" }));
        editGenderComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editGenderComboBoxActionPerformed(evt);
            }
        });

        idDField.setEnabled(false);
        idDField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idDFieldActionPerformed(evt);
            }
        });

        jLabel2.setText("ID");

        jLabel3.setText("Họ tên");

        jLabel4.setText("Ngày sinh");

        jLabel5.setText("Giời tính");

        jLabel6.setText("Số điện thoại");

        jLabel7.setText("Địa chỉ");

        saveBTN.setBackground(new java.awt.Color(0, 51, 255));
        saveBTN.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        saveBTN.setForeground(new java.awt.Color(255, 255, 255));
        saveBTN.setText("THÊM");
        saveBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBTNActionPerformed(evt);
            }
        });

        messageDLable.setForeground(new java.awt.Color(204, 0, 0));
        messageDLable.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel9.setText("Số phòng");

        jLabel10.setText("Lớp");

        editClassComboBox.setEditable(true);
        editClassComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editClassComboBoxActionPerformed(evt);
            }
        });

        jLabel8.setText("Mã sinh viên");

        jButton1.setText("Lựa chọn phòng");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listRoomBTNActionPerformed(evt);
            }
        });

        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.setText("1");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        messageDLable1.setForeground(new java.awt.Color(204, 0, 0));
        messageDLable1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        messageDLable1.setText("Thông báo");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(50, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(birthDField))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(phoneDField, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(idDField, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(NameDField)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(NameDField2))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(pointDField, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(editClassComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(editGenderComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(messageDLable, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(jButton1)))
                        .addGap(0, 50, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(saveBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(detailLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(messageDLable1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(detailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(idDField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(NameDField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(NameDField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                        .addComponent(jLabel4))
                    .addComponent(birthDField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editGenderComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(messageDLable)
                        .addGap(0, 0, 0)
                        .addComponent(phoneDField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(editClassComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(pointDField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(messageDLable1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(saveBTN)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBTNActionPerformed
        // TODO add your handling code here:
        addNewStudent();
    }//GEN-LAST:event_saveBTNActionPerformed

    private void birthDFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_birthDFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_birthDFieldActionPerformed

    private void phoneDFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_phoneDFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_phoneDFieldActionPerformed

    private void editClassComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editClassComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editClassComboBoxActionPerformed

    private void editGenderComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editGenderComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_editGenderComboBoxActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void listRoomBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listRoomBTNActionPerformed
        // TODO add your handling code here:
        listRoomFunc();
    }//GEN-LAST:event_listRoomBTNActionPerformed

    private void idDFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idDFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idDFieldActionPerformed

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
            java.util.logging.Logger.getLogger(addFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(addFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(addFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(addFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new addFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField NameDField;
    private javax.swing.JTextField NameDField2;
    private javax.swing.JTextField birthDField;
    private javax.swing.JLabel detailLabel;
    private javax.swing.JComboBox<String> editClassComboBox;
    private javax.swing.JComboBox<String> editGenderComboBox;
    private javax.swing.JTextField idDField;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel messageDLable;
    private javax.swing.JLabel messageDLable1;
    private javax.swing.JTextField phoneDField;
    private javax.swing.JTextField pointDField;
    private javax.swing.JButton saveBTN;
    // End of variables declaration//GEN-END:variables
}
