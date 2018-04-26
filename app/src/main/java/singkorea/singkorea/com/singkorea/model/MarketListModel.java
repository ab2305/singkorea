package singkorea.singkorea.com.singkorea.model;

import java.util.List;

public class MarketListModel {
    private String RESULT;
    private String TOTALPAGE;
    private List<MarketItemModel> LIST;

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

    public List<MarketItemModel> getLIST() {
        return LIST;
    }

    public void setLIST(List<MarketItemModel> LIST) {
        this.LIST = LIST;
    }
}
