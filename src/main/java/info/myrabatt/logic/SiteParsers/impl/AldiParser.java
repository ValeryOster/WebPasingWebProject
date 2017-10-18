package info.myrabatt.logic.SiteParsers.impl;

import info.myrabatt.data.entities.AngebotElement;
import info.myrabatt.logic.SiteParsers.ParserAll;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class AldiParser implements ParserAll {

    private List<String> urlArray = new ArrayList<>() ;
    private String mainUrl = "https://www.aldi-nord.de";
    private List<AngebotElement> offers = new ArrayList<>();


    AldiParser() {
        Document doc;
        try {
            doc = Jsoup.connect(mainUrl).get();
            Elements root = doc.select("div.mod-main-navigation__menu.mod-main-navigation__menu--level-1 > ul > li:nth-child(1) > div > ul > li > a");

            for (Element element: root) {

                System.out.println(mainUrl + "" + element.attr("href"));
                urlArray.add(mainUrl + "" + element.attr("href"));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        getOffers();
    }


    public List<AngebotElement> getOffers() {

        saveAllOffers("https://www.aldi-nord.de/angebote/wochen-aktion-mo-16-10.html");

        for (String url : urlArray) {
        }

        return offers;
    }

    public void saveAllOffers(String url) {
        String offersName = "";
        String offersPrice = "";
        String offersManuf = "";
        String offersProperties = "";
        String offersImgUrl = "";
        try{
            Document doc = Jsoup.connect(url).get();
            Elements root = doc.getElementsByClass("col-xs-6 col-sm-4 col-lg-3");

            if(doc.hasClass("col-xs-12 col-sm-12 col-lg-12")){
                root.addAll(doc.getElementsByClass("col-xs-6 col-sm-4 col-lg-3"));
            }

            for (Element el : root) {
                //the site include image-set, we take first of which
                offersImgUrl = el.getElementsByClass("img-responsive img-contain cq-dd-image")
                        .first().absUrl("srcset").toString().split(" ")[0];
                offersName = el.getElementsByClass("mod-article-tile__action").first().text();

                offersPrice = el.getElementsByClass("price__main").first().text().replace("*","");
                offersManuf = el.getElementsByClass("mod-article-tile__brand").first().text();
                offersProperties = el.getElementsByClass("mod-article-tile__info")
                        .first().getElementsByTag("p").text();

                System.out.println(offersName + "-> " + offersProperties);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
