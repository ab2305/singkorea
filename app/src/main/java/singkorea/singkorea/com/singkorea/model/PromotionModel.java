package singkorea.singkorea.com.singkorea.model;

public class PromotionModel {

    private int rank;
    private String idx;
    private String ProName;
    private String ProName_EN;
    private String CategoryName_EN;

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getProName() {
        return ProName;
    }

    public void setProName(String proName) {
        ProName = proName;
    }

    public String getProName_EN() {
        return ProName_EN;
    }

    public void setProName_EN(String proName_EN) {
        ProName_EN = proName_EN;
    }

    public String getCategoryName_EN() {
        return CategoryName_EN;
    }

    public void setCategoryName_EN(String categoryName_EN) {
        CategoryName_EN = categoryName_EN;
    }

    public String getLinkType() {
        return LinkType;
    }

    public void setLinkType(String linkType) {
        LinkType = linkType;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String getFilePath() {
        return FilePath;
    }

    public void setFilePath(String filePath) {
        FilePath = filePath;
    }

    private String LinkType;
    private String Link;
    private String FilePath;
}
