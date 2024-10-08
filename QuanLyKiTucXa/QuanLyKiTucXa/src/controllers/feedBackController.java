package controllers;

import static controllers.studentController.setLength;
import static controllers.studentController.st;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JTable;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import models.Room;
import models.Student;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import models.Feedback;

import quanlyktx.data.ConnectDB;
import static quanlyktx.data.ConnectDB.con;
import static quanlyktx.data.ConnectDB.stmt;

public class feedBackController {

    //Hàm lấy stID theo feedbackID
    public int getStudentIDByFeedbackID(int feedbackID) {
        int studentID = -1;
        try {
            String query = "SELECT stID FROM feedback WHERE feedbackID = " + feedbackID;
            ResultSet resultSet = ConnectDB.stmt.executeQuery(query);
            if (resultSet.next()) {
                studentID = resultSet.getInt("stID");
            }
        } catch (SQLException ex) {
            System.err.println("Lỗi khi truy vấn stID từ feedbackID: " + ex.getMessage());
        }
        return studentID;
    }

//    // Thêm một feedback mới vào cơ sở dữ liệu -- LIEN QUAN DEN SINH VIEN
//    public void addFeedback(int stID, String comment, int status) {
//        String query = "INSERT INTO `feedback` (`stID`, `comment`, `status`) VALUES ('" + stID + "', '" + comment + "', '" + status + "')";
//        try {
//            ConnectDB.stmt.executeUpdate(query);
//        } catch (SQLException ex) {
//            System.out.println("Error adding feedback: " + ex.getMessage());
//        }
//    }
//
    // Xóa dựa trên id của feedback
    public void deleteFeedback(int feedbackID) {
        String query = "DELETE FROM `feedback` WHERE `feedbackID` = '" + feedbackID + "'";
        try {
            ConnectDB.stmt.executeUpdate(query);
        } catch (SQLException ex) {
            System.out.println("Lỗi khi xóa phản hồi: " + ex.getMessage());
        }
    }

    // Cập nhật thông tin của 1 feedback
    public void updateFeedbackStatus(int feedbackID, String comment, String status) {
        int statusValue = status.equalsIgnoreCase("Duyệt") ? 1 : 0;

        String updateQuery = "UPDATE feedback SET comment = '" + comment + "', status = " + statusValue + " WHERE feedbackID = " + feedbackID;

        try {
            Statement stmt = con.createStatement();

            int rowsUpdated = stmt.executeUpdate(updateQuery);

            if (rowsUpdated > 0) {
                System.out.println("Trạng thái phản hồi đã được cập nhật thành công!");
            } else {
                System.out.println("Cập nhật trạng thái không thành công!");
            }

            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //Hàm lấy dữ liệu theo id
    public Feedback getFeedbackByID(int IDkey) {
        Feedback fb = new Feedback();

        String sq = "SELECT feedback.feedbackID, student.stName, student.stMSV, class.className, student.stPhone, feedback.comment, feedback.status "
                + "FROM feedback "
                + "INNER JOIN student ON feedback.stID = student.stID "
                + "INNER JOIN class ON student.stClass = class.stClass "
                + "WHERE feedback.feedbackID = " + IDkey;
        try {
            ResultSet rs = stmt.executeQuery(sq);
            while (rs.next()) {
                int feedbackID = rs.getInt("feedbackID");
                String name = rs.getString("stName");
                String msv = rs.getString("stMSV");
                String phone = rs.getString("stPhone");
                String className = rs.getString("className");
                String comment = rs.getString("comment");

                int status = rs.getInt("status");
                String statusText = (status == 1) ? "Duyệt" : "Chưa duyệt";

                //Hien thi
                fb = new Feedback(feedbackID, name, msv, phone, className, comment, status);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return fb;
    }

    // Hàm load dữ liệu từ bảng feedback vào bảng
    public void loadDBFeedback(JTable table, boolean all) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        String sq = "SELECT feedback.feedbackID, student.stName, student.stMSV, class.className, student.stPhone, feedback.comment, feedback.status "
                + "FROM feedback "
                + "INNER JOIN student ON feedback.stID = student.stID "
                + "INNER JOIN class ON student.stClass = class.stClass";
        try {
            ResultSet rs = stmt.executeQuery(sq);

            while (rs.next()) {
                int feedbackID = rs.getInt("feedbackID");
                String stName = rs.getString("stName");
                String stMSV = rs.getString("stMSV");
                String className = rs.getString("className");
                int stPhone = rs.getInt("stPhone");
                String comment = rs.getString("comment");

                int status = rs.getInt("status");
                String statusText = (status == 1) ? "Duyệt" : "Chưa duyệt";

                //Hien
                Object[] data = {feedbackID, stName, stMSV, className, stPhone, comment, statusText};
                model.addRow(data);
            }
        } catch (SQLException ex) {
            System.out.println("Lỗi: " + ex.getMessage());
        }
    }

}
