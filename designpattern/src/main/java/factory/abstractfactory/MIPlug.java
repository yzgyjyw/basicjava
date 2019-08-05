package factory.abstractfactory;


import factory.PhoneBrand;

public class MIPlug implements IPlug {

    @Override
    public String name() {
        return PhoneBrand.MI.getName() + "-PLUG";
    }
}
