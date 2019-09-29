package facade;

public class Facade {


    ServiceA serviceA;
    ServiceB serviceB;

    public Facade() {
        serviceA = new ServiceA();
        serviceB = new ServiceB();
    }

    public void method1() {
        serviceA.method1();
    }

    public void method2() {
        serviceB.method2();
    }


    public static void main(String[] args) {
        String OPPO_QUICKAPP = "OPPO_QUICKAPP";
        String VIVO_QUICKAPP = "VIVO_QUICKAPP";

        String OPPO = "OPPO";
        String VIVO = "VIVO";
        String newRegInfo = "brand:VIVO_QUICKAPP~token:CN_63b68c4df69d687c73a0c65212d517b4~package_name:com.calendar.dym";
        if (newRegInfo.contains(VIVO_QUICKAPP)) {
            newRegInfo = newRegInfo.replace(VIVO_QUICKAPP, VIVO);
        } else if (newRegInfo.contains(OPPO_QUICKAPP)) {
            newRegInfo = newRegInfo.replace(OPPO_QUICKAPP, OPPO);
        }


        System.out.println(newRegInfo);
    }
}
