package factory.abstractfactory;


import factory.Phone;

/**
 * 抽象工厂：
 *      生产一整套产品的，例如手机厂商不止需要生产手机还需要声场手机充电器，并且两者是相互依赖的
 *    角色：
 *      产品接口1,产品接口2,产品接口3,产品接口.....
 *      产品实现1,产品实现2,产品实现3,产品实现.....,
 *      抽象工厂接口
 *      具体工厂
 *
 * 相比于工厂模式区别：
 *      工厂模式只生产一个产品，而抽象工厂至少生产两个以上的产品，并且他们之间是相互关联的
 *
 */
public class Sale {
    public static void main(String[] args) {
        IFactory miFactory = MIFactory.getInstance();
        Phone miPhone = miFactory.getPhone();
        IPlug miPlug = miFactory.getPlug();
        System.out.println(miPhone.brand()+"--"+miPlug.name());
    }
}
