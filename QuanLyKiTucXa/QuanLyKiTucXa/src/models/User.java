package models;

public class User {
    
//Khai bao cac thuoc tinh cuar User
    private int userID;
    private String userName;
    private String passWord;
//Ham khoi tao
    public User() {
    }
//Ham khoi tao co tham so 
    public User(int userID, String userName, String passWord) {
        this.userID = userID;
        this.userName = userName;
        this.passWord = passWord;
    }
//Cac phuong thu get va set
    public int getUserID() {
        return userID;
    }

    public void setUseID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
    
    
}
