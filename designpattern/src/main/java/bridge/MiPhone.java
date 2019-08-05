package bridge;

public class MiPhone extends Phone {

    public MiPhone(Battery battery) {
        super(battery);
    }

    @Override
    public void installBattery() {
        battery.installBattery();
    }
}
