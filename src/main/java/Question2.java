import java.util.Scanner;
import java.util.Stack;

/**
 *  Name: Luke Clarke
 *  Class Group: SD2A
 */
public class Question2  // Car Parking - Stack
{
    public static void runSimulation()
    {
        Stack<Integer> driveway = new Stack<>();
        Stack<Integer> street = new Stack<>();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter positive numbers to add cars, negative to retrieve, and 0 to stop:");

        while (true) {
            int input = scanner.nextInt();

            if (input == 0) {
                System.out.println("Simulation ended.");
                break;
            } else if (input > 0) {
                // Add car to driveway
                driveway.push(input);
                System.out.println("Added car " + input + " to the driveway.");
            } else {
                // Retrieve car from driveway
                int carToRetrieve = -input;
                if (!driveway.contains(carToRetrieve)) {
                    System.out.println("Car " + carToRetrieve + " is not in the driveway.");
                } else {
                    // Temporarily move cars to street
                    while (driveway.peek() != carToRetrieve) {
                        int movedCar = driveway.pop();
                        street.push(movedCar);
                        System.out.println("Moved car " + movedCar + " to the street.");
                    }
                    // Remove the desired car
                    driveway.pop();
                    System.out.println("Car " + carToRetrieve + " has left the driveway.");

                    // Move cars back from street to driveway
                    while (!street.isEmpty()) {
                        int returnedCar = street.pop();
                        driveway.push(returnedCar);
                        System.out.println("Moved car " + returnedCar + " back to the driveway.");
                    }
                }
            }
            // Display the current state of the driveway
            System.out.println("Current driveway: " + driveway);
        }

        scanner.close();
    }

    public static void main(String[] args) {
        runSimulation();
    }
}