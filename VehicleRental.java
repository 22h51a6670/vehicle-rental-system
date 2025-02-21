import java.util.InputMismatchException;
import java.util.Scanner;

// Abstraction: Define an abstract class for Vehicle
abstract class Vehicle {
    private String type;
    private double pricePerDay;

    public Vehicle(String type, double pricePerDay) {
        this.type = type;
        this.pricePerDay = pricePerDay;
    }

    public String getType() {
        return type;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }
}

// Concrete vehicle classes (Inheritance)
class Sedan extends Vehicle { public Sedan() { super("Sedan (5-Seater)", 2500); } }
class Hatchback extends Vehicle { public Hatchback() { super("Hatchback (5-Seater)", 2000); } }
class SUV extends Vehicle { public SUV() { super("SUV (7-Seater)", 3500); } }
class MUV extends Vehicle { public MUV() { super("MUV (7-Seater)", 4000); } }
class SportsBike extends Vehicle { public SportsBike() { super("Sports Bike", 1000); } }
class CruiserBike extends Vehicle { public CruiserBike() { super("Cruiser Bike", 800); } }
class Scooter extends Vehicle { public Scooter() { super("Scooter", 500); } }

// Interface to enforce rental operations
interface Rentable {
    double calculateTotalPrice();
    String getDetails();
}

// Reservation class implementing Rentable
class Reservation implements Rentable, Runnable {
    private Vehicle vehicle;
    private int days;

    public Reservation(Vehicle vehicle, int days) {
        this.vehicle = vehicle;
        this.days = days;
    }

    @Override
    public double calculateTotalPrice() {
        return vehicle.getPricePerDay() * days;
    }

    @Override
    public String getDetails() {
        return "\nReservation Details:" +
               "\n---------------------" +
               "\nVehicle: " + vehicle.getType() +
               "\nDays: " + days +
               "\nTotal Price: " + calculateTotalPrice() + " Rs\n";
    }

    @Override
    public void run() {
        System.out.println("Processing your reservation...");
        try {
            Thread.sleep(2000); // Simulate processing delay
        } catch (InterruptedException e) {
            System.out.println("Error in processing reservation: " + e.getMessage());
        }
        System.out.println(getDetails());
    }
}

public class VehicleRental {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Vehicle vehicle = null;
        int days = 0;

        System.out.println("Welcome to Vehicle Rental System");
        System.out.println("--------------------------------");
        System.out.println("Available Vehicles:");
        System.out.println("1. Sedan (5-Seater) - 2500 Rs/day");
        System.out.println("2. Hatchback (5-Seater) - 2000 Rs/day");
        System.out.println("3. SUV (7-Seater) - 3500 Rs/day");
        System.out.println("4. MUV (7-Seater) - 4000 Rs/day");
        System.out.println("5. Sports Bike - 1000 Rs/day");
        System.out.println("6. Cruiser Bike - 800 Rs/day");
        System.out.println("7. Scooter - 500 Rs/day");
        
        while (vehicle == null) {
            try {
                System.out.print("Enter the vehicle type (1-7): ");
                int vehicleChoice = scanner.nextInt();
                switch (vehicleChoice) {
                    case 1 -> vehicle = new Sedan();
                    case 2 -> vehicle = new Hatchback();
                    case 3 -> vehicle = new SUV();
                    case 4 -> vehicle = new MUV();
                    case 5 -> vehicle = new SportsBike();
                    case 6 -> vehicle = new CruiserBike();
                    case 7 -> vehicle = new Scooter();
                    default -> System.out.println("Invalid choice. Please select again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 7.");
                scanner.next(); // Clear invalid input
            }
        }

        while (true) {
            try {
                System.out.print("Enter the number of days: ");
                days = scanner.nextInt();
                if (days > 0) {
                    break;
                } else {
                    System.out.println("Invalid number of days. Please enter a positive number.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next(); // Clear invalid input
            }
        }
        
        Reservation reservation = new Reservation(vehicle, days);
        Thread reservationThread = new Thread(reservation);
        reservationThread.start();
        
        scanner.close();
    }
}
