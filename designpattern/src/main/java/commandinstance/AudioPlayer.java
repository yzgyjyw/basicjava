package commandinstance;

//Receiver
public class AudioPlayer {
    public void play(){
        System.out.println("开始播放");
    }

    public void stop(){
        System.out.println("停止播放");
    }

    public void closeVolumn(){
        System.out.println("静音");
    }
}
