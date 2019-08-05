package commandinstance;

//按钮,充当invoker
public class KeyBoard {

    AudioCommand playCommand;
    AudioCommand stopCommand;
    AudioCommand closeVolumnCommand;

    public void setPlayCommand(AudioCommand playCommand) {
        this.playCommand = playCommand;
    }

    public void setStopCommand(AudioCommand stopCommand) {
        this.stopCommand = stopCommand;
    }

    public void setCloseVolumnCommand(AudioCommand closeVolumnCommand) {
        this.closeVolumnCommand = closeVolumnCommand;
    }


    public void play(){
        playCommand.execute();
    }

    public void stop(){
        stopCommand.execute();
    }

    public void closeVolumn(){
        closeVolumnCommand.execute();
    }
}
