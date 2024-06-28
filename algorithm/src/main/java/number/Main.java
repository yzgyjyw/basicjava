package number;

public class Main{

    static Object object1 = new Object();

    static Object object2 = new Object();

    static Object object3 = new Object();

    public static void main(String[] args){
        new A().start();
        new B().start();
        new C().start();
    }






    static class A extends Thread{

        @Override
        public void run(){
            while(true){
                synchronized(object1){

                    System.out.print("A");
                    object2.notify();
                    try {
                        object1.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }


            }
        }
    }

    static class B extends Thread{
        @Override
        public void run(){
            while(true){
                synchronized(object2){
                    try {
                        object2.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.print("B");
                    object3.notify();

                }

            }
        }
    }

    static class C extends Thread{
        @Override
        public void run(){
            while(true){
                synchronized(object3){
                    try {
                        object3.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.print("C");
                    object1.notify();

                }
            }
        }


    }
}

