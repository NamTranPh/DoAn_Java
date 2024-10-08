package controllers;

import models.Student;
import quanlyktx.data.ConnectDB;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import static quanlyktx.data.ConnectDB.stmt;

public class studentController {

    classController clCTL = new classController();
    static Student[] st;

    //Ham set so phan tu cua mang st    
    public static void setLength(String sq) throws SQLException {
        int Sl = 0;
        ResultSet rs1 = stmt.executeQuery(sq);
        while (rs1.next()) {
            Sl++;
        }
        st = new Student[Sl];
    }

    //Ham convert gioi tinh
    public String convertGender(String i) {
        if (i.equals("0")) {
            return "Nam";
        } else {
            return "Nữ";
        }
    }
    public String convertGenderToF(String i) {
        if (i.equals("Nữ")) {
            return "1";
        } else {
            return "0";
        }
    }

    //Ham xoa du lieu cua bang
    public void deleteTableData() {
        String sq = "DELETE FROM student WHERE stClass != -1;";
        try {
            stmt.executeUpdate(sq);
            System.out.println("Data deleted!");
        } catch (SQLException ex) {
            System.out.println("Loi ham deleteTableData() trong studentController: " + ex.getMessage());
        }
    }

    // Ham insert du lieu bang lên csdl
    public void insertDB(String stID, String stMSV, String stName, String stBirth, String stGender, String stPhone, int stClass, String stPlace, int stRoom) {
        String sq = "INSERT INTO student (stID, stMSV, stName, stBirth, stGender, stPhone, stClass, stPlace, stRoom) "
                + "VALUES ('" + stID + "','" + stMSV + "','" + stName + "','" + stBirth + "','" + stGender + "','" + stPhone + "','" + stClass + "','" + stPlace + "','" + stRoom + "')";
        try {
            stmt.executeUpdate(sq);
        } catch (SQLException ex) {
            System.err.println("Loi ham insertDB() trong studentController: " + ex.getMessage());
        }
    }

    //Ham lay du lieu db ve bang
    public void loadDB(JTable table, int stClass, int stRoom, boolean all) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int i = 0;

        String sq = "SELECT s.`stID`, s.`stMSV`, s.`stName`, s.`stBirth`, s.`stGender`, s.`stPhone`, c.`className`, s.`stPlace`, r.`roomNumber` "
                + "FROM student s "
                + "INNER JOIN class c ON s.`stClass` = c.`stClass` "
                + "INNER JOIN rooms r ON s.`stRoom` = r.`stRoom`";

        if (!all) {
            sq = sq + " WHERE s.`stClass` = " + stClass + " AND s.`stRoom` = " + stRoom;
        }
        sq = sq + ";";

        try {
            setLength(sq);

            ResultSet rs = stmt.executeQuery(sq);

            while (rs.next()) {
                int stID = Integer.parseInt(rs.getString("stID"));
                String stMSV = rs.getString("stMSV");
                String stName = rs.getString("stName");
                String stBirth = rs.getString("stBirth");
                String stGender = convertGender(rs.getString("stGender"));
                String stPhone = rs.getString("stPhone");
                String className = rs.getString("className");
                String stPlace = rs.getString("stPlace");
                int roomNumber = rs.getInt("roomNumber");

                st[i] = new Student(stID, stMSV, stName, stBirth, stGender, stPhone, className, stPlace, roomNumber);
                i++;

                String[] data = {String.valueOf(stID), stMSV, stName, stBirth, stGender, stPhone, className, stPlace, String.valueOf(roomNumber)};
                model.addRow(data);
            }

        } catch (Exception ex) {
            System.out.println("Lỗi: " + ex.getMessage());
        }
    }

    //Ham lay dl theo id - dung cho edit
    public Student getStudentByID(int IDkey) {
        Student st = new Student();
        int ID = 0;
        String msv = "";
        String name = "";
        String birth = "";
        String gender = "";
        String phone = "";
        String stClass = "";
        String className = "";
        String place = "";
        int roomNumber = 0;

        String sq = "Select s.*, r.roomNumber "
                + "from student s "
                + "inner join rooms r on s.stRoom = r.stRoom "
                + "where s.stID = " + IDkey + ";";

        try {
            ResultSet rs = stmt.executeQuery(sq);
            while (rs.next()) {
                ID = Integer.parseInt(rs.getString("stID"));
                msv = rs.getString("stMSV");
                name = rs.getString("stName");
                birth = rs.getString("stBirth");
                gender = convertGender(rs.getString("stGender"));
                phone = rs.getString("stPhone");
                stClass = rs.getString("stClass");
                place = rs.getString("stPlace");
                roomNumber = rs.getInt("roomNumber");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        className = clCTL.getClassNameInfor(stClass);
        st = new Student(ID, msv, name, birth, gender, phone, className, place, roomNumber);
        return st;
    }

    //Lấy thông tin sv tim kiem theo id
    public Object[] getStudentInfo(int stID) {
        Object[] rowData = null;
        String query = "SELECT s.stID, s.stMSV, s.stName, s.stBirth, s.stGender, s.stPhone, c.className, s.stPlace, r.roomNumber "
                + "FROM student s "
                + "INNER JOIN class c ON s.stClass = c.stClass "
                + "INNER JOIN rooms r ON s.stRoom = r.stRoom "
                + "WHERE s.stID = " + stID;

        try {
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                int ID = rs.getInt("stID");
                String MSV = rs.getString("stMSV");
                String Name = rs.getString("stName");
                String Birth = rs.getString("stBirth");
                String Gender = rs.getString("stGender");
                String Phone = rs.getString("stPhone");
                String ClassName = rs.getString("className");
                String Place = rs.getString("stPlace");
                int RoomNumber = rs.getInt("roomNumber");

                rowData = new Object[]{ID, MSV, Name, Birth, Gender, Phone, ClassName, Place, RoomNumber};
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rowData;
    }
    
    //Cập nhật thông tin
    public void updateStInfor(Student st) {
        int ID = st.getStID();
        String name = st.getStName();
        String birth = st.getStBirth();
        String gender = convertGenderToF(st.getStGender());
        String phone = st.getStPhone();
        String className = st.getStClass();
        int stClass = clCTL.getstClassInfor(className);
        String place = st.getStPlace();
        int roomNumber = st.getStRoom();
        String sq = "UPDATE student SET stName = '" + name + "', stBirth = '" + birth + "', "
                + "stGender = '" + gender + "', stPhone = '" + phone + "', stClass = " + stClass + ", "
                + "stPlace = '" + place + "', stRoom = " + roomNumber + " WHERE stID = " + ID;

        try {
            stmt.executeUpdate(sq);
            System.out.println("Updated!");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //Ham xoa sinh vien
    public void deleteSt(Student st) {
        String sq = "DELETE FROM student WHERE `stID`= " + st.getStID() + ";";
        try {
            stmt.executeUpdate(sq);
        } catch (Exception e) {
        }
    }

    public studentController() {
        new ConnectDB();
    }

//    public static void main(String[] args) {
//        studentController studentCTL = new studentController();
//        JTable table = new JTable();
//        Student st = studentCTL.getStudentByID(4);
//        System.err.println("Sinh viên cũ: "+st.getStName());
//        st.setStName("Le Thanh Bay");
//        studentCTL.updateStInfor(st);
//        System.out.println("Tên sinh viên mới: "+st.getStName());
//    }
    public static void main(String[] args) {
        studentController studentCTL = new studentController();
        Student st = new Student();
        st.setStID(4);
        Student studentFromDB = studentCTL.getStudentByID(st.getStID());
        System.err.println("Tên sinh viên cũ: " + studentFromDB.getStName());
        studentFromDB.setStName("Le Thanh Bay");
        studentCTL.updateStInfor(studentFromDB);
        System.out.println("Tên sinh viên mới: " + studentFromDB.getStName());
    }
}
