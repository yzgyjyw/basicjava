package factory.abstractfactory;


import factory.Phone;

public interface IFactory {
    Phone getPhone();
    IPlug getPlug();
}
