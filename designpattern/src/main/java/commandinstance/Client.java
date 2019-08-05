package commandinstance;

//使用AudioPlayer的人
public class Client {

    public static void main(String[] args) {
        AudioPlayer audioPlayer = new AudioPlayer();

        AudioCommand playCommand = new PlayComamnd(audioPlayer);
        StopComamnd stopCommand = new StopComamnd(audioPlayer);
        CloseVolumnComamnd closeVolumnComamnd = new CloseVolumnComamnd(audioPlayer);


        KeyBoard keyBoard = new KeyBoard();
        keyBoard.setPlayCommand(playCommand);
        keyBoard.setStopCommand(stopCommand);
        keyBoard.setCloseVolumnCommand(closeVolumnComamnd);

        keyBoard.play();
        keyBoard.stop();
        keyBoard.closeVolumn();
    }

}
