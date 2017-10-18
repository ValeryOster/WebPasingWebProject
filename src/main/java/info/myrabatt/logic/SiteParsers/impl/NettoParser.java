package info.myrabatt.logic.SiteParsers.impl;

import info.myrabatt.data.entities.AngebotElement;
import info.myrabatt.logic.SiteParsers.ParserAll;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class NettoParser implements ParserAll {
    private List <String> urlArray = new ArrayList<>() ;
    private String mainUrl = "https://www.netto-online.de/index.php/page/set_store/6594/1";

    public NettoParser() {
        getAllArrayUrl();
    }

    private void getAllArrayUrl() {
            Document doc;
            try {
                doc = Jsoup.connect(mainUrl).get();
                Elements root = doc.select("div.sub_navi_slide");

                if (root.select("a[href]").size() != 0) {
                   Elements elements = root.select("a[href]");
                    for (Element element : elements) {
                        urlArray.add( element.attr("href").toString() );
                    }

                }else
                    System.out.println("**** NettoParser **** Problem with Navigation Parsing !!!");

            }catch (IOException e) {
                e.printStackTrace();
            }
    }

    @Override
    public List<AngebotElement> getOffers() {

        for (String url : urlArray) {
            getAllOffersOfArray(url);
        }
        return offers;
    }

    private void getAllOffersOfArray(String url) {

        Document doc;
        try {
            doc = Jsoup.connect(url).get();

            if (doc.select("div.box_article.clean").size() != 0) {
                writeElementsInArray(url,doc.select("div.box_article.clean"));

            }else if (doc.select("div.penny-themenwelt-product.pny_nonfood").size() != 0){
                writeElementsInArray(url,doc.select("div.penny-themenwelt-product.pny_nonfood"));
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeElementsInArray(String url, Elements elemens) {
        int i = 0;
        String offersName = "";
        String offersPrice = "";
        String offersManuf = "";
        String offersProp = "";
        String offersImgUrl = "";
        for (Element e : elemens){
            try {
                //That same in Pennyparser names
                String[] nameArray = e.getElementsByClass("box_article_title").text().split(" ",2);
                if (nameArray.length == 1) {
                    offersManuf = nameArray[0];
                    offersName = nameArray[0];
                } else if (possibleWords.contains(nameArray[0])) {
                    nameArray = e.getElementsByClass("box_article_title").text().split(" ",3);
                    offersManuf = nameArray[0] + " " + nameArray[1];
                    offersName = nameArray[2];
                }else {
                    offersManuf = nameArray[0];
                    offersName = nameArray[1];
                }
                offersPrice = e.getElementsByClass("price-main").text();
                offersProp = e.getElementsByClass("box_article_desc").first().ownText();
                Element as = e.getElementsByClass("box_article_img").first();
                offersImgUrl =  as.select("img").first().absUrl("src");
                LocalDate date = LocalDate.now();

                offers.add(new AngebotElement(
                        offersName,
                        offersPrice,
                        url,
                        offersImgUrl,
                        "Netto",
                        offersManuf,
                        date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                        offersProp
                ));
            } catch (IndexOutOfBoundsException e1) {
                System.out.println("IndexOutOfBoundsException Hier ==> " + url);
                System.out.println("IndexOutOfBoundsException Hier ==> " + e.text());
            } catch (NullPointerException e2) {
                System.out.println("NullPointerExeption Hier ==> " + url);
                System.out.println("NullPointerExeption Hier ==> " + e.text());

            }
        }
    }

}
