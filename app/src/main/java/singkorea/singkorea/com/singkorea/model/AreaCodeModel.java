package singkorea.singkorea.com.singkorea.model;


import com.google.gson.annotations.Expose;

public class AreaCodeModel {

    private String AreaCode; //지역코드
    private String AreaName; //지역명
    private String AreaName_EN; //영문지역명
    private String RegDate; //등록일자
    private String shopcnt; //매장수

    @Expose private boolean isChecked = false;

    public String getAreaCode() {
        return AreaCode;
    }

    public void setAreaCode(String areaCode) {
        AreaCode = areaCode;
    }

    public String getAreaName() {
        return AreaName;
    }

    public void setAreaName(String areaName) {
        AreaName = areaName;
    }

    public String getAreaName_EN() {
        return AreaName_EN;
    }

    public void setAreaName_EN(String areaName_EN) {
        AreaName_EN = areaName_EN;
    }

    public String getRegDate() {
        return RegDate;
    }

    public void setRegDate(String regDate) {
        RegDate = regDate;
    }

    public String getShopcnt() {
        return shopcnt;
    }

    public void setShopcnt(String shopcnt) {
        this.shopcnt = shopcnt;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
