public class AppointmentManagement {
    private Appointment[] appointment;
    private int front;
    private int rear;
    private int size;
    private int capacity;

    public AppointmentManagement(int capacity){
        this.capacity = capacity;
        this.appointment = new Appointment[capacity];
        this.front = 0;
        this.rear = -1;
        this.size = 0;
    }

    public void scheduleAppointment(Appointment element){
        if (size == capacity){
            System.out.println("Queue is full");
            return;
        }
        rear = (rear + 1) % capacity;
        appointment[rear] = element;
        size++;
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
            return -1;
        }
        return appointment[front].appointmentID;
    }

    public void viewUpcomingAppointments(){
        if (size == 0){
            System.out.println("No upcoming appointments");
            return;
        }
        System.out.println("Upcoming Appointments:");
        for (int i = 0; i < size; i++){
            int index = (front + i) % capacity;
            System.out.println("Appointment ID: " + appointment[index].appointmentID + 
                               ", Patient ID: " + appointment[index].patientID + 
                               ", Doctor ID: " + appointment[index].doctorID + 
                               ", Time: " + appointment[index].time);
        }
    }
}
