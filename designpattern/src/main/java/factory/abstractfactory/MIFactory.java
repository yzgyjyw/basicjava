package factory.abstractfactory;


import factory.MIPhone;
import factory.Phone;

public class MIFactory implements IFactory {

    private MIFactory(){}

    private static class MiFactoryHolder{
        static MIFactory miFactory = new MIFactory();
    }

    public static IFactory getInstance(){
        return MiFactoryHolder.miFactory;
    }


    @Override
    public Phone getPhone() {
        return new MIPhone();
    }

    @Override
    public IPlug getPlug() {
        return new MIPlug();
    }
}
