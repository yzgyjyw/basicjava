package adapter;


import adapter.adapter.AmericaPlugAdapterByClass;
import adapter.adapter.AmericaPlugAdapterByObject;

/**
 * 现在在南京，拥有一部美版Iphone及其充电器，需要使用转接头进行充电
 */
public class NJArea {

    public static void main(String[] args) {
        ObjectAdapter();
        ClassAdapter();
    }

    private static void  ObjectAdapter(){
        IAmericaPlug americaPlug = new AmericaPlug4Iphone();
        //使用适配器将AmericaPlug转换为ChinesePlug
        charge4Iphone(new AmericaPlugAdapterByObject(americaPlug));
    }

    private static void ClassAdapter(){
        IAmericaPlug americaPlug = new AmericaPlug4Iphone();
        charge4Iphone(new AmericaPlugAdapterByClass());
    }

    /**
     * 在中国为iphone充电
     * @param chinesePlug 充电器
     */
    public static void charge4Iphone(IChinesePlug chinesePlug){
        chinesePlug.chargingInChina();
    }

}
