package factory.abstractfactory;


import factory.PhoneBrand;

public class HuaweiPlug implements IPlug {
    @Override
    public String name() {
        return PhoneBrand.HUAWEI.getName() + "-PLUG";
    }
}
