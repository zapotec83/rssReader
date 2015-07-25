package es.jorider.rssreader.connections;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import es.jorider.rssreader.constants.ConstantsCon;
import es.jorider.rssreader.constants.ConstantsRss;
import es.jorider.rssreader.interfaces.OnDownloadImages;
import es.jorider.rssreader.model.RespServer;
import es.jorider.rssreader.model.RssItem;

/**
 * Created by jorge
 */
public class HostConnection {

    public final String TAG = HostConnection.class.getName();

    /**
     * Method to make the petition
     *
     * @param urlString
     */
    public RespServer getNews(final String urlString) {

        RespServer respServer = new RespServer();

        try {
            URL url = new URL(urlString);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(ConstantsCon.MILIS_READ_TIMEOUT /* milliseconds */);
            conn.setConnectTimeout(ConstantsCon.MILIS_CON_TIMEOUT /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            InputStream stream = conn.getInputStream();
            List<RssItem> itemList = parserResponse(stream);
            stream.close();
            respServer.setItemList(itemList);

        } catch (IOException e) {
            respServer.setExceptionCaught(true);
            respServer.setErrorMsg("" + ((e!= null && e.getMessage()!= null)? e.getMessage(): "IOException"));
        }

        return respServer;
    }


    /**
     * Parse response from server
     *
     * @param inPutStream   InputStream
     * @return              List<RssItem>
     */
    public List<RssItem> parserResponse (InputStream inPutStream) {

        List<RssItem> itemsList = new ArrayList<RssItem>();

        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(new InputSource(inPutStream));
            doc.getDocumentElement().normalize();

            NodeList list = doc.getElementsByTagName("item");
            int length = list.getLength();

            for (int i = 0; i < length; i++) {

                Node currentNode = list.item(i);
                RssItem item = new RssItem();

                NodeList nodeChild = currentNode.getChildNodes();
                int cLength = nodeChild.getLength();

                for (int j = 1; j < cLength; j = j + 2) {
                    String nodeName = nodeChild.item(j).getNodeName(), nodeString = null;
                    if(nodeChild.item(j).getFirstChild() != null){
                        nodeString = nodeChild.item(j).getFirstChild().getNodeValue();
                    } else if (nodeChild.item(j).getAttributes()!= null && nodeChild.item(j).getAttributes().getLength() > 2) {
                        nodeString = nodeChild.item(j).getAttributes().item(2).getNodeValue();
                    }
                    if (nodeString != null) {
                        if (ConstantsRss.TITLE.equals(nodeName)) {
                            item.setTitle(nodeString);
                        } else if (ConstantsRss.DESCRIPTION.equals(nodeName)) {
                            item.setDescription(nodeString);
                        } else if (ConstantsRss.DATE.equals(nodeName)) {
                            item.setDate(nodeString);
                        } else if (ConstantsRss.IMAGE.equals(nodeName)){
                            item.setImageSrc(nodeString);
                        }
                    }
                }
                itemsList.add(item);
            }
        } catch (Exception e) {
            return new ArrayList<RssItem>();
        }

        return itemsList;
    }
}
