package bridge;

public class BatteryB implements Battery {
    @Override
    public void installBattery() {
        System.out.println("安装电池B");
    }
}
