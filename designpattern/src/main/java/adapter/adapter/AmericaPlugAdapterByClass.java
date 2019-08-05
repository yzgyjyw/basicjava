package adapter.adapter;


import adapter.AmericaPlug4Iphone;
import adapter.IChinesePlug;

public class AmericaPlugAdapterByClass extends AmericaPlug4Iphone implements IChinesePlug {
    @Override
    public void chargingInChina() {
        System.out.println("使用类继承实现的适配器将美国充电器适配为中国充电器");
        super.chargingInAmerica();
    }
}
