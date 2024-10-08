package controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import quanlyktx.data.ConnectDB;
import static quanlyktx.data.ConnectDB.stmt;

public class classController {

    //Ham lay ma lop 
    public int getstClassInfor(String className) {
        int index = 0;
        String sq = "SELECT `stClass` FROM `class` WHERE `className` = '" + className + "'";
        try {
            ResultSet rs = stmt.executeQuery(sq);
            while (rs.next()) {
                index = Integer.parseInt(rs.getString("stClass"));
            }
        } catch (Exception e) {
            System.out.println("Loi o classController.java: " + e.getMessage());
        }
        return index;
    }

    //Ham lay ten lop 
    public String getClassNameInfor(String stClass) {
        String name = "";
        String sq = "SELECT `className` FROM `class` WHERE `stClass` = '" + stClass + "'";
        try {
            ResultSet rs = stmt.executeQuery(sq);
            while (rs.next()) {
                name = rs.getString("className");
            }
        } catch (Exception e) {
            System.out.println("Loi o classController.java: " + e.getMessage());
        }
        return name;
    }

    //Ham lay maLop
    public String getIdClass(String i) {
        String kq = null;
        try {
            String sq = "SELECT `stClass` FROM `class` WHERE `className` = '" + i + "'";
            ResultSet rs = stmt.executeQuery(sq);
            while (rs.next()) {
                kq = rs.getString("stClass");
            }
        } catch (SQLException ex) {
            System.err.println("Loi lay ma lop!");
        }
        return kq;
    }

    //Ham them 1 lơp
    public void addNewClass(String className) {
        String sq = "INSERT INTO `class`(`className`) VALUES ('" + className + "');";
        try {
            stmt.executeUpdate(sq);
        } catch (Exception e) {
        }
    }

    //Ham xoa 1 class -- getStudentsInClass -> deleteFeedbackByStudentID -> deleteStudentsInClass -> deleteClass
    public void deleteClass(int stClass) {
        // Xóa tất cả sinh viên trong lớp
        deleteStudentsInClass(stClass);

        // Tiếp tục xóa lớp
        try {
            String qr = "DELETE FROM `class` WHERE `stClass` = " + stClass;
            stmt.executeUpdate(qr);
        } catch (SQLException ex) {
            System.out.println("Lỗi khi xóa lớp: " + ex.getMessage());
        }
    }

    public List<String> getStudentsInClass(int stClass) {
        List<String> students = new ArrayList<>();
        try {
            String query = "SELECT stMSV, stName FROM student WHERE stClass = '" + stClass + "'";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String msv = rs.getString("stMSV");
                String name = rs.getString("stName");
                students.add(msv + " - " + name);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi truy vấn sinh viên trong lớp: " + e.getMessage());
        }
        return students;
    }

    public void deleteFeedbackByStudentID(int stID) {
        try {
            String query = "DELETE FROM feedback WHERE stID = " + stID;
            int rowsAffected = stmt.executeUpdate(query);
            System.out.println(rowsAffected + " phản hồi đã được xóa cho sinh viên có ID " + stID);
        } catch (SQLException ex) {
            System.err.println("Lỗi khi xóa phản hồi: " + ex.getMessage());
        }
    }

    public void deleteStudentsInClass(int stClass) {
        try {
            String deleteQuery = "DELETE FROM student WHERE stClass = " + stClass;
            int rowsAffected = stmt.executeUpdate(deleteQuery);
            System.out.println(rowsAffected + " sinh viên được xóa khỏi lớp có stClass " + stClass);
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa sinh viên: " + e.getMessage());
        }
    }

    //Ham truyen du lieu cho JComboBox
    public void setModelCBB(JComboBox CB1) {

        String sq = "SELECT `className` FROM `class`";

        try {
            ResultSet rs = stmt.executeQuery(sq);
            while (rs.next()) {
                String className = rs.getString("className");
                CB1.addItem(className);
            }
        } catch (SQLException ex) {
            System.out.println("Loi o classController.java: " + ex.getMessage());
        }
    }
}
