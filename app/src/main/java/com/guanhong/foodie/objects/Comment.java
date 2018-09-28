package com.guanhong.foodie.objects;

public class Comment {

    private String mId;
    private String mArticleId;
    private Author mOwner;
    private String mComment;
    private int mCreatedTime;

    public Comment() {
        mId = "";
        mArticleId = "";
        mOwner = new Author();
        mComment = "";
        mCreatedTime = -1;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getArticleId() {
        return mArticleId;
    }

    public void setArticleId(String articleId) {
        mArticleId = articleId;
    }

    public Author getOwner() {
        return mOwner;
    }

    public void setOwner(Author owner) {
        mOwner = owner;
    }

    public String getComment() {
        return mComment;
    }

    public void setComment(String comment) {
        mComment = comment;
    }

    public int getCreatedTime() {
        return mCreatedTime;
    }

    public void setCreatedTime(int createdTime) {
        mCreatedTime = createdTime;
    }
}
