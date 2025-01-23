import java.util.*;
// Vehicle structure to store the details of each parked vehicle
class Vehicle {
    String type;          // Type of vehicle (car, bike)
    String license;       // License plate of the vehicle
    int entryTime;        // Entry time of the vehicle in hours

    //Constructor for Vehicle class
    Vehicle(String type, String license, int entryTime) {
        this.type = type;
        this.license = license;
        this.entryTime = entryTime;
    }
}
public class Parking_Lot_Management_System {
    private int totalSpots;                            // Total parking spots in the lot
    private Stack<Integer> availableSpots;             // Stack for LIFO spot allocation
    private Map<String, Vehicle> parkedVehicles;       // Map of license -> Vehicle details
    private Map<String, Integer> vehicleSpotMap;       // Map of license -> Spot number
    private final int parkingRate = 20;                // Fixed parking fee rate (₹20 per hour)

    public Parking_Lot_Management_System(int totalSpots) {
        // Constructor to initialize parking lot with the total spots
        this.totalSpots = totalSpots;
        this.availableSpots = new Stack<>();
        this.parkedVehicles = new HashMap<>();
        this.vehicleSpotMap = new HashMap<>();
        for (int i = totalSpots; i >= 1; i--) {
            availableSpots.push(i);                    // Fill the stack with spots in reverse order
        }
    }

    // Function to park a vehicle
    public void parkVehicle(String vehicleType, String license, int entryTime) {
        // If no parking spots are available
        if (availableSpots.isEmpty()) {
            System.out.println("-1");
            return;
        }

        // If the vehicle is already parked
        if (parkedVehicles.containsKey(license)) {
            System.out.println("Vehicle with license " + license + " is already parked.");
            return;
        }

        // Allocate the top spot from the stack
        int spot = availableSpots.pop();

        // Store vehicle details
        parkedVehicles.put(license, new Vehicle(vehicleType, license, entryTime));
        vehicleSpotMap.put(license, spot);

        // Output the allocated spot
        System.out.println("Spot " + spot + " allocated to " + vehicleType + " " + license + ".");
    }

    // Function to remove a vehicle and calculate the parking fee
    public void removeVehicle(String license, int exitTime) {

        // If the vehicle is not found in the parking lot
        if (!parkedVehicles.containsKey(license)) {
            System.out.println("-1");
            return;
        }

        // Retrieve vehicle details
        Vehicle vehicle = parkedVehicles.get(license);
        int spot = vehicleSpotMap.get(license);
        int duration = Math.max(1, exitTime - vehicle.entryTime);
        int fee = (int) Math.ceil(duration) * parkingRate;

        // Free the parking spot and remove the vehicle from records
        availableSpots.push(spot);
        parkedVehicles.remove(license);
        vehicleSpotMap.remove(license);

        // Output the removal message and parking fee
        System.out.println(vehicle.type + " " + license + " removed from Spot " + spot + ". Parking fee: ₹" + fee + ".");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        int totalSpots;
        do {
            System.out.print("Enter total parking spots (1-1000): ");
            totalSpots = scanner.nextInt();
            if (totalSpots < 1 || totalSpots > 1000) {
                System.out.println("Invalid input. Please enter a number between 1 and 1000.");
            }
        } while (totalSpots < 1 || totalSpots > 1000);

        // Initialize parking lot
        Parking_Lot_Management_System parkingLot = new Parking_Lot_Management_System(totalSpots);

        while (true) {
            System.out.println("\n1. Park Vehicle\n2. Remove Vehicle\n3. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();

            if (choice == 3) {
                break;
            }

            String vehicleType, license;
            int time;

            switch (choice) {
                case 1:     // Park Vehicle
                    System.out.print("Enter vehicle type (car/bike): ");
                    vehicleType = scanner.next();
                    System.out.print("Enter license number: ");
                    license = scanner.next();
                    System.out.print("Enter entry time (0-23): ");
                    time = scanner.nextInt();
                    parkingLot.parkVehicle(vehicleType, license, time);
                    break;
                case 2:     // Remove Vehicle
                    System.out.print("Enter license number: ");
                    license = scanner.next();
                    System.out.print("Enter exit time (0-23): ");
                    time = scanner.nextInt();
                    parkingLot.removeVehicle(license, time);
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
