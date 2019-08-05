public class Main {
    public static void main(String[] args) {
        int count = 1;
        for(int y1=1;y1<=6;y1++){
            for(int y2=0;y2<=7;y2++){
                for(int y3=4;y3<=8;y3++){
                    for(int y4=2;y4<=6;y4++){
                        if(y1+y2+y3+y4==20){
                            System.out.println(y1+","+y2+","+y3+","+y4);
                            System.out.println(count++);
                        }
                    }
                }
            }
        }
    }
}
