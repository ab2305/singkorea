package singkorea.singkorea.com.singkorea.model;

public class TopNewsModel {

    private String idx;
    private String NewsType; //(Content : 내부링크, Link : 외부링크)
    private String NewsTitle;
    private String NewsTitle_EN;
    private String NewsLink; //외부링크주소

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getNewsType() {
        return NewsType;
    }

    public void setNewsType(String newsType) {
        NewsType = newsType;
    }

    public String getNewsTitle() {
        return NewsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        NewsTitle = newsTitle;
    }

    public String getNewsTitle_EN() {
        return NewsTitle_EN;
    }

    public void setNewsTitle_EN(String newsTitle_EN) {
        NewsTitle_EN = newsTitle_EN;
    }

    public String getNewsLink() {
        return NewsLink;
    }

    public void setNewsLink(String newsLink) {
        NewsLink = newsLink;
    }
}
