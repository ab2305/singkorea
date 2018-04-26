package singkorea.singkorea.com.singkorea.model;

import com.google.gson.annotations.Expose;

public class CategoryModel {

    private String Code;
    private String CategoryName;
    private String CategoryName_EN;

    @Expose
    private boolean isChecked = false;

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

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
