package bridge;

//抽象
public abstract class Phone {

    Battery battery;

    public Phone(Battery battery) {
        this.battery = battery;
    }

    public Battery getBattery() {
        return battery;
    }

    public void setBattery(Battery battery) {
        this.battery = battery;
    }

    //此为抽象的行为
    public abstract void  installBattery();
}
