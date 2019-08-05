package factory.abstractfactory;


import factory.HuaweiPhone;
import factory.Phone;

public class HuaweiFactory implements IFactory{
    private HuaweiFactory(){}

    private static class HuaweiFactoryHolder{
        static HuaweiFactory huaweiFactory = new HuaweiFactory();
    }

    public static IFactory getInstance(){
        return HuaweiFactoryHolder.huaweiFactory;
    }

    @Override
    public Phone getPhone() {
        return new HuaweiPhone();
    }

    @Override
    public IPlug getPlug() {
        return new HuaweiPlug();
    }
}
