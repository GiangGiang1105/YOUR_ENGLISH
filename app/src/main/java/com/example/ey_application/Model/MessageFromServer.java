package com.example.ey_application.Model;

import com.google.gson.annotations.SerializedName;

public class MessageFromServer {
    @SerializedName("success")
    private int status;
    @SerializedName("message")
    private String message;
    @SerializedName("ID")
    private int ID;

    public MessageFromServer(int status, String message, int id) {
        this.status = status;
        this.message = message;
        this.ID = id;
    }

    public MessageFromServer() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
