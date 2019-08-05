package strategy;

public class Client {
    //策略模式仅仅封装算法,而不会决定在何时使用何种算法,选择算法由客户端决定
    public static void main(String[] args) {
        IStrategy strategy  = new AdvancedMemberStrategy();

        Context advancedStrategeyContext = new Context(strategy);

        double priceAfterDisCount = advancedStrategeyContext.getPriceAfterDisCount(10);

        System.out.println(priceAfterDisCount);
    }
}
