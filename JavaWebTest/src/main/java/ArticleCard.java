public class ArticleCard {
    String author;
    int authorIcon;
    String articleTitle;
    String articleInfo;

    public ArticleCard(){ }
    public ArticleCard(String author,int authorIcon,String articleTitle,String articleInfo){
        this.author = author;
        this.authorIcon = authorIcon;
        this.articleTitle= articleTitle;
        this.articleInfo=articleInfo;
    }
}
