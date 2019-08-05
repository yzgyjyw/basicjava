package adapter;

public class ChinesePlug4Iphone implements IChinesePlug {
    @Override
    public void chargingInChina() {
        System.out.println("使用中国插头为iphone充电");
    }
}
