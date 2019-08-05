package factory;

public class HuaweiPhone implements Phone {

    @Override
    public String brand() {
        return PhoneBrand.HUAWEI.getName();
    }
}