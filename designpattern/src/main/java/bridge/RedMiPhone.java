package bridge;

public class RedMiPhone extends Phone {

    public RedMiPhone(Battery battery) {
        super(battery);
    }

    @Override
    public void installBattery() {
        battery.installBattery();
    }
}
