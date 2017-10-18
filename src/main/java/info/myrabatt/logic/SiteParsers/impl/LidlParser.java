package info.myrabatt.logic.SiteParsers.impl;

import info.myrabatt.data.entities.AngebotElement;
import info.myrabatt.logic.SiteParsers.ParserAll;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LidlParser implements ParserAll {

    private String mainUrl = "https://www.lidl.de/de/";

    private Map<String,String> lildArrays = new HashMap<>();
    private String offersLink = mainUrl + "angebote";

    @Override
    public List<AngebotElement> getOffers() {
        getAllOffersSectors(getOfferForNow());
        for (String mEntry : lildArrays.keySet())
        {
            //System.out.println(lildArrays.get(mEntry));
            getAllOffersOfArray(mainUrl+""+lildArrays.get(mEntry));

        }
        return offers;
    }

    //Bekommen den Links für alle Angebotte diese Wochen
    private String getOfferForNow(){
        Document doc;
        String linkOffersNow = "";
        try {
            doc = Jsoup.connect(offersLink).get();
            Elements elements =  doc.select("body > div.shifter-page > div > section.main > div.wrapper.content > div > div > div.space.p-lr > ul");
            if(!elements.isEmpty())
            {
                for (Element e :
                        elements) {
                    //Finden wir element mit Tag "diese Woche"
                    List<Element> list = e.children();
                    for(Element el: list){
                        if(el.text().contains("diese Woche"))
                        {
                            linkOffersNow = mainUrl + el.child(0).tagName("href").attr("href");
                        }

                    }
                }
            }else
                System.out.println("Array Element 'Diese Woche' ist leer!");

        }catch (IOException e) {
            e.printStackTrace();
        }
        return linkOffersNow;
    }
    //Finden specifische Abteilungen für Lidl
    private void getAllOffersSectors(String link) {
        Document doc;
        try {
            doc = Jsoup.connect(link).get();

/*            BufferedWriter htmlWriter =
                    new BufferedWriter(new OutputStreamWriter(new FileOutputStream("meinAngebot.html"), "UTF-8"));
            htmlWriter.write(doc.toString());*/

            Elements elements =  doc.select("body > div.shifter-page > div > section.main > div.wrapper.content > div > div > div.space.p-l > div > div");
            if(!elements.isEmpty())
            {
                Elements es = elements.first().children();

                for (Element e : es)
                {
                    Element elem = e.child(0).tagName("href");
//                    lildArrays.add();
                    lildArrays.put( e.text()
                            , elem.child(0).tagName("href").attr("href")  );
                }
            }else
                System.out.println("Array ist leer!");

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    //get All Offers of the Array
    private void getAllOffersOfArray(String link) {
        Document doc;
        Element elm;
        try {
            doc = Jsoup.connect(link).get();
//            Elements elemens = doc.select("body > div.shifter-page > div > section.main > div.wrapper.content > div > article > div > div.r.no-m ");
            Elements elemens = doc.select(".space.p-r.p-b");

            if (elemens.size() != 0) {
                for (Element e: elemens)
                    if(e.child(0).text().contains("Filiale") && e.child(0).text().contains("*"))
                    {
                        try {

                            offers.add(new AngebotElement(
                                    e.child(0).child(2).child(0).text(),
                                    e.child(0).child(3).text(),
                                    link));
                        } catch (IndexOutOfBoundsException e1) {
                            ///System.out.println(link);A
                            offers.add(new AngebotElement( e.child(0).child(3).child(0).text(),
                                                            e.child(0).child(4).child(0).text() ,
                                                            link ) );

                        }
                    }
            }else {
                System.out.println("the path is false");
                return;
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }


    //get All imposible Array for Offer of this weck
    public Map<String,String > getLildArrays() {
        return lildArrays;
    }
}
