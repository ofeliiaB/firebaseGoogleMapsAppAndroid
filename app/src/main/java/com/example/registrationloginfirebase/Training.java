package com.example.registrationloginfirebase;

public class Training {
     String teacher;
     String name;
     String desc;
     String price;
     String trainingId;
     String markerID;
     String studentID1;
    public Training() {
    }
    public Training(String teacher, String name, String desc, String price,
                    String trainingId, String markerID, String studentID) {
        this.teacher = teacher;
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.trainingId = trainingId;
        this.markerID = markerID;
        this.studentID1 = studentID;
    }
    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
    public void setMarkerID(String markerID) {
        this.markerID = markerID;
    }
    public void setStudentID1(String studentID1) {
        this.studentID1 = studentID1;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public void setTrainingId(String trainingId) {
        this.trainingId = trainingId;
    }
    public String getTeacher() {
        return teacher;
    }
    public String getStudentID1() {
        return studentID1;
    }
    public String getName() {
        return name;
    }
    public String getMarkerID() {
        return markerID;
    }
    public String getPrice() {
        return price;
    }
    public String getDesc() {
        return desc;
    }
    public String getTrainingId() {
        return trainingId;
    }
}
