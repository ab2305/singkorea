package singkorea.singkorea.com.singkorea.model;

public class SubCategoryModel {


    private String CategoryCode;
    private String SubCategoryCode;
    private String SubCategoryName;
    private String SubCategoryName_EN;
    private int shopcnt;

    public String getCategoryCode() {
        return CategoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        CategoryCode = categoryCode;
    }

    public String getSubCategoryCode() {
        return SubCategoryCode;
    }

    public void setSubCategoryCode(String subCategoryCode) {
        SubCategoryCode = subCategoryCode;
    }

    public String getSubCategoryName() {
        return SubCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        SubCategoryName = subCategoryName;
    }

    public String getSubCategoryName_EN() {
        return SubCategoryName_EN;
    }

    public void setSubCategoryName_EN(String subCategoryName_EN) {
        SubCategoryName_EN = subCategoryName_EN;
    }

    public int getShopcnt() {
        return shopcnt;
    }

    public void setShopcnt(int shopcnt) {
        this.shopcnt = shopcnt;
    }
}
