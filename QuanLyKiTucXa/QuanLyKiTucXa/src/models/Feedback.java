package models;

public class Feedback {
    private int feedbackID;
    private int stID;
    private String comment;
    private int status;

    public Feedback() {
    }
    
    public Feedback(int feedbackID, int stID, String comment, int status) {
        this.feedbackID = feedbackID;
        this.stID = stID;
        this.comment = comment;
        this.status = status;
    }
    
    private String stName;
    private String stMSV;
    private String className;
    private String stPhone;

    // Constructor mới
    public Feedback(int feedbackID, String stName, String stMSV, String className, String stPhone, String comment, int status) {
        this.feedbackID = feedbackID;
        this.stName = stName;
        this.stMSV = stMSV;
        this.className = className;
        this.stPhone = stPhone;
        this.comment = comment;
        this.status = status;
    }

    public int getFeedbackID() {
        return feedbackID;
    }

    public void setFeedbackID(int feedbackID) {
        this.feedbackID = feedbackID;
    }

    public int getStID() {
        return stID;
    }

    public void setStID(int stID) {
        this.stID = stID;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
