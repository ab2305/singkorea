package singkorea.singkorea.com.singkorea.model;

public class BannerModel {

    private long idx;
    private String BannerName;
    private String BannerName_EN;
    private String BannerType;
    private String LinkType;
    private String Link;
    private String FilePath;

    public long getIdx() {
        return idx;
    }

    public void setIdx(long idx) {
        this.idx = idx;
    }

    public String getBannerName() {
        return BannerName;
    }

    public void setBannerName(String bannerName) {
        BannerName = bannerName;
    }

    public String getBannerName_EN() {
        return BannerName_EN;
    }

    public void setBannerName_EN(String bannerName_EN) {
        BannerName_EN = bannerName_EN;
    }

    public String getBannerType() {
        return BannerType;
    }

    public void setBannerType(String bannerType) {
        BannerType = bannerType;
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
}
