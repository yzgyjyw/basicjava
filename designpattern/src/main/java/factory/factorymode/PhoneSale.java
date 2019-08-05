package factory.factorymode;


import factory.Phone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 使用工厂模式创建对象
 *
 * 工厂模式角色：
 *      工厂接口
 *      工厂具体实现类
 *      产品接口
 *      产品具体实现
 *
 * 与简单工厂相比：
 *      遵循开闭原则
 *
 */
public class PhoneSale {
    private static final Logger LOGGER = LoggerFactory.getLogger(PhoneSale.class);

    public static void main(String[] args) {
        Phone miPhone = MIPhoneFactory.getInstance().getPhone();
        LOGGER.info(miPhone.brand());
        Phone huaweiPhone = HuaweiPhoneFactory.getInstance().getPhone();
        LOGGER.info(huaweiPhone.brand());
    }
}
