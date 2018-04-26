package singkorea.singkorea.com.singkorea.model;

import java.util.List;

public class ClipListModel {
    private String RESULT;
    private String TOTALPAGE;
    private String PAGE;
    private List<ClipModel> LIST;

    public String getRESULT() {
        return RESULT;
    }

    public void setRESULT(String RESULT) {
        this.RESULT = RESULT;
    }

    public String getTOTALPAGE() {
        return TOTALPAGE;
    }

    public void setTOTALPAGE(String TOTALPAGE) {
        this.TOTALPAGE = TOTALPAGE;
    }

    public String getPAGE() {
        return PAGE;
    }

    public void setPAGE(String PAGE) {
        this.PAGE = PAGE;
    }

    public List<ClipModel> getLIST() {
        return LIST;
    }

    public void setLIST(List<ClipModel> LIST) {
        this.LIST = LIST;
    }
}
