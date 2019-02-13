package Entity;

public class Individual {

    private String Id;
    private String Name;
    private String Dob;
    private String Email;
    private String Phone;
    private String Address;

    public void addId(String id) {
        this.Id = id;
    }

    public void addName(String name) {
        this.Name = name;
    }

    public void addDob(String dob) {
        this.Dob = dob;
    }

    public void addEmail(String email) {
        this.Email = email;
    }

    public void addPhone(String phone) {
        this.Phone = phone;
    }

    public void addAddress(String address) {
        this.Address = address;
    }

    public String getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getDob() {
        return Dob;
    }

    public String getEmail() {
        return Email;
    }

    public String getPhone() {
        return Phone;
    }

    public String getAddress() {
        return Address;
    }

}
