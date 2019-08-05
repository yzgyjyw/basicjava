package bridge;

/**
 * 桥接模式：将抽象与行为分离开来，保持抽象与行为的独立性
 */
public class Client {
    public static void main(String[] args) {
        BatteryA batteryA = new BatteryA();
        BatteryB batteryB = new BatteryB();

        MiPhone miPhone = new MiPhone(batteryA);
        RedMiPhone redMiPhone = new RedMiPhone(batteryB);

        miPhone.installBattery();
        redMiPhone.installBattery();
    }
}
