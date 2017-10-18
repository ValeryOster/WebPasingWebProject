package info.myrabatt.logic.SiteParsers;

import info.myrabatt.data.entities.AngebotElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public interface ParserAll {
    List<String> possibleWords = Arrays.asList(
        "Die", "DIE","Der","Das","Für", "Dr.", "DR.", "MON", "Uncle");
    List<AngebotElement> offers = new ArrayList<>();
    List<AngebotElement> getOffers();
}
