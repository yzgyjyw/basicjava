package factory;

public class MIPhone implements Phone {
    @Override
    public String brand() {
        return PhoneBrand.MI.getName();
    }
}