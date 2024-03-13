import java.util.Scanner;

public class SnackKFC {


    private static final int Que_1 = 2;
    private static final int Que_2 = 3;
    private static final int Que_3 = 5;
    private static final int Max_pizza = 100; //maximum numbers of pizzas in the shop
    private static final int per_customer = 10; //10 pizza's per customer
    private static final int[] Que_array1 = new int[Que_1];
    private static final int[] Que_array2 = new int[Que_2];
    private static final int[] Que_array3 = new int[Que_3];

    private static int stock = 0;


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
            System.out.println("999 or EXT: Exit the Program");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // consume the newline character

            if (choice == 100) {
                viewAllQueues();
            } else if (choice == 101) {
                viewAllEmptyQueues();
            } else if (choice == 102) {
                addCustomerToQueue();
            } else if (choice == 103) {
                removeCustomerFromQueue();
            } else if (choice == 104) {
                removeServedCustomer();
            } else if (choice == 105) {
                viewCustomersSorted();
            } else if (choice == 106) {
                storeProgramData();
            } else if (choice == 107) {
                loadProgramData();
            } else if (choice == 108) {
                viewRemainingStock();
            } else if (choice == 109) {
                addPizzasToStock();

            } else if (choice == 999) {
                System.out.println("Exiting the program.");
            } else {
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
        printQueue(Que_array1, Que_1);
        printQueue(Que_array2, Que_2);
        printQueue(Que_array3, Que_3);
    }

    private static void printQueue(int[] queue, int maxSize) {
        for (int i = 0; i < maxSize; i++) {
            if (queue[i] == 0) {
                System.out.print("X ");
            } else {
                System.out.print("O ");
            }
        }
        System.out.println();
    }

    private static boolean viewAllEmptyQueues() {
        if (Que_array1.length == 0) {
            return true;
        } else if (Que_array2.length == 0) {
            return true;
        } else if (Que_array3.length == 0) {
            return true;
        } else {
            return false;
        }
    }


    private static boolean hasEmptySlots(int[] queue) {
        for (int slot : queue) {
            if (slot == 0) {
                return true; // Queue has at least one empty slot
            }
        }
        return false; // Queue is full
    }

    private static int nextCustomerId = 1; // Initialize with 1

    private static int generateCustomerId() {
        return nextCustomerId++;
    }

    private static void addCustomerToQueue() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter queue number (1, 2, or 3): ");
        int queueNumber = scanner.nextInt();
        scanner.nextLine(); // consume the newline character

        int[] queue = null;
        int maxSize = 0;

        if (queueNumber == 1) {
            queue = Que_array1;
            maxSize = Que_1;
        } else if (queueNumber == 2) {
            queue = Que_array2;
            maxSize = Que_2;
        } else if (queueNumber == 3) {
            queue = Que_array3;
            maxSize = Que_3;
        } else {
            System.out.println("Invalid. Try again.");
            return;
        }

        if (hasEmptySlots(queue)) {
            System.out.println("Have empty slots. Customer is adding");

            int customerId = generateCustomerId(); // Replace with your logic to generate a unique customer ID

            for (int i = 0; i < maxSize; i++) {
                if (queue[i] == 0) {
                    queue[i] = customerId; // Assign the customer to the first empty slot
                    System.out.println("Customer " + customerId + " added to Queue " + queueNumber + ".");
                    return;
                }
            }

        } else {
            System.out.println("Sorry. All queues are full. Customer added to the waiting list.");
        }
    }


    private static void removeCustomerFromQueue() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter queue number (1, 2, or 3): ");
        int queueNumber = scanner.nextInt();
        scanner.nextLine(); // consume the newline character

        int[] queue = null;
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
                System.out.println(" Invalid. Try again.");
                return;

        }
        System.out.println("Customers is in " + queueNumber + ":");
        boolean isEmpty = true;
        for (int i = 0; i < maxSize; i++) {
            if (queue[i] != 0) {
                System.out.println((i + 1) + ". " + queue[i]);
                isEmpty = false;
            }
        }
        if (isEmpty) {
            System.out.println("this queue " + queueNumber + " is empty.");
            return;
        }
        System.out.print("Enter  prefer position to remove the customer: ");
        int position = scanner.nextInt();
        scanner.nextLine(); // consume the newline character
        if (position < 1 || position > maxSize) {
            System.out.println("Invalid position. Please enter a valid position.");
            return;
        }
        if (queue[position - 1] == 0) {
            System.out.println("No customer at position " + position + " in Queue " + queueNumber + ".");
            return;
        }
        // Remove the customer from the specified position
        queue[position - 1] = 0;

        // Shift the remaining customers to fill the gap
        for (int i = position - 1; i < maxSize - 1; i++) {
            if (queue[i + 1] != 0) {
                queue[i] = queue[i + 1];
                queue[i + 1] = 0;
            } else {
                break;
            }
        }

        System.out.println("Customer removed from Queue " + queueNumber + ".");
    }


    private static final int[] frontPointers = new int[]{0, 0, 0}; // Initialize all front pointers to 0

    private static int removeServedCustomer() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter queue number (1, 2, or 3): ");
        int queueNumber = scanner.nextInt();
        scanner.nextLine(); // consume the newline character

        int[] queue = null;
        int front = -1;
        int maxSize = 0;

        switch (queueNumber) {
            case 1:
                queue = Que_array1;
                front = frontPointers[0];
                maxSize = Que_1;
                break;
            case 2:
                queue = Que_array2;
                front = frontPointers[1];
                maxSize = Que_2;
                break;
            case 3:
                queue = Que_array3;
                front = frontPointers[2];
                maxSize = Que_3;
                break;
            default:
                System.out.println("Invalid queue number. Please try again.");
                return -1;
        }

        if (isEmpty(queue, front)) {
            System.out.println("Queue " + queueNumber + " has no served customers.");
            return -1;
        }

        int removedCustomer = queue[front];
        queue[front] = 0;
        frontPointers[queueNumber - 1] = (front + 1) % maxSize;
        return removedCustomer;
    }

    private static boolean isEmpty(int[] queue, int front) {
        return queue[front] == 0;
    }

    private static void viewCustomersSorted() {
        System.out.println("Customers Sorted in alphabetical order :");
        // code this because in here I'm not taking customer name and id
    }

    private static void storeProgramData() {
        System.out.println("Storing program data into a file...");

    }

    private static void loadProgramData() {
        // Implement loading program data from a file
        System.out.println("Loading program data from a file...");

    }
    private static void viewRemainingStock() {

        // Check if there is enough stock to fulfill the order
        if (stock >= per_customer) {
            stock -= per_customer; // Deduct pizzas from stock
            System.out.println("Purchased " + per_customer + " pizzas. Remaining Stock: " + stock);
        } else {
            System.out.println("Sorry, we don't have enough pizzas in stock to fulfill your order.");
        }
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






}























































