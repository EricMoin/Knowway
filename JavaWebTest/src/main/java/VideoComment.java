public class VideoComment {
    int commentImageId;
    String commentAuthor;
    String commentWhere;
    String commentInfo;
    public VideoComment(){ }
    public VideoComment(int commentImageId,String commentAuthor,String commentWhere,String commentInfo){
        this.commentImageId = commentImageId;
        this.commentAuthor = commentAuthor;
        this.commentWhere = commentWhere;
        this.commentInfo = commentInfo;
    }
}
