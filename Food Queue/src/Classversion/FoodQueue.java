package Classversion;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;


public class FoodQueue {
    private String firstName;
    private String lastName;
    private int id;
    private int pizzasRequired;
    private Queue<Character> queue;


    public FoodQueue(String firstName, String lastName, int id, int pizzasRequired) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.pizzasRequired = pizzasRequired;
        queue = new LinkedList<>();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getId() {
        return id;
    }

    public int getPizzasRequired() {
        return pizzasRequired;
    }

    public Queue<Character> getQueue() {
        return queue;
    }
}

class SnackKFC {
    private static final int Que_1 = 2;
    private static final int Que_2 = 3;
    private static final int Que_3 = 5;
    private static final int Max_pizza = 100; // maximum numbers of pizzas in the shop

    private static final FoodQueue[] Que_array1 = new FoodQueue[Que_1];
    private static final FoodQueue[] Que_array2 = new FoodQueue[Que_2];
    private static final FoodQueue[] Que_array3 = new FoodQueue[Que_3];

    private static int stock = Max_pizza;
    private static int pizzasRequired;
    private static List<FoodQueue> waitingList = new ArrayList<>();

    private static int price_per_pizza = 1350;

    private static int incomeQueue1 = 0;
    private static int incomeQueue2 = 0;
    private static int incomeQueue3 = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int choice;

        do {
            System.out.println("========================");
            System.out.println("Snack King Food Center");
            System.out.println("========================");
            System.out.println("100 or VFQ: View all Queues");
            System.out.println("101 or VEQ: View all Empty Queues");
            System.out.println("102 or ACQ: Add customer to a Queue");
            System.out.println("103 or RCQ: Remove a customer from a Queue (From a specific location)");
            System.out.println("104 or PCQ: Remove a served customer");
            System.out.println("105 or VCS: View Customers Sorted in alphabetical order");
            System.out.println("106 or SPD: Store Program Data into file");
            System.out.println("107 or LPD: Load Program Data from file");
            System.out.println("108 or STK: View Remaining pizzas in Stock");
            System.out.println("109 or AFS: Add pizzas to Stock");
            System.out.println("110 or IFQ: Income of each queue");
            System.out.println("999 or EXT: Exit the Program");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // consume the newline character

            switch (choice) {
                case 100:
                    viewAllQueues();
                    break;
                case 101:
                    viewAllEmptyQueues();
                    break;
                case 102:
                    addCustomerToQueue();
                    break;
                case 103:
                    removeCustomerFromQueue();
                    break;
                case 104:
                    removeServedCustomer();
                    break;
                case 105:
                    viewCustomersSorted();
                    break;
                case 106:
                    storeProgramData();
                    break;
                case 107:
                    loadProgramData();
                    break;
                case 108:
                    viewRemainingStock();
                    break;

                case 109:
                    addPizzasToStock();
                    break;
                case 110:
                    viewIncomeOfEachQueue();
                    break;
                case 999:
                    System.out.println("Exiting the program.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 999);
    }

    private static void viewAllQueues() {
        System.out.println("*********************");
        System.out.println("*********************");
        System.out.println("Cashier");
        System.out.println("*********************");
        System.out.println("*********************");

        System.out.println("Queue 1:");
        printQueue(Que_array1);

        System.out.println("Queue 2:");
        printQueue(Que_array2);

        System.out.println("Queue 3:");
        printQueue(Que_array3);
    }

    private static void printQueue(FoodQueue[] queue) {
        for (FoodQueue customer : queue) {
            if (customer != null) {
                System.out.print("O ");
            } else {
                System.out.print("X ");
            }
        }
        System.out.println();
    }


    // Modify viewAllEmptyQueues() to call the new method for each queue
    private static void viewAllEmptyQueues() {
        boolean emptyFound = false; // Flag to track if any empty slots are found

        // Check and print empty slots in Queue 1
        if (!printEmptySlots(Que_array1, 1)) {
            emptyFound = true;
        }

        // Check and print empty slots in Queue 2
        if (!printEmptySlots(Que_array2, 2)) {
            emptyFound = true;
        }

        // Check and print empty slots in Queue 3
        if (!printEmptySlots(Que_array3, 3)) {
            emptyFound = true;
        }

        // If no empty slots found in any queue, inform the user
        if (!emptyFound) {
            System.out.println("All queues are currently empty.");
        }
    }

    // Modify printEmptySlots() to return a boolean indicating if any empty slots were found
    private static boolean printEmptySlots(FoodQueue[] queue, int queueNumber) {
        boolean emptyFound = false; // Flag to track if empty slots are found

        System.out.println("Empty slots in Queue " + queueNumber + ":");
        for (int i = 0; i < queue.length; i++) {
            if (queue[i] == null) {
                System.out.println("Slot " + (i + 1));
                emptyFound = true; // Set the flag to true if an empty slot is found
            }
        }

        return emptyFound; // Return whether empty slots were found in this queue
    }


    private static boolean isEmpty(FoodQueue[] queue) {
        for (FoodQueue customer : queue) {
            if (customer != null) {
                return false; // Queue has at least one customer
            }
        }
        return true; // Queue is empty
    }

    private static int nextCustomerId = 1; // Initialize with 1

    private static int generateCustomerId() {
        return nextCustomerId++;
    }

    private static Scanner scanner = new Scanner(System.in);

    private static void addToWaitingList(String firstName, String lastName, int customerId, int pizzasRequired) {
        FoodQueue customer = new FoodQueue(firstName, lastName, customerId, pizzasRequired);
        waitingList.add(customer);
    }

    private static void addCustomerToQueue() {
        Scanner scanner = new Scanner(System.in);
        int queueNumber;

        while (true) {
            System.out.print("Enter queue number (1, 2, or 3): ");

            if (scanner.hasNextInt()) {
                queueNumber = scanner.nextInt();
                scanner.nextLine(); // consume the newline character
                if (queueNumber >= 1 && queueNumber <= 3) {
                    // Valid input, break the loop
                    break;
                } else {
                    System.out.println("Please enter a valid number (1, 2, or 3).");
                }
            } else {
                // Invalid input, not an integer
                System.out.println("Please enter a valid number (1, 2, or 3).");
                scanner.nextLine(); // consume the invalid input
            }
        }

        FoodQueue[] queue = null;
        int maxSize = 0;

        switch (queueNumber) {
            case 1:
                queue = Que_array1;
                maxSize = Que_1;
                break;
            case 2:
                queue = Que_array2;
                maxSize = Que_2;
                break;
            case 3:
                queue = Que_array3;
                maxSize = Que_3;
                break;
            default:
                System.out.println("Invalid. Please try again.");
                return;
        }

        if (!isFull(queue, maxSize)) {
            System.out.println("Have some empty slots. Customer is adding");

            int customerId = generateCustomerId();

            System.out.print("Enter first name: ");
            String firstName = scanner.nextLine();
            System.out.print("Enter last name: ");
            String lastName = scanner.nextLine();
            System.out.println("Enter customer ID :");
            customerId = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Enter number of pizzas required: ");

            pizzasRequired = scanner.nextInt();
            scanner.nextLine(); // consume the newline character

            // Check if there is enough stock to fulfill the order
            if (stock >= pizzasRequired) {
                // Reduce the stock by the required amount
                stock -= pizzasRequired;

                // Find the first empty slot in the queue
                int i;
                for (i = 0; i < maxSize; i++) {
                    if (queue[i] == null) {
                        break;
                    }
                }

                FoodQueue customer = new FoodQueue(firstName, lastName, customerId, pizzasRequired);
                queue[i] = customer; // Assign the customer to the first empty slot
                System.out.println("Customer " +  firstName + " " +
                        lastName + " added to Queue " + queueNumber + ".");
                System.out.println("Remaining pizzas in the stock: " + stock);

                // Continue the loop to process more customers
            } else {
                addToWaitingList(firstName, lastName, customerId, pizzasRequired);
                System.out.println("Sorry, we don't have enough pizzas in stock to place your order.");
            }
        } else {
            int customerId = generateCustomerId();
            System.out.print("Enter first name: ");
            String firstName = scanner.nextLine();
            System.out.print("Enter last name: ");
            String lastName = scanner.nextLine();
            System.out.println("Enter customer ID :");
            customerId = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Enter number of pizzas required: ");
            int pizzasRequired = scanner.nextInt();
            scanner.nextLine(); // consume the newline character

            addToWaitingList(firstName, lastName, customerId, pizzasRequired);
            System.out.println(" Sorry. All queues are full.Your data have stored.You have added to the waiting list.");


        }

    }


    private static boolean isFull(FoodQueue[] queue, int maxSize) {
        for (FoodQueue customer : queue) {
            if (customer == null) {
                return false; // Queue has at least one empty slot
            }
        }
        return true; // Queue is full
    }


    private static void removeCustomerFromQueue() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter queue number (1, 2, or 3): ");
        int queueNumber = scanner.nextInt();
        scanner.nextLine(); // consume the newline character

        FoodQueue[] queue = null;
        int maxSize = 0;

        switch (queueNumber) {
            case 1:
                queue = Que_array1;
                maxSize = Que_1;
                break;
            case 2:
                queue = Que_array2;
                maxSize = Que_2;
                break;
            case 3:
                queue = Que_array3;
                maxSize = Que_3;
                break;
            default:
                System.out.println("Invalid queue number. Please try again.");
                return;
        }

        System.out.println("Customers in Queue " + queueNumber + ":");
        boolean isEmpty = true;
        for (int i = 0; i < maxSize; i++) {
            if (queue[i] != null) {
                System.out.println((i + 1) + ". " + queue[i].getId());
                isEmpty = false;
            }
        }
        if (isEmpty) {
            System.out.println("This queue " + queueNumber + " is empty.");
            return;
        }

        System.out.print("Enter preferred queue position to remove the customer: ");
        int position = scanner.nextInt();
        scanner.nextLine(); // consume the newline character
        if (position < 1 || position > maxSize) {
            System.out.println("Invalid position. Please enter a valid one.");
            return;
        }
        if (queue[position - 1] == null) {
            System.out.println("No customer at that position " + position + " in Queue " + queueNumber + ".");
            return;
        }
        // Remove the customer from the specified position
        queue[position - 1] = null;

        // Shift the remaining customers to fill the gap
        for (int i = position - 1; i < maxSize - 1; i++) {
            if (queue[i + 1] != null) {
                queue[i] = queue[i + 1];
                queue[i + 1] = null;
            } else {
                break;
            }
        }

        System.out.println("Customer removed from Queue " + queueNumber + ".");
    }

    private static void removeServedCustomer() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter queue number (1, 2, or 3): ");
        int queueNumber = scanner.nextInt();
        scanner.nextLine(); // consume the newline character

        FoodQueue[] queue = null;
        int maxSize = 0;

        switch (queueNumber) {
            case 1:
                queue = Que_array1;
                maxSize = Que_1;
                break;
            case 2:
                queue = Que_array2;
                maxSize = Que_2;
                break;
            case 3:
                queue = Que_array3;
                maxSize = Que_3;
                break;
            default:
                System.out.println("Invalid queue number. Please try again.");
                return;
        }

        System.out.println("Serving customer in Queue " + queueNumber + ":");
        if (!isEmpty(queue)) {
            FoodQueue servedCustomer = queue[0];
            queue[0] = null;

            // Shift the remaining customers to fill the gap
            for (int i = 0; i < maxSize - 1; i++) {
                if (queue[i + 1] != null) {
                    queue[i] = queue[i + 1];
                    queue[i + 1] = null;
                } else {
                    break;
                }
            }

            System.out.println("Served customer removed from Queue " + queueNumber + ".");

            // Check if there are customers in the waiting list
            if (!waitingList.isEmpty()) {
                FoodQueue nextCustomer = waitingList.get(0);
                waitingList.remove(0);

                // Place the next customer in the food queue
                for (int i = 0; i < maxSize; i++) {
                    if (queue[i] == null) {
                        queue[i] = nextCustomer;
                        System.out.println("Next customer placed in Queue from waiting list " + queueNumber + ".");
                        break;
                    }
                }
            }
        } else {
            System.out.println("This Queue " + queueNumber + " is empty.");
        }
    }

    private static void viewCustomersSorted() {

        List<FoodQueue> allCustomers = new ArrayList<>();

        // Copy customers from all queues to the allCustomers list
        for (FoodQueue[] queue : new FoodQueue[][]{Que_array1, Que_array2, Que_array3}) {
            for (FoodQueue customer : queue) {
                if (customer != null) {
                    allCustomers.add(customer);
                }
            }
        }

        // Sort the allCustomers list by last names
        Collections.sort(allCustomers, Comparator.comparing(FoodQueue::getLastName));

        // Display the sorted customer list
        System.out.println("Sorted Customer List:");
        for (FoodQueue customer : allCustomers) {
            System.out.println(customer.getLastName() + ", " + customer.getFirstName());
        }
    }

    public static void storeProgramData() {
        try {
            FileWriter myWriter = new FileWriter("Snack_King_Program_Data.txt");
            myWriter.write("Snack King Food Center" + "\n\n");

            for (int i = 0; i < Que_array1.length; i++) {
                FoodQueue foodQueue = Que_array1[i];
                if (foodQueue != null) {
                    Queue<Character> queue = foodQueue.getQueue();
                    myWriter.write("Cashier 1: ");
                    for (Character element : queue) {
                        myWriter.write(element + " ");
                    }
                    myWriter.write("\n");
                }
            }
            myWriter.write("\n");
            for (int i = 0; i < Que_array2.length; i++) {
                FoodQueue foodQueue = Que_array2[i];
                if (foodQueue != null) {
                    Queue<Character> queue = foodQueue.getQueue();
                    myWriter.write("Cashier 2: ");
                    for (Character element : queue) {
                        myWriter.write(element + " ");
                    }
                    myWriter.write("\n");
                }
            }
            myWriter.write("\n");

            for (int i = 0; i < Que_array3.length; i++) {
                FoodQueue foodQueue = Que_array3[i];
                if (foodQueue != null) {
                    Queue<Character> queue = foodQueue.getQueue();
                    myWriter.write("Cashier 3: ");
                    for (Character element : queue) {
                        myWriter.write(element + " ");
                    }
                    myWriter.write("\n");
                }
            }
            myWriter.write("\n");





            myWriter.write("\n" + "Customer Names" + "\n\n");

            for (FoodQueue foodQueue : waitingList) {
                myWriter.write(foodQueue.getFirstName() + " " + foodQueue.getLastName() + "\n");
            }

            myWriter.close();
            System.out.println("Snack King program data have been stored successfully.");
        } catch (IOException e) {
            System.out.println("An error has occurred. Please try again");
            e.printStackTrace();
        }
    }

    public static void loadProgramData() {
        try {
            FileReader fileReader = new FileReader("Snack_King_Program_Data.txt");
            Scanner scanner = new Scanner(fileReader);

            // Load Cashier Queues
            for (int i = 0; i < Que_array1.length; i++) {
                String queueLine = scanner.nextLine();
                if (queueLine.startsWith("Cashier 1: ")) {
                    String[] elements = queueLine.substring(11).split(" ");
                    for (String element : elements) {
                        Que_array1[i].getQueue().add(element.charAt(0));
                    }
                } else {
                    break;
                }
            }
            // Load Cashier Queues for Que_array2
            for (int i = 0; i < Que_array2.length; i++) {
                String queueLine = scanner.nextLine();
                if (queueLine.startsWith("Cashier 2: ")) {
                    String[] elements = queueLine.substring(11).split(" ");
                    for (String element : elements) {
                        Que_array2[i].getQueue().add(element.charAt(0));
                    }
                } else {
                    break;
                }
            }
            // Load Cashier Queues for Que_array3
            for (int i = 0; i < Que_array3.length; i++) {
                String queueLine = scanner.nextLine();
                if (queueLine.startsWith("Cashier 3: ")) {
                    String[] elements = queueLine.substring(11).split(" ");
                    for (String element : elements) {
                        Que_array3[i].getQueue().add(element.charAt(0));
                    }
                } else {
                    break;
                }
            }





            // Skip empty line
            scanner.nextLine();

            // Load Customer Names to the waiting list
            while (scanner.hasNextLine()) {
                String customerName = scanner.nextLine();
                String[] nameParts = customerName.split(" ");
                if (nameParts.length == 2) {
                    addToWaitingList(nameParts[0], nameParts[1], generateCustomerId(), 0); // Assuming pizzasRequired is 0 for waiting customers
                }
            }

            scanner.close();

            System.out.println("The program data of the Snack King Food Center has been loaded from the text file.");

        } catch (IOException e) {
            System.out.println("An error has occurred. Please try again.");
            e.printStackTrace();
        }
    }


    private static void viewRemainingStock() {
        // Check if there is enough stock to fulfill the order

        System.out.println("Remaining pizzas in stock: " + stock);

    }


    private static void addPizzasToStock() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of pizzas to add to Stock: ");
        int pizzasToAdd = scanner.nextInt();
        scanner.nextLine(); // consume the newline character

        if (stock + pizzasToAdd <= Max_pizza) {
            stock += pizzasToAdd;
            System.out.println(pizzasToAdd + " pizzas added to Stock. New stock: " + stock);
        } else {
            int spaceAvailable = Max_pizza - stock;
            System.out.println("You can only add up to " + spaceAvailable + " pizzas to reach the maximum limit of 100.");
        }
    }


    public static void viewIncomeOfEachQueue() {
        System.out.println("Income of each queue:");

        // Calculate income for Queue 1
        int incomeQueue1 = 0;
        for (FoodQueue customer : Que_array1) {
            if (customer != null) {
                incomeQueue1 += customer.getPizzasRequired() * price_per_pizza;
            }
        }
        System.out.println("Queue 1: " + incomeQueue1);

        // Calculate income for Queue 2
        int incomeQueue2 = 0;
        for (FoodQueue customer : Que_array2) {
            if (customer != null) {
                incomeQueue2 += customer.getPizzasRequired() * price_per_pizza;
            }
        }
        System.out.println("Queue 2: " + incomeQueue2);

        // Calculate income for Queue 3
        int incomeQueue3 = 0;
        for (FoodQueue customer : Que_array3) {
            if (customer != null) {
                incomeQueue3 += customer.getPizzasRequired() * price_per_pizza;
            }
        }
        System.out.println("Queue 3: " + incomeQueue3);
    }


}

