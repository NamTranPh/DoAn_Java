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
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import models.Student;
import quanlyktx.data.ConnectDB;
import static quanlyktx.data.ConnectDB.stmt;

public class RoomController {

    public List<Room> getRoomNumbers() throws SQLException {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM rooms";

        try (
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int stRoom = rs.getInt("stRoom");
                int roomNumber = rs.getInt("roomNumber");
                int floor = rs.getInt("floor");
                int roomType = rs.getInt("roomType");
                int capacity = rs.getInt("capacity");
                String building = rs.getString("building");
                String status = rs.getString("status");

                Room room = new Room(stRoom, roomNumber, floor, roomType, capacity, building, status);
                rooms.add(room);
            }
        }
        return rooms;
    }

    //Lay ma phong - dau vao chuoi
    public int getstRoomString(String roomNumber) {
        int roomID = 0;
        String query = "SELECT `stRoom` FROM `rooms` WHERE `roomNumber` = '" + roomNumber + "'";
        try {
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                roomID = rs.getInt("stRoom");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return roomID;
    }

    //Lay ma phong - dau vao so
    public int getstRoomInt(int roomNumber) {
        String query = "SELECT stRoom FROM rooms WHERE roomNumber = " + roomNumber + ";";
        int stRoom = -1;

        try {
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                stRoom = rs.getInt("stRoom");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return stRoom;
    }

    //Lấy số phòng
    public String getRoomNameInfor(String stRoom) {
        String name = "";
        String sq = "SELECT `roomNumber` FROM `rooms` WHERE `stRoom` = '" + stRoom + "'";
        try {
            ResultSet rs = stmt.executeQuery(sq);
            while (rs.next()) {
                name = rs.getString("roomNumber");
            }
        } catch (Exception e) {
            System.out.println("ERR in RoomController.java: " + e.getMessage());
        }
        return name;
    }

    //Hàm lấy mã phòng theo số phòng
    public String getRoomID(String roomNumber) {
        String kq = null;
        try {
            String sq = "SELECT `stRoom` FROM `rooms` WHERE `roomNumber` = '" + roomNumber + "'";
            ResultSet rs = stmt.executeQuery(sq);
            while (rs.next()) {
                kq = rs.getString("stRoom");
            }
        } catch (SQLException ex) {
            System.err.println("Lỗi khi lấy mã phòng!");
        }
        return kq;
    }

    //Them phong
    public void addNewRoom(int stRoom, int roomNumber, int floor, int roomType, int capacity, String building, String status) {
        String query = "INSERT INTO rooms (stRoom, roomNumber, floor, roomType, capacity, building, status) VALUES (" + stRoom + ", " + roomNumber + ", " + floor + ", " + roomType + ", " + capacity + ", '" + building + "', '" + status + "')";

        try {
            stmt.executeUpdate(query);
            System.out.println("Thêm phòng thành công!");
        } catch (SQLException ex) {
            System.err.println("Lỗi khi thêm phòng mới: " + ex.getMessage());
        }
    }

    //Tăng id
    public int getNextRoomID() {
        String query = "SELECT MAX(stRoom) AS max_id FROM rooms";
        try {
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                return rs.getInt("max_id") + 1;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    //Sửa phòng
    public void updateRoom(int stRoom, int roomNumber, int floor, int roomType, int capacity, String building, String status) {
        String query = "UPDATE rooms SET roomNumber = " + roomNumber + ", floor = " + floor + ", roomType = " + roomType + ", capacity = " + capacity + ", building = '" + building + "', status = '" + status + "' WHERE stRoom = " + stRoom;

        try {
            int rowsAffected = ConnectDB.stmt.executeUpdate(query);

            if (rowsAffected > 0) {
                System.out.println("Phòng đã được cập nhật thành công.");
            } else {
                System.out.println("Không có phòng nào được cập nhật.");
            }
        } catch (SQLException ex) {
            System.err.println("Lỗi khi cập nhật phòng: " + ex.getMessage());
        }
    }

    //Xóa phòng - getStudentsInRoom -> deleteStudentsInRoom -> deleteRoom
    public void deleteRoom(int stRoom) {
        String qr = "DELETE FROM `rooms` WHERE `stRoom` = " + stRoom;
        try {
            ConnectDB.stmt.executeUpdate(qr);
        } catch (SQLException ex) {
            System.out.println("Lỗi khi xóa phòng: " + ex.getMessage());
        }
    }

    public List<String> getStudentsInRoom(int stRoom) {
        List<String> students = new ArrayList<>();
        try {
            String query = "SELECT stMSV, stName FROM student WHERE stRoom = '" + stRoom + "'";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String msv = rs.getString("stMSV");
                String name = rs.getString("stName");
                students.add(msv + " - " + name);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi truy vấn sinh viên: " + e.getMessage());
        }
        return students;
    }

    public void deleteStudentsInRoom(int stRoom) {
        try {
            String deleteQuery = "DELETE FROM student WHERE stRoom = '" + stRoom + "'";
            int rowsAffected = stmt.executeUpdate(deleteQuery);
            System.out.println(rowsAffected + " sinh viên được xóa khỏi phòng có stRoom " + stRoom);
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa sinh viên: " + e.getMessage());
        }
    }

    //Ham lay du lieu db rooms ve bang
    public void loadDBRoom(JTable table, boolean all) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ trong bảng
        String sq = "SELECT stRoom, roomNumber, floor, roomType, capacity, building, status FROM rooms";
        if (!all) {
            //Điều kiện khi muốn chỉ hiển thị gì
        }
        try {
            ResultSet rs = stmt.executeQuery(sq);

            while (rs.next()) {
                int stRoom = rs.getInt("stRoom");
                int roomNumber = rs.getInt("roomNumber");
                int floor = rs.getInt("floor");
                int roomTypeValue = rs.getInt("roomType");
                String roomType;
                if (roomTypeValue == 1) {
                    roomType = "Nam";
                } else {
                    roomType = "Nữ";
                }
                int capacity = rs.getInt("capacity");
                String building = rs.getString("building");
                String status = rs.getString("status");
                // Thêm dữ liệu
                Object[] data = {stRoom, roomNumber, floor, roomType, capacity, building, status};
                model.addRow(data);
            }
        } catch (SQLException ex) {
            System.out.println("Lỗi: " + ex.getMessage());
        }
    }
}
