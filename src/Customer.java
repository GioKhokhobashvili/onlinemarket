import java.time.LocalDate;
public class Customer {
    private String name;
    private String surname;
    private Address address;
    private ContactInfo contactInfo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public Customer(String name, String surname, Address address, ContactInfo contactInfo) {
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.contactInfo = contactInfo;
    }
}
