public class Appointment {
    int appointmentID;
    int patientID;
    int doctorID;
    int time;

    public Appointment(int appointmentID, int patientID, int doctorID, int time) {
        this.appointmentID = appointmentID;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.time = time;
    }
}
