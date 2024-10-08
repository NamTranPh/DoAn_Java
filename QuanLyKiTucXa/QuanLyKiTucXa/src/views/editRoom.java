package views;

import controllers.classController;
import controllers.studentController;
import controllers.RoomController;
import javax.swing.table.DefaultTableModel;
import models.Student;
import models.User;
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
import javax.swing.JFrame;
import controllers.RoomController;
import controllers.userController;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import models.User;
import models.Room;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class editRoom extends javax.swing.JFrame {

    userController userCTL = new userController();
    RoomController rmCTL = new RoomController();
    User user = new User();
    Room fb = new Room();

//View
    public editRoom() {
        initComponents();
        setDetail();
    }

    //Ham Them cau hinh cho editRoom
    public void setDetail() {
        setTitle("Quản lí phòng");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(450, 450));
        // Can giua cua so chuong trinh
        setLocationRelativeTo(null);
        messageDLable.setText("");
    }

    public void UpdateInfor() {
        setNumberOfSt();
    }

    public void setNumberOfSt() {
        int count = jTable1.getRowCount();
        numberLabel6.setText("Số lượng: " + count);
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
                        String stRoom = jTable1.getValueAt(selectedRow, 0).toString();
                        String roomNumber = jTable1.getValueAt(selectedRow, 1).toString();
                        String floor = jTable1.getValueAt(selectedRow, 2).toString();
                        String roomTypeValue = jTable1.getValueAt(selectedRow, 3).toString();
                        String capacity = jTable1.getValueAt(selectedRow, 4).toString();
                        String building = jTable1.getValueAt(selectedRow, 5).toString();
                        String status = jTable1.getValueAt(selectedRow, 6).toString();
                        String roomType;
                        if (roomTypeValue.equals("Nam")) {
                            roomType = "Nam";
                        } else {
                            roomType = "Nữ";
                        }

                        // Truyền dữ liệu vào input
                        idDField.setText(stRoom);
                        jTextPane1.setText(roomNumber);
                        jComboBox1.setSelectedItem(floor);
                        jComboBox2.setSelectedItem(roomType);
                        jTextPane2.setText(capacity);
                        jComboBox3.setSelectedItem(building);
                        jComboBox4.setSelectedItem(status);
                    }
                }
            }
        });
    }

//Ham load csdl hiển thi ra table
    public void loadTable(boolean all) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        rmCTL.loadDBRoom(jTable1, all);
        UpdateInfor();

        addDoubleClickEvent();
    }

    private boolean isNullField() {
        return idDField.getText().isEmpty()
                || jTextPane1.getText().isEmpty()
                || jComboBox1.getSelectedItem() == null
                || jComboBox2.getSelectedItem() == null
                || jTextPane2.getText().isEmpty()
                || jComboBox3.getSelectedItem() == null
                || jComboBox4.getSelectedItem() == null;
    }

//Nút thêm -- chưa xong ở thằng id
    private void addRoomFunc() {
        try {
            int stRoom = rmCTL.getNextRoomID();
            if (stRoom == -1) {
                JOptionPane.showMessageDialog(this, "Không thể lấy ID tiếp theo cho phòng mới!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            idDField.setText(String.valueOf(stRoom));
            int id = Integer.parseInt(idDField.getText());
            int roomNumber = Integer.parseInt(jTextPane1.getText());
            int floor = Integer.parseInt(jComboBox1.getSelectedItem().toString());
            String roomTypeValue = jComboBox2.getSelectedItem().toString();
            int roomType;
            if (roomTypeValue.equals("Nam")) {
                roomType = 1;
            } else {
                roomType = 0;
            }
            int capacity = Integer.parseInt(jTextPane2.getText());
            String building = jComboBox3.getSelectedItem().toString();
            String status = jComboBox4.getSelectedItem().toString();

            rmCTL.addNewRoom(stRoom, roomNumber, floor, roomType, capacity, building, status);

            JOptionPane.showMessageDialog(this, "Thêm phòng thành công!");

            UpdateInfor();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Thêm phòng thất bại do dữ liệu không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

//Nút sửa
    private void updateRoomFunc() {
        try {
            int stRoom = Integer.parseInt(idDField.getText());
            int roomNumber = Integer.parseInt(jTextPane1.getText());
            int floor = Integer.parseInt(jComboBox1.getSelectedItem().toString());
            String roomTypeValue = jComboBox2.getSelectedItem().toString();
            int roomType;
            if (roomTypeValue.equals("Nam")) {
                roomType = 1;
            } else {
                roomType = 0;
            }
            int capacity = Integer.parseInt(jTextPane2.getText());
            String building = jComboBox3.getSelectedItem().toString();
            String status = jComboBox4.getSelectedItem().toString();

            int option = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn cập nhật không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                rmCTL.updateRoom(stRoom, roomNumber, floor, roomType, capacity, building, status);

                JOptionPane.showMessageDialog(this, "Thông tin phòng đã được cập nhật.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

                UpdateInfor();
                resetFields();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Cập nhật phòng thất bại do dữ liệu không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

// Nút xóa
    public void deleteRoomFunc() {
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow >= 0) {
            int roomNumber = Integer.parseInt(jTextPane1.getText());
            int selectedID = Integer.parseInt(jTable1.getValueAt(selectedRow, 0).toString());

            int option = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                List<String> studentsInRoom = rmCTL.getStudentsInRoom(selectedID);
                if (!studentsInRoom.isEmpty()) {
                    String studentList = String.join("\n", studentsInRoom);
                    int confirmOption = JOptionPane.showConfirmDialog(this, "Có sinh viên còn ở phòng này: \n" + studentList + "\nBạn có muốn tiếp tục xóa không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                    if (confirmOption == JOptionPane.YES_OPTION) {
                        //Xóa sv
                        rmCTL.deleteStudentsInRoom(selectedID);
                    } else {
                        return;
                    }
                }
                rmCTL.deleteRoom(selectedID);
                DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                model.removeRow(selectedRow);
                messageDLable.setText("Phòng " + roomNumber + " đã được xóa thành công!");
                messageDLable.setForeground(new java.awt.Color(204, 0, 0));

                // Tạo một luồng độc lập để tự động đóng thông báo sau 5 giây
                Thread closeMessageThread = new Thread(() -> {
                    try {
                        Thread.sleep(3000); // Chờ 5 giây
                        messageDLable.setText(""); // Xóa thông báo sau 5 giây
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                });
                closeMessageThread.start(); // Bắt đầu luồng
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một phòng để xóa.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void resetFields() {
//        int nextRoomID = rmCTL.getNextRoomID();
//        idDField.setText(String.valueOf(nextRoomID));
        jTextPane1.setText("");
        jComboBox1.setSelectedIndex(0);
        jComboBox2.setSelectedIndex(0);
        jTextPane2.setText("");
        jComboBox3.setSelectedIndex(0);
        jComboBox4.setSelectedIndex(0);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        detailLabel1 = new javax.swing.JLabel();
        numberLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        idDField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        messageDLable = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jComboBox2 = new javax.swing.JComboBox<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextPane2 = new javax.swing.JTextPane();
        jComboBox3 = new javax.swing.JComboBox<>();
        jComboBox4 = new javax.swing.JComboBox<>();
        reloadBTN = new javax.swing.JButton();

        jTextField1.setText("jTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        detailLabel1.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        detailLabel1.setForeground(new java.awt.Color(0, 51, 51));
        detailLabel1.setText("DANH SÁCH PHÒNG");

        numberLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        numberLabel6.setForeground(new java.awt.Color(0, 51, 51));
        numberLabel6.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        numberLabel6.setText("Số lượng: 0");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Số Phòng", "Tầng", "Loại Phòng", "Số Lượng", "Tòa", "Trạng Thái"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Object.class, java.lang.String.class
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
        jScrollPane1.setViewportView(jTable1);

        jLabel2.setText("ID");

        idDField.setEnabled(false);
        idDField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idDFieldActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Thông tin phòng");

        jLabel8.setText("Số Phòng");

        jLabel4.setText("Loại Phòng");

        jLabel6.setText("Tầng");

        jLabel10.setText("Số Lượng");

        jLabel5.setText("Tòa");

        jLabel7.setText("Trạng thái");

        jButton1.setBackground(new java.awt.Color(0, 102, 255));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Sửa");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateDFieldActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(255, 0, 0));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Xóa");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteDFieldActionPerformed(evt);
            }
        });

        messageDLable.setForeground(new java.awt.Color(204, 0, 0));
        messageDLable.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        messageDLable.setText("Thông báo");

        jButton3.setBackground(new java.awt.Color(51, 204, 0));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Thêm");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddDFieldActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", " " }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jScrollPane3.setViewportView(jTextPane1);

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nam", "Nữ" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jScrollPane4.setViewportView(jTextPane2);

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "k1", "k2", "k3" }));
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Trống", "Đầy", "Đang bảo dưỡng" }));
        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(161, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(idDField, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(142, 142, 142)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane4)
                            .addComponent(jComboBox4, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 160, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButton3)
                                .addGap(30, 30, 30)
                                .addComponent(jButton1)
                                .addGap(30, 30, 30)
                                .addComponent(jButton2)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addComponent(messageDLable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(idDField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel6)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel10)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton3)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(messageDLable)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        reloadBTN.setBackground(new java.awt.Color(0, 51, 51));
        reloadBTN.setForeground(new java.awt.Color(255, 255, 255));
        reloadBTN.setText("Tải lại");
        reloadBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reloadBTNActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(detailLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(numberLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(reloadBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(detailLabel1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(reloadBTN)
                        .addComponent(numberLabel6)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void idDFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idDFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idDFieldActionPerformed

    private void UpdateDFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateDFieldActionPerformed
        // TODO add your handling code here:
        updateRoomFunc();
    }//GEN-LAST:event_UpdateDFieldActionPerformed

    private void DeleteDFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteDFieldActionPerformed
        // TODO add your handling code here:
        deleteRoomFunc();
    }//GEN-LAST:event_DeleteDFieldActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox4ActionPerformed

    private void AddDFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddDFieldActionPerformed
        // TODO add your handling code here:
        addRoomFunc();
    }//GEN-LAST:event_AddDFieldActionPerformed

    private void reloadBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reloadBTNActionPerformed
        // TODO add your handling code here:
        loadTable(true);
    }//GEN-LAST:event_reloadBTNActionPerformed

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
            java.util.logging.Logger.getLogger(editRoom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(editRoom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(editRoom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(editRoom.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new editRoom().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel detailLabel1;
    private javax.swing.JTextField idDField;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTextPane jTextPane2;
    private javax.swing.JLabel messageDLable;
    private javax.swing.JLabel numberLabel6;
    private javax.swing.JButton reloadBTN;
    // End of variables declaration//GEN-END:variables
}
