import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
// import java.time.LocalDate;
import java.time.LocalDateTime;
// import java.time.LocalTime;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Scanner;

public class main{
    static Scanner input = new Scanner(System.in);
    static public final String RESET = "\u001B[0m";
    static public final String RED = "\u001B[31m";
    static public final String GREEN = "\u001B[32m";
    static PatientRecordManagement prm = new PatientRecordManagement();
    static DoctorLogin doctorLogin = new DoctorLogin();
    static int role = 0;

    static void readCSV(){
        try(BufferedReader reader = new BufferedReader(new FileReader("PatientRecords.csv"))){
            reader.readLine();
            String line;
            while((line = reader.readLine()) != null){
                String[] p = line.split("\\|");
                prm.addPatient(new Patient(Integer.parseInt(p[0].trim()), p[1].trim(), Integer.parseInt(p[2].trim()), p[3].trim(), p[4].trim()));
            }
            System.out.println("Data loaded");
        }
        catch(Exception e){
            System.out.println("Error.");
        }

        try(BufferedReader reader = new BufferedReader(new FileReader("DoctorRecords.csv"))){
            reader.readLine();
            String line;
            while((line = reader.readLine()) != null){
                String[] data = line.split("\\|");
                if(data.length == 5){
                    int id = Integer.parseInt(data[0]);
                    String name = data[1];
                    String specialty = data[2];
                    String loginTime = data[3];
                    String logoutTime = data[4];
                    doctorLogin.addDoctor(new Doctor(id, name, specialty, loginTime, logoutTime));
                }
            }
        }
        catch(IOException e){
            System.out.println("Error reading file.");
        }
    }

    static void appendCSV(String patient){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("PatientRecords.csv", true))){
            writer.write(patient);
            writer.newLine();
        }
        catch(IOException e){
            System.out.println("Error.");
        }
    }

    static void rewriteCSV(){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("PatientRecords.csv"))){
            writer.write("ID|Name|Age|Address|Phone");
            writer.newLine();
            PatientRecordManagement.Node current = prm.getHead();
            while(current != null){
                writer.write(current.patient.id + "|" + current.patient.name + "|" + current.patient.age + "|" + current.patient.address + "|" + current.patient.phone);
                writer.newLine();
                current = current.next;
            }
        }
        catch(IOException e){
            System.out.println("Error.");
        }
    }

    static void initialize(){
        readCSV();
    }

    static void redirect(){
        try{
            System.out.println("Redirecting to Main Page...");
            doubleLine();
            Thread.sleep(3000);
            mainPage();
        }
        catch(InterruptedException e){}
    }

    static void doubleLine(){
        System.out.println("=======================================================");
    }

    static void singleLine(){
        System.out.println("-------------------------------------------------------");
    }

    static void printTab(String str){
        System.out.print(str);
        for(int i = 0; i < 13 - str.length(); i++){
            System.out.print(" ");
        }
        System.out.print(": ");
    }

    static void printTitle(String title){
        doubleLine();
        for(int i = 0; i < (55 - title.length()) / 2; i++){
            System.out.print(" ");
        }
        System.out.println(title);
        doubleLine();
    }

    static void patientFound(int id){
        PatientRecordManagement.Node patient = prm.findPatientById(id);
        System.out.println("Patient found: ");
        printTab("ID");
        System.out.println(patient.patient.id);
        printTab("Full Name");
        System.out.println(patient.patient.name);
        printTab("Age");
        System.out.println(patient.patient.age);
        printTab("Address");
        System.out.println(patient.patient.address);
        printTab("Phone Number");
        System.out.println(patient.patient.phone);
        singleLine();
    }

    static void addNewPatient(){
        System.out.print("\033[H\033[2J");
        printTitle("ADD NEW PATIENT");
        printTab("Full Name");
        String name = input.nextLine();
        printTab("Age");
        int age = input.nextInt();
        input.nextLine();
        printTab("Address");
        String address = input.nextLine();
        printTab("Phone Number");
        String phone = input.nextLine();
        int lastID = 0;;
        PatientRecordManagement.Node current = prm.getHead();
        while(current != null){
            if(current.patient.id > lastID){
                lastID = current.patient.id;
            }
            current = current.next;
        }
        singleLine();
        System.out.println("[1] Add Patient\n[2] Cancel");
        singleLine();
        System.out.print("Please select an option: ");
        int menu = input.nextInt();
        input.nextLine();
        System.out.print("\033[H\033[2J");
        switch(menu){
            case 1: 
                printTitle("SUCCESS");
                prm.addPatient(new Patient(lastID + 1, name, age, address, phone));
                appendCSV(lastID + 1 + "|" + name + "|" + age + "|" + address + "|" + phone);
                System.out.println(GREEN + "Patient added successfully." + RESET);
                break;
            case 2: 
                printTitle("CANCELLED");
                System.out.println("Patient registration cancelled.");
                break;
            default:
        }
        redirect();
    }

    static void removePatient(){
        System.out.print("\033[H\033[2J");
        printTitle("REMOVE PATIENT");
        System.out.print("Patient ID: ");
        int id = input.nextInt();
        input.nextLine();
        // prm.displayAllPatients();
        if(prm.findPatientById(id) != null){
            patientFound(id);
            System.out.println("[1] Remove Patient\n[2] Cancel");
            singleLine();
            System.out.print("Please select an option: ");
            int menu = input.nextInt();
            input.nextLine();
            System.out.print("\033[H\033[2J");
            switch(menu){
                case 1: 
                    printTitle("SUCCESS");
                    prm.removePatientById(id);
                    rewriteCSV();
                    System.out.println(GREEN + "Patient removed successfully." + RESET);
                    break;
                case 2: 
                    printTitle("CANCELLED");
                    System.out.println("Patient removal cancelled.");
                    break;
                default:
            }
            redirect();
        }
        else{
            System.out.println(RED + "Patient not found." + RESET);
        }
    }

    static void searchPatient(int id){
        if(prm.findPatientById(id) != null){
            patientFound(id);
            System.out.println("[1] Search Again\n[2] Go Back to Main Page");
            singleLine();
            System.out.print("Please select an option: ");
            int menu = input.nextInt();
            input.nextLine();
            switch(menu){
                case 1: searchPatientPage(); break;
                case 2: mainPage(); break;
                default:
            }
        }
        else{
            System.out.println(RED + "Patient not found." + RESET);
        }
    }

    static void searchPatientPage(){
        System.out.print("\033[H\033[2J");
        printTitle("SEARCH PATIENT");
        System.out.print("Patient ID: ");
        int id = input.nextInt();
        input.nextLine();
        searchPatient(id);
    }

    static void scheduleAppointment(){
        System.out.print("\033[H\033[2J");
        printTitle("SCHEDULE APPOINTMENT");
    
    }

    static void displayAllPatients(){
        System.out.print("\033[H\033[2J");
        printTitle("REGISTERED PATIENTS");
        prm.displayAllPatients();
        // singleLine();
    }

    static void doctorFound(int id){
        Doctor doctor = doctorLogin.findDoctorByID(id);
        if(doctor != null){
            System.out.println("Doctor found: ");
            printTab("ID");
            System.out.println(doctor.id);
            printTab("Full Name");
            System.out.println(doctor.name);
            printTab("Specialty");
            System.out.println(doctor.specialty);
            singleLine();
        }
        else{
            System.out.println(RED + "Doctor not found." + RESET);
        }
    }

    static void doctorLogin(){
        System.out.print("\033[H\033[2J");
        printTitle("DOCTOR LOGIN");
        System.out.print("Doctor ID: ");
        int id = input.nextInt();
        input.nextLine();
        Doctor doctor = doctorLogin.findDoctorByID(id);
        if(doctor != null){
            doctorFound(id);
            System.out.println("[1] Login\n[2] Cancel");
            singleLine();
            System.out.print("Please select an option: ");
            int menu = input.nextInt();
            input.nextLine();
            System.out.print("\033[H\033[2J");
            switch(menu){
                case 1:
                    printTitle("SUCCESS");
                    LocalDateTime currentDateTime = LocalDateTime.now();
                    String dateTime = currentDateTime.toLocalDate() + " " + currentDateTime.toLocalTime();
                    doctor.loginTime = dateTime;
                    doctorLogin.loginDoctor(doctor);
                    break;
                case 2: 
                    printTitle("CANCELLED");    
                    System.out.println("Doctor login cancelled.");
                    redirect();
                    break;
                default:
            }
            
        }
        else{
            System.out.println(RED + "Doctor not found." + RESET);
        }
    }

    static void doctorLogout(){
        System.out.print("\033[H\033[2J");
        printTitle("DOCTOR LOGOUT");
        System.out.print("Doctor ID: ");
        int id = input.nextInt();
        input.nextLine();
        Doctor doctor = doctorLogin.findDoctorByID(id);
        if(doctor != null){
            doctorFound(id);
            System.out.println("[1] Logout\n[2] Cancel");
            singleLine();
            System.out.print("Please select an option: ");
            int menu = input.nextInt();
            input.nextLine();
            System.out.print("\033[H\033[2J");
            switch(menu){
                case 1: 
                    printTitle("SUCCESS");
                    doctorLogin.logoutDoctor(id);
                    break;
                case 2: 
                    printTitle("CANCELLED");
                    System.out.println("Doctor logout cancelled.");
                    redirect();
                    break;
                default:
            }
        }
        else{
            System.out.println(RED + "Doctor not found." + RESET);
            return;
        }
    }

    static void viewLastLoggedInDoctor(){
        System.out.print("\033[H\033[2J");
        printTitle("LAST LOGGED-IN DOCTOR");
        doctorLogin.getAllLoggedInDoctors();
    }

    static void mainPage(){
        System.out.print("\033[H\033[2J");
        printTitle("AMBALABU CLINIC");
        System.out.println("[1] Add New Patient\n[2] Remove Patient by ID\n[3] Search Patient by Name");
        System.out.println("[4] Display All Patients\n[5] Doctor login\n[6] Doctor Logout");
        System.out.println("[7] View Last Logged-in Doctor\n[8] Schedule Appointment\n[9] Process Appointment");
        System.out.println("[10] Display Upcoming Appointments\n[11] Search Patient by ID\n[12] Display All Patients\n[0] Exit");
        singleLine();
        System.out.print("Please select an option: ");
        int menu = input.nextInt();
        input.nextLine();
        switch(menu){
            case 0: System.exit(0); break;
            case 1: addNewPatient(); break;
            case 2: removePatient(); break;
            case 3: searchPatientPage(); break;
            case 4: displayAllPatients(); break;
            case 5: doctorLogin(); break;
            case 6: doctorLogout(); break;
            case 7: viewLastLoggedInDoctor(); break;
            case 8: break;
            case 9: break;
            // case 10: displayAppointments(); break;
            case 11: break;
            case 12: break;
            default:
        }
    }

    static void loginPage(){
        System.out.print("\033[H\033[2J");
        printTitle("AMBALABU CLINIC");
        System.out.println("Welcome to Ambalabu Clinic! Please Login to continue.");
        singleLine();
        System.out.println("Login as:");
        System.out.println("[1] Patient\n[2] Doctor\n[3] Admin");
        singleLine();
        System.out.print("Please select an option: ");
        role = input.nextInt();
        input.nextLine();
        mainPage();
    }

    public static void main(String[] args){
        initialize();
        mainPage();
    }
}