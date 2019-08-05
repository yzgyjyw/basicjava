package observer.jdk;

public class JDKObservMain {
    public static void main(String[] args) {
        ObserverC oc = new ObserverC();

        Magazine magazine = new Magazine();
        magazine.addObserver(oc);
        int price = 123;
        magazine.setState(price);

    }
}
