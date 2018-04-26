package singkorea.singkorea.com.singkorea.model;

import java.util.List;

public class ShopDetailListModel {

    private String RESULT;
    private List<ShopDetailModel> LIST;

    public String getRESULT() {
        return RESULT;
    }

    public void setRESULT(String RESULT) {
        this.RESULT = RESULT;
    }

    public List<ShopDetailModel> getLIST() {
        return LIST;
    }

    public void setLIST(List<ShopDetailModel> LIST) {
        this.LIST = LIST;
    }


    public static class ShopDetailModel {

        private int idx;
        private String ShopName;
        private String ShopName_EN;
        private String CategoryCode;
        private String CategoryName;
        private String CategoryName_EN;
        private String AreaCode;
        private String Tel ;
        private String Address ;
        private String Address_EN ;
        private String Homepage ;
        private String ShopInfo ;
        private String ShopInfo_EN ;
        private String lat ;
        private String lon ;
        private String Comment ;
        private String Comment_EN ;
        private String ThumbPath ;
        private String Startdate ;
        private String enddate ;
        private int replyCnt ;
        private int LikeCnt ;

        private List<ShopDetailModel.ImageFileInfo> FILELIST;

        public int getIdx() {
            return idx;
        }

        public void setIdx(int idx) {
            this.idx = idx;
        }

        public String getShopName() {
            return ShopName;
        }

        public void setShopName(String shopName) {
            ShopName = shopName;
        }

        public String getShopName_EN() {
            return ShopName_EN;
        }

        public void setShopName_EN(String shopName_EN) {
            ShopName_EN = shopName_EN;
        }

        public String getCategoryCode() {
            return CategoryCode;
        }

        public void setCategoryCode(String categoryCode) {
            CategoryCode = categoryCode;
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

        public String getAreaCode() {
            return AreaCode;
        }

        public void setAreaCode(String areaCode) {
            AreaCode = areaCode;
        }

        public String getTel() {
            return Tel;
        }

        public void setTel(String tel) {
            Tel = tel;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String address) {
            Address = address;
        }

        public String getAddress_EN() {
            return Address_EN;
        }

        public void setAddress_EN(String address_EN) {
            Address_EN = address_EN;
        }

        public String getHomepage() {
            return Homepage;
        }

        public void setHomepage(String homepage) {
            Homepage = homepage;
        }

        public String getShopInfo() {
            return ShopInfo;
        }

        public void setShopInfo(String shopInfo) {
            ShopInfo = shopInfo;
        }

        public String getShopInfo_EN() {
            return ShopInfo_EN;
        }

        public void setShopInfo_EN(String shopInfo_EN) {
            ShopInfo_EN = shopInfo_EN;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLon() {
            return lon;
        }

        public void setLon(String lon) {
            this.lon = lon;
        }

        public String getComment() {
            return Comment;
        }

        public void setComment(String comment) {
            Comment = comment;
        }

        public String getComment_EN() {
            return Comment_EN;
        }

        public void setComment_EN(String comment_EN) {
            Comment_EN = comment_EN;
        }

        public String getThumbPath() {
            return ThumbPath;
        }

        public void setThumbPath(String thumbPath) {
            ThumbPath = thumbPath;
        }

        public String getStartdate() {
            return Startdate;
        }

        public void setStartdate(String startdate) {
            Startdate = startdate;
        }

        public String getEnddate() {
            return enddate;
        }

        public void setEnddate(String enddate) {
            this.enddate = enddate;
        }

        public int getReplyCnt() {
            return replyCnt;
        }

        public void setReplyCnt(int replyCnt) {
            this.replyCnt = replyCnt;
        }

        public int getLikeCnt() {
            return LikeCnt;
        }

        public void setLikeCnt(int likeCnt) {
            LikeCnt = likeCnt;
        }

        public List<ImageFileInfo> getFILELIST() {
            return FILELIST;
        }

        public void setFILELIST(List<ImageFileInfo> FILELIST) {
            this.FILELIST = FILELIST;
        }


        public static class ImageFileInfo {
            private int idx;
            private String ShopIdx;
            private String FileSeq;
            private String MasterPic;
            private String FilePath;
            private String RegDate;
            private String ThumbPath;
            private String ThumbPath_R;
            private String ThumbPath_R1;

            public int getIdx() {
                return idx;
            }

            public void setIdx(int idx) {
                this.idx = idx;
            }

            public String getShopIdx() {
                return ShopIdx;
            }

            public void setShopIdx(String shopIdx) {
                ShopIdx = shopIdx;
            }

            public String getFileSeq() {
                return FileSeq;
            }

            public void setFileSeq(String fileSeq) {
                FileSeq = fileSeq;
            }

            public String getMasterPic() {
                return MasterPic;
            }

            public void setMasterPic(String masterPic) {
                MasterPic = masterPic;
            }

            public String getFilePath() {
                return FilePath;
            }

            public void setFilePath(String filePath) {
                FilePath = filePath;
            }

            public String getRegDate() {
                return RegDate;
            }

            public void setRegDate(String regDate) {
                RegDate = regDate;
            }

            public String getThumbPath() {
                return ThumbPath;
            }

            public void setThumbPath(String thumbPath) {
                ThumbPath = thumbPath;
            }

            public String getThumbPath_R() {
                return ThumbPath_R;
            }

            public void setThumbPath_R(String thumbPath_R) {
                ThumbPath_R = thumbPath_R;
            }

            public String getThumbPath_R1() {
                return ThumbPath_R1;
            }

            public void setThumbPath_R1(String thumbPath_R1) {
                ThumbPath_R1 = thumbPath_R1;
            }
        }
    }
}
