package info.myrabatt.data.entities;


public class AngebotElement {
    private String offersName;
    private String offersPrice;
    private String offersLink;
    private String offersImageLink;
    private String offersDialer;
    private String offersManufacturer;
    private String offersDate;
    private String offersProperties;

    public AngebotElement() {
    }

    public AngebotElement(String offersName, String offersPrice, String offersLink) {

        this.offersName = offersName;
        this.offersPrice = offersPrice;

        this.offersLink = offersLink;
    }

    public AngebotElement(String offersName, String offersPrice, String offersLink, String offersImageLink) {

        this.offersName = offersName;
        this.offersPrice = offersPrice;
        this.offersLink = offersLink;
        this.offersImageLink = offersImageLink;
    }

    public AngebotElement(String offersName, String offersPrice, String offersLink,
                          String offersImageLink, String offersDate, String offersDialer,
                          String offersManufacturer) {
        this.offersName = offersName;
        this.offersPrice = offersPrice;
        this.offersLink = offersLink;
        this.offersImageLink = offersImageLink;
        this.offersDate = offersDate;
        this.offersDialer = offersDialer;
        this.offersManufacturer = offersManufacturer;

    }

    public AngebotElement(String offersName, String offersPrice, String offersLink, String offersImageLink,
                          String offersDialer, String offersManufacturer, String offersDate, String offersProperties) {
        this.offersName = offersName;
        this.offersPrice = offersPrice;
        this.offersLink = offersLink;
        this.offersImageLink = offersImageLink;
        this.offersDialer = offersDialer;
        this.offersManufacturer = offersManufacturer;
        this.offersDate = offersDate;
        this.offersProperties = offersProperties;
    }

    public String getOffersDate() {
        return offersDate;
    }

    public void setOffersDate(String offersDate) {
        this.offersDate = offersDate;
    }

    public String getOffersDialer() {
        return offersDialer;
    }

    public void setOffersDialer(String offersDialer) {
        this.offersDialer = offersDialer;
    }

    public String getOffersManufacturer() {
        return offersManufacturer;
    }

    public void setOffersManufacturer(String offersManufacturer) {
        this.offersManufacturer = offersManufacturer;
    }

    public String getOffersPrice() {
        return offersPrice;
    }

    public void setOffersPrice(String offersPrice) {
        this.offersPrice = offersPrice;
    }

    public String getOffersName() {
        return offersName;
    }

    public void setOffersName(String offersName) {
        this.offersName = offersName;
    }

    public String getOffersLink() {
        return offersLink;
    }

    public void setOffersLink(String offersLink) {
        this.offersLink = offersLink;
    }

    public String getOffersImageLink() {
        return offersImageLink;
    }

    public void setOffersImageLink(String offersImageLink) {
        this.offersImageLink = offersImageLink;
    }

    public String getOffersProperties() {
        return offersProperties;
    }

    public void setOffersProperties(String offersProperties) {
        this.offersProperties = offersProperties;
    }

    @Override
    public String toString() {
        return "AngebotElement{" +
                "offersName='" + offersName + '\'' +
                ", offersPrice='" + offersPrice + '\'' +
                ", offersLink='" + offersLink + '\'' +
                ", offersImageLink='" + offersImageLink + '\'' +
                ", offersDialer='" + offersDialer + '\'' +
                ", offersManufacturer='" + offersManufacturer + '\'' +
                ", offersDate='" + offersDate + '\'' +
                ", offersProperties='" + offersProperties + '\'' +
                '}';
    }
}
