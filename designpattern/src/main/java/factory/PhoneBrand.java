package factory;

public enum PhoneBrand {

    MI("mi"),
    HUAWEI("huawei"),
    VIVO("vivo");

    private String name;

    public String getName() {
        return name;
    }

    PhoneBrand(String name) {
        this.name = name;
    }
}