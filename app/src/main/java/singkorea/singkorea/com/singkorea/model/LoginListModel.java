package singkorea.singkorea.com.singkorea.model;

import java.util.List;

public class LoginListModel {

    private String RESULT;
    private List<LoginModel> LIST;

    public String getRESULT() {
        return RESULT;
    }

    public void setRESULT(String RESULT) {
        this.RESULT = RESULT;
    }

    public List<LoginModel> getLIST() {
        return LIST;
    }

    public void setLIST(List<LoginModel> LIST) {
        this.LIST = LIST;
    }

    public static class LoginModel {
        String UserID; //사용자아이디
        String UserName; //사용자명
        String Email; //이메일
        String Sex; //성별
        String Location; //거주지
        String State; //상태

        public void setUserID(String userID) {
            UserID = userID;
        }

        public void setUserName(String userName) {
            UserName = userName;
        }

        public void setEmail(String email) {
            Email = email;
        }

        public void setSex(String sex) {
            Sex = sex;
        }

        public void setLocation(String location) {
            Location = location;
        }

        public void setState(String state) {
            State = state;
        }

        public String getUserID() {
            return UserID;
        }

        public String getUserName() {
            return UserName;
        }

        public String getEmail() {
            return Email;
        }

        public String getSex() {
            return Sex;
        }

        public String getLocation() {
            return Location;
        }

        public String getState() {
            return State;
        }
    }
}
