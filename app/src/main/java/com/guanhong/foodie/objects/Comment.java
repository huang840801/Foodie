package com.guanhong.foodie.objects;

public class Comment {

//    private String mId;
//    private String mArticleId;
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

//    public String getId() {
//        return mId;
//    }
//
//    public void setId(String id) {
//        mId = id;
//    }

//    public String getArticleId() {
//        return mArticleId;
//    }
//
//    public void setArticleId(String articleId) {
//        mArticleId = articleId;
//    }

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
