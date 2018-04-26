package singkorea.singkorea.com.singkorea.model;

import java.util.List;

public class EstateDetailParentModel {

    private String RESULT;
    private List<EstateDetailModel> LIST;

    public String getRESULT() {
        return RESULT;
    }

    public void setRESULT(String RESULT) {
        this.RESULT = RESULT;
    }

    public List<EstateDetailModel> getLIST() {
        return LIST;
    }

    public void setLIST(List<EstateDetailModel> LIST) {
        this.LIST = LIST;
    }


    public static class EstateDetailModel {
        private String idx;
        private String Title;
        private String AreaCode;
        private String Address;
        private String Tel;
        private String Email;
        private String Price;
        private String UserID;
        private String lat;
        private String lon;
        private String Content;
        private String RegDate;
        private String ViewCnt;
        private String ThumbPath;
        private List<ImageFileInfo> imgList;

        public String getIdx() {
            return idx;
        }

        public void setIdx(String idx) {
            this.idx = idx;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public String getAreaCode() {
            return AreaCode;
        }

        public void setAreaCode(String areaCode) {
            AreaCode = areaCode;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String address) {
            Address = address;
        }

        public String getTel() {
            return Tel;
        }

        public void setTel(String tel) {
            Tel = tel;
        }

        public String getEmail() {
            return Email;
        }

        public void setEmail(String email) {
            Email = email;
        }

        public String getPrice() {
            return Price;
        }

        public void setPrice(String price) {
            Price = price;
        }

        public String getUserID() {
            return UserID;
        }

        public void setUserID(String userID) {
            UserID = userID;
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

        public String getContent() {
            return Content;
        }

        public void setContent(String content) {
            Content = content;
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

        public String getThumbPath() {
            return ThumbPath;
        }

        public void setThumbPath(String thumbPath) {
            ThumbPath = thumbPath;
        }

        public List<ImageFileInfo> getImgList() {
            return imgList;
        }

        public void setImgList(List<ImageFileInfo> imgList) {
            this.imgList = imgList;
        }

        public static class ImageFileInfo {
            private String FileSeq;
            private String MasterPic;
            private String FilePath;
            private String RegDate;
            private String ThumbPath;
            private String ThumbPath_R;

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
        }

    }
}
