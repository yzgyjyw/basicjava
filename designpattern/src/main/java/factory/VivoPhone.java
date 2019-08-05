package factory;

public class VivoPhone implements Phone {
    @Override
    public String brand() {
        return PhoneBrand.VIVO.getName();
    }
}