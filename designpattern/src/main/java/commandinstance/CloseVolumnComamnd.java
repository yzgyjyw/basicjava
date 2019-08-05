package commandinstance;

public class CloseVolumnComamnd implements AudioCommand {

    AudioPlayer audioPlayer;

    public CloseVolumnComamnd(AudioPlayer audioPlayer) {
        this.audioPlayer = audioPlayer;
    }

    @Override
    public void execute() {
        audioPlayer.closeVolumn();
    }
}
