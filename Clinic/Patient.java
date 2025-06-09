public class Patient {
    int id;
    String name;
    int age;
    String address;
    String phone;

    public Patient(int id, String name, int age, String address, String phone){
        this.id = id;
        this.name = name;
        this.age = age;
        this.address = address;
        this.phone = phone;
    }

    public boolean equals(Patient compare){
        return  this.id == compare.id &&
                this.name.equals(compare.name) &&
                this.age == compare.age && 
                this.address.equals(compare.address) &&
                this.phone.equals(compare.phone);
    }

}
