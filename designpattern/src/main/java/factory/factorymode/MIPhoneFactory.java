package factory.factorymode;


import factory.MIPhone;
import factory.Phone;

public class MIPhoneFactory implements IPhoneFactory {

    private MIPhoneFactory(){}

    private static class MIPhoneFactoryHolder{
        static MIPhoneFactory miPhoneFactory = new MIPhoneFactory();
    }

    public static IPhoneFactory getInstance(){
        return MIPhoneFactoryHolder.miPhoneFactory;
    }

    @Override
    public Phone getPhone() {
        return new MIPhone();
    }
}
