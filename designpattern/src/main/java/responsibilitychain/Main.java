package responsibilitychain;

public class Main {

    private static int count = 0;

    private static int highPrioritySize = 28;
    private static int highPriorityBit = (1 << 28) - 1;

//    private static int importanceBit = 0b00001111000000000000000000000000;

    private static int importanceBit = 0x0f000000;


    public static void main(String[] args) {

        System.out.println(10011&2);
//        AbstractHandler handler = new ConcreteHandlerA();
//        handler.setNext(new ConcreteHandlerB());

//        handler.action(5);


        // 前4位为重要消息,接着4位为
        int hightPriorityMessage = 1 << 28;

//        Integer i = Integer.MAX_VALUE;

//        System.out.println(Integer.toBinaryString(i));



        for(int i=0;i<16;i++){
            incr();
            System.out.println(getHightPriority());
        }

        for(int i=0;i<15;i++){
            incrI();
            System.out.println(getImportancePriority());
        }

    }

    private static void incr() {
        int a = getHightPriority();
        a++;
        count = (count & ((1 << 28)-1) | (a << 28));
        System.out.println(Integer.toBinaryString(count));
    }

    private static int getHightPriority() {
        return (count & (-1 << 28)) >>> 28;
    }

    private static void incrI() {
        int a = getImportancePriority();
        a++;
        count = (count & (~importanceBit)) | (a << 24);
        System.out.println(Integer.toBinaryString(count));
    }

    private static int getImportancePriority() {
        return (count & importanceBit) >>> 24;
    }
}
