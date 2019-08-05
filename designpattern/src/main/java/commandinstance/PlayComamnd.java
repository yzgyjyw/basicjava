package commandinstance;

import command.Receiver;

public class PlayComamnd implements AudioCommand {

    AudioPlayer audioPlayer;

    public PlayComamnd(AudioPlayer audioPlayer) {
        this.audioPlayer = audioPlayer;
    }

    @Override
    public void execute() {
        audioPlayer.play();
    }
}
