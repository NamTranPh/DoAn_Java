package models;

public class Room {
    private int stRoom;
    private int roomNumber;
    private int floor;
    private int roomType;
    private int capacity;
    private String building;
    private String status;

     public Room() {
    }
    
    public Room(int stRoom, int roomNumber, int floor, int roomType, int capacity, String building, String status) {
        this.stRoom = stRoom;
        this.roomNumber = roomNumber;
        this.floor = floor;
        this.roomType = roomType;
        this.capacity = capacity;
        this.building = building;
        this.status = status;
    }

    public int getStRoom() {
        return stRoom;
    }

    public void setStRoom(int stRoom) {
        this.stRoom = stRoom;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getRoomType() {
        return roomType;
    }

    public void setRoomType(int roomType) {
        this.roomType = roomType;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}