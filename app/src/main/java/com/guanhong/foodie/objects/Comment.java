package com.guanhong.foodie.objects;

public class Comment {

    private Author mAuthor;
    private String mComment;
    private String mCreatedTime;

    public Comment() {
//        mId = "";
//        mArticleId = "";
        mAuthor = new Author();
        mComment = "";
        mCreatedTime = "";
    }

    public Author getAuthor() {
        return mAuthor;
    }

    public void setAuthor(Author author) {
        mAuthor = author;
    }

    public String getComment() {
        return mComment;
    }

    public void setComment(String comment) {
        mComment = comment;
    }

    public String getCreatedTime() {
        return mCreatedTime;
    }

    public void setCreatedTime(String createdTime) {
        mCreatedTime = createdTime;
    }
}
