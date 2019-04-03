package com.example.travelin;

public class Room {
    private String roomname = "";
    private String users = "";
    private String messagesSentBy = "";
    private String messagesenderhistory = "";

    public void addUser(String user) {
        if (users.equals("")) {
            users = users + user;
        } else {
            users = users + "|" + user;
        }
    }

    public void removeUser(String remove) {
        String[] allUsers = users.split("\\|");
        this.clearUsers();

        for (int i = 0; i < allUsers.length; i++) {
            if (!allUsers[i].equals(remove)) {
                this.addUser(allUsers[i]);
            }
        }
    }

    public void clearUsers() {
        users = "";
    }

    public String getUsers() {
        return users;
    }

    public void addMessageSender(String user) {
        if (messagesSentBy.equals("")) {
            messagesSentBy = messagesSentBy + user;
        } else {
            messagesSentBy = messagesSentBy + "|" + user;
        }
    }

    public String getMessageSenders() {
        return messagesSentBy;
    }

    public void addMessageSenderHistory(String user) {
        if (messagesenderhistory.equals("")) {
            messagesenderhistory = messagesenderhistory + user;
        } else {
            messagesenderhistory = messagesenderhistory + "|" + user;
        }
    }

    public String getMessageSenderHistory() {
        return messagesenderhistory;
    }

    public String getRoomname() {
        return roomname;
    }

    public void setRoomname(String roomname) {
        this.roomname = roomname;
    }
}
