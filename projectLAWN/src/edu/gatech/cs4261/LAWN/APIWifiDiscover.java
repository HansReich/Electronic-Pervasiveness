package edu.gatech.cs4261.LAWN;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

public class APIWifiDiscover extends DeviceDiscover {
    private static final String protocolType = "APIWifiDiscover"; 
    private static final String TAG = "APIWifiDiscover";
    private static DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    
    public APIWifiDiscover() {
        Log.v(TAG, "Constructor called");
    }
    
    @Override
    public String getProtocolType() {        
        return protocolType;
    }

    @Override
    public boolean isServiceAvailable() {
        
        return false;
    }

    @Override
    public boolean scan(double lat, double lon, Context ctx) {
        String apPage ="Some ap error";
        String routerMac = "No Mac Found";
        try {
            apPage = getAPData("50-348");
        } catch (ClientProtocolException e) {
            Log.e(TAG, e.toString());
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        }
        routerMac = findRouterMac(ctx);
        Log.d(TAG, apPage);
        return false;
    }
    
    private HttpEntity getUserData(String username) throws ClientProtocolException, IOException{
        String urlBase = "http://gardener.gatech.edu/whereami/getUserAP.php?User=";
        //TODO escape things somewhere 
        String url = urlBase + username;
        HttpContext httpContext = new BasicHttpContext();
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        String page = "Page was not created check for an error";
        HttpResponse response = httpClient.execute(httpGet, httpContext);
        int statusCode = response.getStatusLine().getStatusCode();
        Log.d(TAG, "Status code: " +statusCode);
        HttpEntity entity = response.getEntity();
        page = EntityUtils.toString(entity);        
        return entity;
        
    }
    
    /**parses xml to get the name of the ap we're attached to 
     * 
     * @param rmac the router's mac address to match with
     * @param xml the xml to parse as an HttpEntity
     * @return apname the name of the Access Point as a string or null if no match found
     */
    private String findAPNamefromXML(String rmac, HttpEntity xml) {
        try {
            //set up the document
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document dom = db.parse(xml.getContent());
            
            //get the subtree as a nodelist from the xml
            NodeList nl = dom.getElementsByTagName("apinfo");
            
            //loop to find the right apinfo
            int infoRes = -1;
            for(int i = 0; i < nl.getLength(); i++) {
                String tmac = getTextValue((Element)nl.item(i), "mac");
                
                //check the mac addresses
                if(tmac.equalsIgnoreCase(rmac)) {
                    infoRes = i;
                }
            }
            
            //checks if an ap was found and if so returns its name
            if(infoRes < 0) {
                return null;
            } else {
                return getTextValue((Element)nl.item(infoRes), "apname");
            }
        } catch (ParserConfigurationException e) {
            Log.e(TAG, e.toString());
        } catch (IllegalStateException e) {
            Log.e(TAG, e.toString());
        } catch (SAXException e) {
            Log.e(TAG, e.toString());
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        }
        
        //set up the
        return null;
    }
    
    /**
     * gets the text value held by the tag in a certain element
     * @param ele the parent element
     * @param tagName the tag name that holds the text data
     * @return
     */
    private static String getTextValue(Element ele, String tagName) {
        String textVal = null;
        NodeList nl = ele.getElementsByTagName(tagName);
        if(nl != null && nl.getLength() > 0) {
            Element el = (Element)nl.item(0);
            Node n = el.getFirstChild();
            if(n != null)
                textVal = el.getFirstChild().getNodeValue();
        }

        return textVal;
    }
  
    /**
     * Just like getTextValue but for ints
     * @param ele
     * @param tagName
     * @return
     */
    private static int getIntValue(Element ele, String tagName) {
        return Integer.parseInt(getTextValue(ele,tagName));
    }
    
    private String getAPData(String apName) throws ClientProtocolException, IOException{
        String urlBase = "http://gardener.gatech.edu/whereami/getAPStatus.php?APName=";
        //TODO escape things somewhere 
        String url = urlBase + apName;
        String page = "Page was not created check for an error";
        HttpContext httpContext = new BasicHttpContext();
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        
        HttpResponse response = httpClient.execute(httpGet, httpContext);
        int statusCode = response.getStatusLine().getStatusCode();
        Log.d(TAG, "Status code: " +statusCode);
        
        HttpEntity entity = response.getEntity();
        page = EntityUtils.toString(entity);        
        return page;        
    }
    
    private String findRouterMac(Context ctx){
        Log.d(TAG, "trying to find mac of connected router");
        String mac = "";
        ConnectivityManager myConnManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo myNetworkInfo = myConnManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        WifiManager myWifiManager = (WifiManager)ctx.getSystemService(Context.WIFI_SERVICE);
        WifiInfo myWifiInfo = myWifiManager.getConnectionInfo();
        mac = myWifiInfo.getBSSID();
        Log.i(TAG, mac);
        return mac;
    }
}
