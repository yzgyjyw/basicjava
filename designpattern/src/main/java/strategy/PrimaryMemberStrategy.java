package strategy;

public class PrimaryMemberStrategy implements IStrategy{

    @Override
    public double calprice(double price) {
        System.out.println("");
        return price;
    }
}
