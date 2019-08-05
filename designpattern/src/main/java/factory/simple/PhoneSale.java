package factory.simple;


import factory.Phone;
import factory.PhoneBrand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PhoneSale {
    private static final Logger LOGGER = LoggerFactory.getLogger(PhoneSale.class);

    public static void main(String[] args) {
        try {
            Phone miPhone = SimplePhoneFactory.getPhone(PhoneBrand.MI);
            System.out.println(miPhone.brand());
            Phone huaweiPhone = SimplePhoneFactory.getPhone(PhoneBrand.HUAWEI);
            System.out.println(huaweiPhone.brand());
        } catch (Exception e) {
            LOGGER.error("cannot get phone",e);
        }
    }

}