package factory.factorymode;


import factory.HuaweiPhone;
import factory.Phone;

public class HuaweiPhoneFactory implements IPhoneFactory {

    private HuaweiPhoneFactory(){}

    private static class HuaweiPhoneFactoryHolder{
        static HuaweiPhoneFactory huaweiPhoneFactory = new HuaweiPhoneFactory();
    }

    public static IPhoneFactory getInstance(){
        return HuaweiPhoneFactoryHolder.huaweiPhoneFactory;
    }

    @Override
    public Phone getPhone() {
        return new HuaweiPhone();
    }
}
