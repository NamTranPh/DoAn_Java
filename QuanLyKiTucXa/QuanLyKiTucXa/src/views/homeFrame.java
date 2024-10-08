package views;

import controllers.classController;
import controllers.studentController;
import controllers.RoomController;
import java.awt.Dimension;
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
import javax.swing.JFrame;
import java.io.FileInputStream;
import java.io.File;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import javax.swing.JFileChooser;

public class homeFrame extends javax.swing.JFrame {

    ColorFrame colorFrame = new ColorFrame();
    static String message = "Chào mừng đến hệ thống quản lí sinh viên!";
    studentController stCTL = new studentController();
    classController clCTL = new classController();
    RoomController rmCTL = new RoomController();
    User currentUser = new User();
    excelFile eFile = new excelFile();
    int rowSelectedIndex = 0;

    public homeFrame() {
        initComponents();
        setDetail();
    }

    public homeFrame(User user) {
        currentUser = user;
        initComponents();
        setDetail();
    }

//Ham Them cau hinh cho Frame home
    public void setDetail() {
        setTitle("Quản lí sinh viên ký túc xá");
        setBounds(150, 50, 1000, 400);
        clCTL.setModelCBB(classSelectBox);
        allClassCheckBox.setSelected(false);
        reloadBTN.setEnabled(true);
        searchBTN.setEnabled(false);
        loadTable(false);
        tbEditCheckBox.setEnabled(false);
        tbEditCheckBox.setSelected(false);
        updateBTN.setEnabled(false);
        homeTable.setEnabled(false);
        messageLabel.setText(message);
        messageLabel.setForeground(colorFrame.getPrimeryColor());
        userLabel.setText(currentUser.getUserName());
        idBTN.setText(String.valueOf(currentUser.getUserID()));

        setSize(1100, 405);
        setMinimumSize(new Dimension(1000, 400));
        // Can giua cua so chuong trinh
        setLocationRelativeTo(null);
    }

//Ham update
    public void UpdateInfor() {
        messageLabel.setText("");
        updateBTN.setEnabled(false);
        setNumberOfSt();
    }

// Sự kiện double click
    private void addDoubleClickEvent() {
        // Loại bỏ sự kiện double click hiện tại khỏi bảng
        for (MouseListener listener : homeTable.getMouseListeners()) {
            if (listener instanceof MouseAdapter) {
                homeTable.removeMouseListener(listener);
            }
        }
        // Thêm lại sự kiện double click mới
        homeTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedRow = homeTable.getSelectedRow();
                    if (selectedRow >= 0) {
                        int stIDSelected = Integer.parseInt((String) homeTable.getValueAt(selectedRow, 0));
                        editFrame editFrame = new editFrame(stIDSelected);
                        editFrame.setVisible(true);
                    }
                }
            }
        });
    }

//Ham load csdl hiển thi ra table
    public void loadTable(boolean all) {
        DefaultTableModel model = (DefaultTableModel) homeTable.getModel();
        model.setRowCount(0);
        String itemSelected = (String) classSelectBox.getSelectedItem();

        int index = clCTL.getstClassInfor(itemSelected);
        int index2 = rmCTL.getstRoomString(itemSelected);
        stCTL.loadDB(homeTable, index, index2, all);
        UpdateInfor();

        addDoubleClickEvent();
    }

//Ham allOrOnew()
    public void allOrOnew(int type) {
        classSelectBox.setModel(new javax.swing.DefaultComboBoxModel(new String[]{}));
        clCTL.setModelCBB(classSelectBox);
        if (allClassCheckBox.isSelected()) {
            tbEditCheckBox.setEnabled(true);
            reloadBTN.setEnabled(false);
            searchBTN.setEnabled(true);
            if (type == 0) {
                loadTable(true);
            }
        } else {
            tbEditCheckBox.setSelected(false);
            homeTable.setEnabled(false);
            tbEditCheckBox.setEnabled(false);
            reloadBTN.setEnabled(true);
            loadTable(false);
            searchBTN.setEnabled(false);
        }
    }
//Ham setTableEdit()

    public void setTableEdit() {
        if (tbEditCheckBox.isSelected()) {
            homeTable.setEnabled(true);
            updateBTN.setEnabled(true);
        } else {
            homeTable.setEnabled(false);
            updateBTN.setEnabled(false);
            messageLabel.setText("");
        }
    }
//Ham import/export file excel

    public void importExcelFunc() {
        eFile.importExcel(homeTable);
        UpdateInfor();
        allClassCheckBox.setSelected(true);
        allOrOnew(1);
    }

    public void exportExcelFunc() {
        eFile.exportExcel(homeTable);
        UpdateInfor();
    }
//Ham tinh so luong sinh vien

    public void setNumberOfSt() {
        int count = homeTable.getRowCount();
        numberLabel.setText("Số lượng: " + count);
    }

//Ham enable cac nut
    public void setEnabledBTN() {
        rowSelectedIndex = homeTable.getSelectedRow();
        if (rowSelectedIndex >= 0) {
            addBTN.setEnabled(true);
        } else {
            UpdateInfor();
        }
    }

//Ham hien thi khung detal
    public void getDetailInfor() {
        loadTable(true);
        int lastID = 0;
        //Tim id max
        for (int row = 0; row < homeTable.getRowCount(); row++) {
            int value = Integer.parseInt((String) homeTable.getValueAt(row, 0));
            if (value > lastID) {
                lastID = value;
            }
        }
        lastID++;

        addFrame addFrame = new addFrame();
        addFrame.studentM.setStID(lastID);
        addFrame.setVisible(true);
        addFrame.refresh();
    }

// Ham Click cho nut UploatDB
    public void uploadDBInfor() {
        stCTL.deleteTableData();
        boolean hasError = false;
        boolean isSuccess = true; // Biến để kiểm tra xem cập nhật có thành công không
        for (int row = 0; row < homeTable.getRowCount(); row++) {
            String stID = (String) homeTable.getValueAt(row, 0);
            String stMSV = (String) homeTable.getValueAt(row, 1);
            String stName = (String) homeTable.getValueAt(row, 2);
            String stBirth = (String) homeTable.getValueAt(row, 3);
            String stGender = stCTL.convertGenderToF((String) homeTable.getValueAt(row, 4));
            String stPhone = (String) homeTable.getValueAt(row, 5);
            String stClassValue = (String) homeTable.getValueAt(row, 6);
            int stClass = clCTL.getstClassInfor(stClassValue);
            String stPlace = (String) homeTable.getValueAt(row, 7);
            String roomNumberValue = (String) homeTable.getValueAt(row, 8);
            int roomNumber = Integer.parseInt(roomNumberValue);
            int stRoom = rmCTL.getstRoomInt(roomNumber);

            if (stRoom != -1) {
                stCTL.insertDB(stID, stMSV, stName, stBirth, stGender, stPhone, stClass, stPlace, stRoom);
            } else {
                hasError = true;
                isSuccess = false; // Đánh dấu rằng không cập nhật thành công
                System.err.println("Lỗi: Giá trị không hợp lệ");
                break;
            }
        }
        // Kiểm tra xem có lỗi từ phía backend không trước khi hiển thị thông báo
        if (!hasError && isSuccess) {
            JOptionPane.showMessageDialog(this, "Cập nhật cơ sở dữ liệu thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật cơ sở dữ liệu!", "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
    }

//Ham search
    private Timer timer;

    public void searchFunc() {
        if (keySearch.getText() != null) {
            String key = keySearch.getText();
            ArrayList<Integer> stIDs = new ArrayList<>();

            for (int row = 0; row < homeTable.getRowCount(); row++) {
                for (int col = 0; col < homeTable.getColumnCount(); col++) {
                    String TbValue = String.valueOf(homeTable.getValueAt(row, col));
                    if (TbValue.equalsIgnoreCase(key)) {
                        int stID = Integer.parseInt((String) homeTable.getValueAt(row, 0));
                        stIDs.add(stID);
                    }
                }
            }
            if (stIDs.size() == 0) {
                messageLabel.setText("Không tìm thấy thông tin tương ứng với từ khóa \"" + key + "\"");
                messageLabel.setForeground(Color.RED);
                timer = new Timer(5000, e -> {
                    messageLabel.setText("");
                    messageLabel.setForeground(null);
                    timer.stop();
                });
                timer.setRepeats(false); // Đảm bảo Timer chỉ chạy một lần
                timer.start();
            } else {
                // Hiển thị kết quả
                DefaultTableModel model = (DefaultTableModel) homeTable.getModel();
                model.setRowCount(0);

                for (int stID : stIDs) {
                    Object[] rowData = stCTL.getStudentInfo(stID);
                    model.addRow(rowData); // Thêm dòng dữ liệu mới                
                }

                setNumberOfSt();

                // Loại bỏ sự kiện double click hiện tại khỏi bảng
                for (MouseListener listener : homeTable.getMouseListeners()) {
                    if (listener instanceof MouseAdapter) {
                        homeTable.removeMouseListener(listener);
                    }
                }
                // Thêm lại sự kiện double click mới
                homeTable.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getClickCount() == 2) {
                            int selectedRow = homeTable.getSelectedRow();
                            if (selectedRow >= 0) {
                                int stIDSelected = (int) homeTable.getValueAt(selectedRow, 0);
                                editFrame editFrame = new editFrame(stIDSelected);
                                editFrame.setVisible(true);
                            }
                        }
                    }
                });
            }
        }
    }

    ///Ham xác nhận
    public void homeTableNote(int type) {
        if (!allClassCheckBox.isSelected()) {
            messageLabel.setForeground(Color.RED);
            if (type == 0) {
                messageLabel.setText("! Bảng có thể chỉnh sửa chỉ có thể được kiểm tra trong khi Tất cả được chọn");
            } else {
                messageLabel.setText("");
            }
        }
    }

    //Setting
    public void settingFunc() {
        userProfileFrame uPF = new userProfileFrame(currentUser);
        uPF.setVisible(true);
    }

    //FeedBack
    public void manFeedbackFunc() {
        managerFeedback feed = new managerFeedback();
        feed.setVisible(true);
    }
//public void displayDocxFile() {
//    JFileChooser fileChooser = new JFileChooser();
//    int result = fileChooser.showOpenDialog(this);
//    if (result == JFileChooser.APPROVE_OPTION) {
//        File selectedFile = fileChooser.getSelectedFile();
//        String filePath = selectedFile.getAbsolutePath();
//        
//        if (!filePath.toLowerCase().endsWith(".docx")) {
//            // Kiểm tra phần mở rộng của file
//            JOptionPane.showMessageDialog(this, "Vui lòng chọn file định dạng .docx", "Lỗi", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//        
//        try {
//            FileInputStream fis = new FileInputStream(selectedFile);
//            XWPFDocument document = new XWPFDocument(fis);
//            XWPFWordExtractor extractor = new XWPFWordExtractor(document);
//            String content = extractor.getText();
//            JOptionPane.showMessageDialog(this, content, "Nội quy", JOptionPane.PLAIN_MESSAGE);
//            fis.close();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//}

    private void displayNoiQuy() {
        NoiQuy noiQuy = new NoiQuy();
        noiQuy.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        userLabel = new javax.swing.JLabel();
        idBTN = new javax.swing.JButton();
        addBTN = new javax.swing.JButton();
        ImportBTN = new javax.swing.JButton();
        exportBTN = new javax.swing.JButton();
        updateBTN = new javax.swing.JButton();
        settingBTN = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        numberLabel = new javax.swing.JLabel();
        searchBTN = new javax.swing.JButton();
        reloadBTN = new javax.swing.JButton();
        classSelectBox = new javax.swing.JComboBox<>();
        allClassCheckBox = new javax.swing.JCheckBox();
        tbEditCheckBox = new javax.swing.JCheckBox();
        keySearch = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        homeTable = new javax.swing.JTable();
        noiQuyBTN = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        messageLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));

        jPanel1.setBackground(new java.awt.Color(0, 51, 51));
        jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        userLabel.setFont(new java.awt.Font("Segoe UI", 2, 10)); // NOI18N
        userLabel.setForeground(new java.awt.Color(255, 153, 0));
        userLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        userLabel.setText("Tài khoản");

        idBTN.setText("image");

        addBTN.setBackground(new java.awt.Color(0, 51, 51));
        addBTN.setForeground(new java.awt.Color(255, 255, 255));
        addBTN.setText("Đăng kí sinh viên mới");
        addBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBTNActionPerformed(evt);
            }
        });

        ImportBTN.setBackground(new java.awt.Color(0, 51, 51));
        ImportBTN.setForeground(new java.awt.Color(255, 255, 255));
        ImportBTN.setText("Nhập");
        ImportBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ImportBTNActionPerformed(evt);
            }
        });

        exportBTN.setBackground(new java.awt.Color(0, 51, 51));
        exportBTN.setForeground(new java.awt.Color(255, 255, 255));
        exportBTN.setText("Xuất");
        exportBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportBTNActionPerformed(evt);
            }
        });

        updateBTN.setBackground(new java.awt.Color(0, 51, 51));
        updateBTN.setForeground(new java.awt.Color(255, 255, 255));
        updateBTN.setText("Cập nhật cơ sở dữ liệu");
        updateBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateBTNActionPerformed(evt);
            }
        });

        settingBTN.setBackground(new java.awt.Color(0, 51, 51));
        settingBTN.setForeground(new java.awt.Color(255, 255, 255));
        settingBTN.setText("Cài đặt");
        settingBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                settingBTNActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(0, 51, 51));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Quản lí đánh giá");
        jButton1.setToolTipText("");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                managerFeedbackBTNActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(userLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(ImportBTN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(exportBTN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(updateBTN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(settingBTN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(idBTN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(addBTN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(idBTN)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(userLabel)
                .addGap(18, 18, 18)
                .addComponent(addBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(ImportBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(exportBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(updateBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(settingBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        numberLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        numberLabel.setForeground(new java.awt.Color(0, 51, 51));
        numberLabel.setText("Số lượng: 0");

        searchBTN.setBackground(new java.awt.Color(255, 153, 0));
        searchBTN.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        searchBTN.setForeground(new java.awt.Color(255, 255, 255));
        searchBTN.setText("Tìm kiếm");
        searchBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBTNActionPerformed(evt);
            }
        });

        reloadBTN.setBackground(new java.awt.Color(0, 51, 51));
        reloadBTN.setForeground(new java.awt.Color(255, 255, 255));
        reloadBTN.setText("Tải lại");
        reloadBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reloadBTNActionPerformed(evt);
            }
        });

        classSelectBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                classSelectBoxActionPerformed(evt);
            }
        });

        allClassCheckBox.setForeground(new java.awt.Color(0, 51, 51));
        allClassCheckBox.setText("All");
        allClassCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                allClassCheckBoxActionPerformed(evt);
            }
        });

        tbEditCheckBox.setText("Các mục được sửa");
        tbEditCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbEditCheckBoxActionPerformed(evt);
            }
        });

        keySearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                keySearchActionPerformed(evt);
            }
        });

        homeTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Mã sinh viên", "Họ tên", "Ngày sinh", "Giới tính", "Số điện thoại", "Lớp", "Địa chỉ", "Số Phòng"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        homeTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        homeTable.setCellSelectionEnabled(true);
        homeTable.setGridColor(new java.awt.Color(255, 255, 255));
        homeTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                homeTableMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                homeTableMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                homeTableMouseExited(evt);
            }
        });
        jScrollPane1.setViewportView(homeTable);

        noiQuyBTN.setBackground(new java.awt.Color(0, 51, 51));
        noiQuyBTN.setForeground(new java.awt.Color(255, 255, 255));
        noiQuyBTN.setText("Nội quy phòng ở");
        noiQuyBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                noiQuyBTNActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(noiQuyBTN)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(classSelectBox, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(reloadBTN)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(allClassCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(numberLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tbEditCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                        .addComponent(keySearch, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchBTN)
                        .addGap(17, 17, 17))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(searchBTN)
                        .addComponent(keySearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(noiQuyBTN)
                        .addComponent(classSelectBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(reloadBTN)
                        .addComponent(tbEditCheckBox)
                        .addComponent(numberLabel)
                        .addComponent(allClassCheckBox)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        messageLabel.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        messageLabel.setForeground(new java.awt.Color(153, 0, 0));
        messageLabel.setText("This is your message!");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(messageLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 726, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(messageLabel))
                .addContainerGap())
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void reloadBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reloadBTNActionPerformed
        // TODO add your handling code here:
        loadTable(false);
    }//GEN-LAST:event_reloadBTNActionPerformed

    private void ImportBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ImportBTNActionPerformed
        // TODO add your handling code here:
        importExcelFunc();
    }//GEN-LAST:event_ImportBTNActionPerformed

    private void exportBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportBTNActionPerformed
        // TODO add your handling code here:
        exportExcelFunc();
    }//GEN-LAST:event_exportBTNActionPerformed

    private void addBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBTNActionPerformed
        // TODO add your handling code here:
        getDetailInfor();
    }//GEN-LAST:event_addBTNActionPerformed

    private void updateBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBTNActionPerformed
        // TODO add your handling code here:
        uploadDBInfor();
    }//GEN-LAST:event_updateBTNActionPerformed

    private void allClassCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_allClassCheckBoxActionPerformed
        // TODO add your handling code here:
        allOrOnew(0);
    }//GEN-LAST:event_allClassCheckBoxActionPerformed

    private void tbEditCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbEditCheckBoxActionPerformed
        // TODO add your handling code here:
        setTableEdit();
    }//GEN-LAST:event_tbEditCheckBoxActionPerformed

    private void searchBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBTNActionPerformed
        // TODO add your handling code here:
        searchFunc();
    }//GEN-LAST:event_searchBTNActionPerformed

    private void settingBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_settingBTNActionPerformed
        // TODO add your handling code here:
        settingFunc();
    }//GEN-LAST:event_settingBTNActionPerformed

    private void managerFeedbackBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_managerFeedbackBTNActionPerformed
        // TODO add your handling code here:
        manFeedbackFunc();
    }//GEN-LAST:event_managerFeedbackBTNActionPerformed

    private void homeTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeTableMouseClicked
        // TODO add your handling code here:
        setEnabledBTN();
    }//GEN-LAST:event_homeTableMouseClicked

    private void homeTableMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeTableMouseEntered
        // TODO add your handling code here:
        homeTableNote(0);
    }//GEN-LAST:event_homeTableMouseEntered

    private void homeTableMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeTableMouseExited
        // TODO add your handling code here:
        homeTableNote(1);
    }//GEN-LAST:event_homeTableMouseExited

    private void keySearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keySearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_keySearchActionPerformed

    private void classSelectBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_classSelectBoxActionPerformed

    }//GEN-LAST:event_classSelectBoxActionPerformed

    private void noiQuyBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_noiQuyBTNActionPerformed
        // TODO add your handling code here:
//        displayDocxFile();
        displayNoiQuy();
    }//GEN-LAST:event_noiQuyBTNActionPerformed

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
            java.util.logging.Logger.getLogger(homeFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(homeFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(homeFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(homeFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new homeFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ImportBTN;
    private javax.swing.JButton addBTN;
    private javax.swing.JCheckBox allClassCheckBox;
    private javax.swing.JComboBox<String> classSelectBox;
    private javax.swing.JButton exportBTN;
    private javax.swing.JTable homeTable;
    private javax.swing.JButton idBTN;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField keySearch;
    private javax.swing.JLabel messageLabel;
    private javax.swing.JButton noiQuyBTN;
    private javax.swing.JLabel numberLabel;
    private javax.swing.JButton reloadBTN;
    private javax.swing.JButton searchBTN;
    private javax.swing.JButton settingBTN;
    private javax.swing.JCheckBox tbEditCheckBox;
    private javax.swing.JButton updateBTN;
    private javax.swing.JLabel userLabel;
    // End of variables declaration//GEN-END:variables
}
