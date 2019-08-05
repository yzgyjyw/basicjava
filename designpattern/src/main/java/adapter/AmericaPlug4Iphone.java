package adapter;

public class AmericaPlug4Iphone implements IAmericaPlug {
    @Override
    public void chargingInAmerica() {
        System.out.println("use American plug to cahrge for iphone");
    }
}
