//i want to insert a scanner object in the array
import java.util.Random;
import java.util.Scanner;

public class tresureHunt {
    public class TresureHunt {
        public static void main(String[] args) {
            try (Scanner scanner = new Scanner(System.in)) {
                int low = 1;
                int high = 1000;
                int steps = 0;

                Random random = new Random();
                int treasureLocation = random.nextInt(high) + 1;

                int guess;
                System.out.println("Ayo cari harta karun! Masukan angka mulai dari 1 hingga 1000...\n");

                while(low <= high) {
                    System.out.print("Masukan angka: ");
                    String response = scanner.next();
                    guess = (high + low) / 2;
                    steps++;

                    System.out.print("Tebakan ke-" + steps + ": Kamu menebak dengan angka " + response + "->\n");

                    if (guess < treasureLocation) {
                        System.out.println(response + " Terlalu kecil!");
                        low = guess + 1;
                        } else if (guess > treasureLocation) {
                            System.out.println(response + " Terlalu besar!");
                            high = guess - 1;
                            } else {
                                System.out.println("Benar!!!!!!!!");
                                break;
                            }
                    }   

                    System.out.println("\nHarta karun ditemukan pada " + treasureLocation + " dalam " + steps + " langkah");
            }
        }
    }
}