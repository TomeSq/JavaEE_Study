package beans;

import javax.ejb.Stateless;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

@Stateless
public class WeatherFeed {
    //private static final String RSS_URL = "http://rss.weather.yahoo.co.jp/rss/days/42.xml";

    public String rss(int pref) {
        String rssUrl = "http://rss.weather.yahoo.co.jp/rss/days/" + pref + ".xml";
        StringBuilder sb = new StringBuilder();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(rssUrl);
            Element root = document.getDocumentElement();
            NodeList channel = root.getElementsByTagName("channel");
            NodeList title = ((Element) channel.item(0)).getElementsByTagName("title");
            sb.append(title.item(0).getFirstChild().getNodeValue());
            sb.append("\n");
            NodeList item_list = root.getElementsByTagName("item");
            for (int i = 0; i < item_list.getLength(); i++) {
                Element element = (Element) item_list.item(i);
                NodeList item_title = element.getElementsByTagName("title");
                NodeList item_description = element.getElementsByTagName("description");
                sb.append(item_title.item(0).getFirstChild().getNodeValue());
                sb.append("\n");
            }
        } catch (Exception e) {
        }
        return sb.toString();
    }
}
