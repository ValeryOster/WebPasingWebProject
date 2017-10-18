import info.myrabatt.data.entities.AngebotElement;
import info.myrabatt.logic.SiteParsers.*;
import info.myrabatt.logic.SiteParsers.impl.*;

import java.util.ArrayList;
import java.util.List;


public class MainApp {

    public static void main(String[] args) {
        new MainApp();
    }

    private List<AngebotElement> elements = new ArrayList<>();

    public MainApp() {
        getAllOffers(new AldiParser());
        getAllOffers(new LidlParser());
        getAllOffers(new NettoParser());
        getAllOffers(new PennyParser());
        getAllOffers(new ReweParser());

        int i = 1;
        String dialer = "";

        for (AngebotElement element : elements) {
            if (dialer.equals(element.getOffersDialer())){
                dialer = element.getOffersDialer();
                System.out.println("***************** - " + dialer + " - ******************");
            }
            System.out.println( i++ + " " + element.toString() );

        }
    }
    private void getAllOffers(ParserAll parser){
        System.out.println(parser.getClass().getName());
        elements = parser.getOffers();
    }
}
