package customer;

import java.util.Objects;

public class ContactInfo {

    private String email;
    private String number;

    public ContactInfo(String email, String number) {
        this.email = email;
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }



    @Override
    public String toString() {
        return "ContactInfo{" +
                "email='" + email + '\'' +
                ", number='" + number + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ContactInfo that = (ContactInfo) o;
        return Objects.equals(email, that.email) && Objects.equals(number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, number);
    }
}
