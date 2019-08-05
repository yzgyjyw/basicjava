package strategy;

public class Context {

    IStrategy iStrategy;

    public Context(IStrategy iStrategy) {
        this.iStrategy = iStrategy;
    }

    public double getPriceAfterDisCount(double price){
        return iStrategy.calprice(price);
    }
}
