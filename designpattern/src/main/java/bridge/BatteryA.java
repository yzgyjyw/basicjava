package bridge;

public class BatteryA implements Battery{
    @Override
    public void installBattery() {
        System.out.println("安装电池A");
    }
}
