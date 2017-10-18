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

public class ReweParser implements ParserAll {
    private List <String> urlArray = new ArrayList<>() ;
    private String mainUrl = "https://www.rewe.de/angebote/";

    @Override
    public List<AngebotElement> getOffers() {
        Document doc;
        String offersName = "";
        String offersPrice = "";
        String offersManuf = "";
        String offersProp = "";
        String offersImgUrl = "";
        LocalDate date = LocalDate.now();

        try {
            doc = Jsoup.connect(mainUrl).get();
            Elements rootEl = doc.getElementsByClass("controller product");
            int i =1;
            for (Element el : rootEl){

                //distinguish Name from Manufacture
                String[] nameAr = el.getElementsByClass("dotdot").first().child(0).text().split(" ");
                if (nameAr.length == 1) {
                    offersManuf = nameAr[0];
                    offersName = nameAr[0];
                } else if (possibleWords.contains(nameAr[0])) {
                    offersManuf = nameAr[0] + " " + nameAr[1];
                    for (String str : nameAr) {
                        offersName += str;
                    }
                }else {
                    offersManuf = nameAr[0];
                    offersName = nameAr[1];
                }
                offersPrice = el.getElementsByClass("price").first().text();
                offersProp = el.getElementsByClass("subtitle").first().text();
                offersImgUrl = el.getElementsByClass("img").first()
                        .select("img").first().absUrl("data-src");

                offers.add(new AngebotElement(
                        offersName,
                        offersPrice,
                        mainUrl,
                        offersImgUrl,
                        "Rewe",
                        offersManuf,
                        date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                        offersProp
                ));

            }

        }catch (IOException e) {
            e.printStackTrace();
        }
        return offers;
    }
}
