package singkorea.singkorea.com.singkorea.model;

public class MarketItemModel {
    private String idx ; // idx
    private String ProductName ; // 상품명
    private String Code ; // 상품카테고리
    private String CategoryName ; // 상품카테고리명
    private String CategoryName_EN ; // 영문상품카테고리명
    private String UserID ; // 등록자아이디
    private String SellAddress ; // 판매자주소
    private String SellTel ; // 판매자연락처
    private String SellEmail ; // 판매자이메일
    private String SellType ; // 판매타입
    private String SellContent ; // 내용
    private String Price ; // 가격
    private String State ; // 상태
    private String RegDate ; // 등록일자
    private String ViewCnt ; // 조회수(사용안함)
    private String LikeCnt ; // 좋아요수 (사용안함)
    private String unlikeCnt ; // 싫어요수 (사용안함)
    private String ThumbPath ; // 썸네일주소
    private String replycnt ; // 댓글수 (사용안함)

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getCategoryName_EN() {
        return CategoryName_EN;
    }

    public void setCategoryName_EN(String categoryName_EN) {
        CategoryName_EN = categoryName_EN;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getSellAddress() {
        return SellAddress;
    }

    public void setSellAddress(String sellAddress) {
        SellAddress = sellAddress;
    }

    public String getSellTel() {
        return SellTel;
    }

    public void setSellTel(String sellTel) {
        SellTel = sellTel;
    }

    public String getSellEmail() {
        return SellEmail;
    }

    public void setSellEmail(String sellEmail) {
        SellEmail = sellEmail;
    }

    public String getSellType() {
        return SellType;
    }

    public void setSellType(String sellType) {
        SellType = sellType;
    }

    public String getSellContent() {
        return SellContent;
    }

    public void setSellContent(String sellContent) {
        SellContent = sellContent;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getRegDate() {
        return RegDate;
    }

    public void setRegDate(String regDate) {
        RegDate = regDate;
    }

    public String getViewCnt() {
        return ViewCnt;
    }

    public void setViewCnt(String viewCnt) {
        ViewCnt = viewCnt;
    }

    public String getLikeCnt() {
        return LikeCnt;
    }

    public void setLikeCnt(String likeCnt) {
        LikeCnt = likeCnt;
    }

    public String getUnlikeCnt() {
        return unlikeCnt;
    }

    public void setUnlikeCnt(String unlikeCnt) {
        this.unlikeCnt = unlikeCnt;
    }

    public String getThumbPath() {
        return ThumbPath;
    }

    public void setThumbPath(String thumbPath) {
        ThumbPath = thumbPath;
    }

    public String getReplycnt() {
        return replycnt;
    }

    public void setReplycnt(String replycnt) {
        this.replycnt = replycnt;
    }
}
