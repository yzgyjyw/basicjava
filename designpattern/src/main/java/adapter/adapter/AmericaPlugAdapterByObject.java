package adapter.adapter;


import adapter.IAmericaPlug;
import adapter.IChinesePlug;

/**
 * 在中国使用美版iphone需要使用一个转接头
 * 使用对象组合的方式实现适配器 优点：
 *                                  1、可以为任意实现了AmecricaPlug接口的对象（子类）实现适配为任意实现了ChinesePlug的类或对象
 */
public class AmericaPlugAdapterByObject implements IChinesePlug {

    private IAmericaPlug americaPlug;

    public AmericaPlugAdapterByObject(IAmericaPlug americaPlug) {
        this.americaPlug = americaPlug;
    }

    @Override
    public void chargingInChina() {
        System.out.println("使用对象组合实现的适配器转接头将AmericaPlug变为ChinesePlug");
        americaPlug.chargingInAmerica();
    }
}
