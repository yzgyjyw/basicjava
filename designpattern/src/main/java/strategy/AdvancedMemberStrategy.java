package strategy;

public class AdvancedMemberStrategy implements IStrategy{

    @Override
    public double calprice(double price) {
        System.out.println("高级会员打8折");
        return price * 0.8;
    }

}
