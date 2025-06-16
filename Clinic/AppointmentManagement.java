import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;

class Appointment {
    int appointmentID;
    int patientID;
    int doctorID;
    String time;
    String status;
    String diagnosis;
    String treatment;

    public Appointment(int appointmentID, int patientID, int doctorID, String time, String status, String diagnosis, String treatment) {
        this.appointmentID = appointmentID;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.time = time;
        this.status = status;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
    }
}

public class AppointmentManagement {
    Appointment[] appointment;
    int front;
    int rear;
    int size;
    int capacity;

    public AppointmentManagement(int capacity){
        this.capacity = capacity;
        this.appointment = new Appointment[capacity];
        this.front = 0;
        this.rear = -1;
        this.size = 0;
    }

    public void appendCSV(){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("AppointmentRecords.csv", true))) {
            for (int i = 0; i < size; i++) {
                Appointment app = appointment[(front + i) % capacity];
                writer.write(app.appointmentID + "|" + app.patientID + "|" + app.doctorID + "|" + app.time + "|" + app.status + "|" + app.diagnosis + "|" + app.treatment);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public void rewriteCSV(){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("AppointmentRecords.csv"))) {
            writer.write("AppointmentID|PatientID|DoctorID|Time|Status|Diagnosis|Treatment");
            writer.newLine();
            for (int i = 0; i < size; i++) {
                Appointment app = appointment[(front + i) % capacity];
                writer.write(app.appointmentID + "|" + app.patientID + "|" + app.doctorID + "|" + app.time + "|" + app.status + "|" + app.diagnosis + "|" + app.treatment);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public void scheduleAppointment(Appointment element){
        if (size == capacity){
            System.out.println("Appointment queue is full");
            return;
        }
        rear = (rear + 1) % capacity;
        appointment[rear] = element;
        size++;
        appendCSV();
        // System.out.println("Appointment scheduled: " + element.appointmentID);
    }

    public int processNextAppointment(){
        if (size == 0){
            System.out.println("No appointments to process");
            return -1;
        }
        int appointmentID = appointment[front].appointmentID;
        front = (front + 1) % capacity;
        size--;
        return appointmentID;
    }

    public int peek(){
        if (size == 0){
            System.out.println("No appointments to peek");
            return 1;
        }
        return appointment[front].appointmentID;
    }

    public void viewUpcomingAppointments(){
        
    }
}
