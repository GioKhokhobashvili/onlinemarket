package customer;

public class Person {

    protected String name;
    protected String surname;
    protected ContactInfo contactInfo;

    public Person(String name, String surname, ContactInfo contactInfo) {
        this.name = name;
        this.surname = surname;
        this.contactInfo = contactInfo;
    }

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

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }


}
