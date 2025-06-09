// import java.io.IOException;
// import java.io.BufferedWriter;
// import java.io.FileWriter;

public class PatientRecordManagement {
    static class Node{
        Patient patient;
        Node next;

        Node(Patient patient){
            this.patient = patient;
            this.next = null;
        }
    }

    private Node head;

    public Node getHead() {
        return head;
    }

    public void addPatient(Patient patient){
        Node newNode = new Node(patient);
        if(head == null){
            head = newNode;
            return;
        }
        Node current = head;
        while(current.next != null){
            current = current.next;
        }
        current.next = newNode;
    }

    public void removePatientById(int id){
        Node current = head;
        Node previous = null;
        while(current != null && current.patient.id != id){
            previous = current;
            current = current.next;
        }
        if(current == null){
            System.out.println("Patient not found.");
            return;
        }
        if(previous == null){
            head = current.next;
        }
        else{
            previous.next = current.next;
        }
    }

    public Node findPatientById(int id){
        Node current = head;
        while(current != null){
            if(current.patient.id == id){
                return current;
            }
            current = current.next;
        }
        return null;
    }

    public Node findPatientByName(String name){
        Node current = head;
        while(current != null){
            if(current.patient.name.equals(name)){
                return current;
            }
            current = current.next;
        }
        return null;
    }

    public void displayAllPatients(){
        if(head == null){
            System.out.println("No patients found.");
            return;
        }
        Node current = head;
        while(current != null){
            main.printTab("ID");
            System.out.println(current.patient.id);
            main.printTab("Full Name");
            System.out.println(current.patient.name);
            main.printTab("Age");
            System.out.println(current.patient.age);
            main.printTab("Address");
            System.out.println(current.patient.address);
            main.printTab("Phone Number");
            System.out.println(current.patient.phone);
            main.singleLine();
            current = current.next;
        }
    }
}
