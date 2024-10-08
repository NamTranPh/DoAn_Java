package models;

public class Student {
    private int stID = 0;
    private String stMSV = "";
    private String stName = "";
    private String stBirth = "";
    private String stGender = "";
    private String stPhone = "";
    private String stClass = "";
    private String stPlace;
    private int stRoom;

    public Student() {
    }

    public Student(int stID, String stMSV, String stName, String stBirth, String stGender, String stPhone, String stClass, String stPlace, int stRoom) {
        this.stID = stID;
        this.stMSV = stMSV;
        this.stName = stName;
        this.stBirth = stBirth;
        this.stGender = stGender;
        this.stPhone = stPhone;
        this.stClass = stClass;
        this.stPlace = stPlace;
        this.stRoom = stRoom;
    }

    public int getStID() {
        return stID;
    }

    public void setStID(int stID) {
        this.stID = stID;
    }

     public String getStMSV() {
        return stMSV;
    }

    public void setStMSV(String stMSV) {
        this.stMSV = stMSV;
    }
    
    public String getStName() {
        return stName;
    }

    public void setStName(String stName) {
        this.stName = stName;
    }

    public String getStBirth() {
        return stBirth;
    }

    public void setStBirth(String stBirth) {
        this.stBirth = stBirth;
    }

    public String getStGender() {
        return stGender;
    }

    public void setStGender(String stGender) {
        this.stGender = stGender;
    }

    public String getStPhone() {
        return stPhone;
    }

    public void setStPhone(String stPhone) {
        this.stPhone = stPhone;
    }

    public String getStClass() {
        return stClass;
    }

    public void setStClass(String stClass) {
        this.stClass = stClass;
    }

    public String getStPlace() {
        return stPlace;
    }

    public void setStPlace(String stPlace) {
        this.stPlace = stPlace;
    }

    public int getStRoom() {
        return stRoom;
    }

    public void setStRoom(int stRoom) {
        this.stRoom = stRoom;
    }
}
