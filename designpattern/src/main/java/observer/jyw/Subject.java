package observer.jyw;

public interface Subject {
    void registerObservers(Observer observer);
    void notifyObservers();
    void unregisterObserver(Observer observer);
}
