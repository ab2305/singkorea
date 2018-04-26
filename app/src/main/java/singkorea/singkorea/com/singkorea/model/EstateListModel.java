package singkorea.singkorea.com.singkorea.model;

import java.util.List;

public class EstateListModel {

    private String RESULT;
    private List<EstateModel> LIST;

    public String getRESULT() {
        return RESULT;
    }

    public void setRESULT(String RESULT) {
        this.RESULT = RESULT;
    }

    public List<EstateModel> getLIST() {
        return LIST;
    }

    public void setLIST(List<EstateModel> LIST) {
        this.LIST = LIST;
    }


    public static class EstateModel {
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
    }
}
