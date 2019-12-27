package commandinstance;

public class StopComamnd implements AudioCommand {

    AudioPlayer audioPlayer;

    public StopComamnd(AudioPlayer audioPlayer) {
        this.audioPlayer = audioPlayer;
    }

    @Override
    public void execute() {
        audioPlayer.stop();
    }
}
