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

public class PennyParser implements ParserAll {
    private List <String> urlArray ;

    public PennyParser() {
        this.urlArray = new ArrayList<>();
        urlArray.add("http://www.penny.de/angebote/aktuell//liste/Ab-Montag/");
        urlArray.add("http://www.penny.de/angebote/aktuell//liste/Ab-Donnerstag/");
        urlArray.add("http://www.penny.de/angebote/aktuell//liste/Ab-Freitag/");
        urlArray.add("http://www.penny.de/angebote/aktuell//liste/Non-Food/");
        urlArray.add("http://www.penny.de/angebote/vorschau/");
    }

    @Override
    public List<AngebotElement> getOffers() {

        for(String url: urlArray){
                getAllOffersOfArray(url);
        }

        return offers;
    }

    //get All Offers of the Array
    private void getAllOffersOfArray(String link) {
        Document doc;
        try {
            doc = Jsoup.connect(link).get();


            if (doc.select("div.penny-themenwelt-product.pny_angebot").size() != 0) {
                writeElementsInArray(link,doc.select("div.penny-themenwelt-product.pny_angebot"));

            }else if (doc.select("div.penny-themenwelt-product.pny_nonfood").size() != 0){
                writeElementsInArray(link,doc.select("div.penny-themenwelt-product.pny_nonfood"));
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeElementsInArray(String url, Elements elemens) {
        String offersName = "";
        String offersPrice = "";
        String offersManuf = "";
        String offersProp = "";
        String[] hersteller ;

        for (Element e : elemens){
            try {
                System.out.println("------------");
                //Test if the class exists
                if(!e.hasClass("penny-themenwelt-product-headline"))
                {
                    //Get text in array and split it bevor
                    hersteller = e.getElementsByClass("penny-themenwelt-product-headline").first().ownText().split(" ",2);
                    if (hersteller.length == 1) {
                        if(hersteller[0].contains("*")) {
                            hersteller[0] = hersteller[0].replace("*", "");
                        }
                        if(hersteller[0].contains(",")) {
                            hersteller[0] = hersteller[0].replace("*", "");
                        }
                        offersName = hersteller[0];
                        offersManuf = hersteller[0];
                    }
                    else if (possibleWords.contains(hersteller[0])) {
                        hersteller = e.child(1).child(0).ownText().split(" ", 3);
                        if (hersteller[2].contains("*")) {
                            offersName = hersteller[2].replace("*", "");
                        } else {
                            offersName = hersteller[2];
                        }
                        offersManuf = hersteller[0] + " " + hersteller[1];
                    } else {
                        if (hersteller[1].contains("*")) {
                            offersName = hersteller[1].replace("*", "");
                        } else {
                            offersName = hersteller[1];
                        }
                        offersManuf = hersteller[0];
                    }
                }
                System.out.println(offersName + " -- " + offersManuf);
                offersPrice = e.child(0).getElementsByClass("textPrice").text();
                offersProp = e.child(1).child(0).child(0).ownText();
                LocalDate date = LocalDate.now();

                offers.add(new AngebotElement(
                        offersName,
                        offersPrice,
                        url,
                        e.child(0).getElementsByTag("img")
                            .first().absUrl("data-src-retina"),
                        "Penny Markt",
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
