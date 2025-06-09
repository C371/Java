public class Doctor {
    int id;
    String name;
    String specialty;
    String loginTime;
    String logoutTime;

    public Doctor(int id, String name, String specialty, String loginTime, String logoutTime) {
        this.id = id;
        this.name = name;
        this.specialty = specialty;
        this.loginTime = loginTime;
        this.logoutTime = logoutTime;
    }
}
