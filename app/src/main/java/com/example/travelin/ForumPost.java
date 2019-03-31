package com.example.travelin;

import java.util.ArrayList;

public class ForumPost {
    private User poster;
    private int upvotes;
    private int downvotes;
    private String text;
    private ForumPost parent;
    private ArrayList<ForumPost> replies = new ArrayList<>();
    public ForumPost(User p, String body) {
        upvotes = 0;
        downvotes = 0;
        text = body;
        poster = p;
    }

    public void setParent(ForumPost p) { parent = p; }

    public void addReply(ForumPost reply) {
        replies.add(reply);
    }

    public boolean deleteReply(ForumPost r) {
        if (replies.contains(r)) {
            replies.remove(r);
            return true;
        }
        return false;
    }

    public void editText(String newtext) {
        text = newtext;
    }

    public void upvote() {
        upvotes++;
    }

    public void downvote() {
        downvotes++;
    }

    public User getPoster() { return poster; }

    public int getUpvotes() { return upvotes; }

    public int getDownvotes() { return downvotes; }

    public String getText() { return text; }

    public ForumPost getParent() { return parent; }

    public ArrayList<ForumPost> getReplies() { return replies; }
}
