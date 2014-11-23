package es.jorider.rssreader.model;

import java.util.List;

/**
 * Created by jorge on 22/11/14.
 */
public class RespServer {

    private List<RssItem> itemList = null;
    private boolean exceptionCaught = false;
    private String errorMsg = null;

    public List<RssItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<RssItem> itemList) {
        this.itemList = itemList;
    }

    public boolean isExceptionCaught() {
        return exceptionCaught;
    }

    public void setExceptionCaught(boolean exceptionCaught) {
        this.exceptionCaught = exceptionCaught;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
