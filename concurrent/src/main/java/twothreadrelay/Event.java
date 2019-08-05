package twothreadrelay;

public class Event {

    private long value;

    public Event(long value) {
        this.value = value;
    }

    public Event() {
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
