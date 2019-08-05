package factory.simple;


import factory.*;

/**
 * 简单工厂：
 * 角色：
 * 产品接口
 * 具体产品类
 * 简单工厂类
 * 优点：封装获取产品的判断逻辑
 * 缺点：当需要增加新的品牌的手机时需要修改工厂类，有悖于开闭原则
 * <p>
 * 这边的实现使用的是if-else语句进行判断
 * 与spring结合的时候，可以直接将具体的产品放在spring容器中，直接从spring中获取，context.getBean()
 * 使用接收参数可以是一个Class对象，采用反射创建
 */
public class SimplePhoneFactory {
    public static Phone getPhone(PhoneBrand brand) throws Exception {
        if (PhoneBrand.MI.equals(brand)) {
            return new MIPhone();
        } else if (PhoneBrand.HUAWEI.equals(brand)) {
            return new HuaweiPhone();
        } else if (PhoneBrand.VIVO.equals(brand)) {
            return new VivoPhone();
        }
        throw new Exception("UNKNOWN BRAND");
    }
}