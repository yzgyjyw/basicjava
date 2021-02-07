package data;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Persons {
    private int id;
    private String lastName;
    private String firstName;
    private String address;
    private String city;

    public Persons(int id, String lastName, String firstName, String address, String city) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.address = address;
        this.city = city;
    }
}
