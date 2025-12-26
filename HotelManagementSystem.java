import java.io.*;
import java.util.*;

/* ================= CUSTOM EXCEPTION ================= */
class HotelException extends Exception 
{
    public HotelException(String message) 
	{
        	super(message);
    	}
}

/* ================= ROOM ABSTRACTION ================= */
abstract class Room implements Serializable 
{
    	int roomNumber;
    	boolean isBooked;
    	double price;

	Room(int roomNumber, double price) 
	{
	        this.roomNumber = roomNumber;
        	this.price = price;
        	this.isBooked = false;
    	}

    	abstract String getRoomType();
}

class LuxuryRoom extends Room 
{
    	LuxuryRoom(int roomNumber) 
	{
        	super(roomNumber, 5000);
    	}

    	String getRoomType() 
	{
        	return "Luxury Room";
    	}
}

class DeluxeRoom extends Room 
{
	DeluxeRoom(int roomNumber) 
	{
        	super(roomNumber, 3000);
    	}
	String getRoomType() 
	{
        	return "Deluxe Room";
    	}
}

/* ================= CUSTOMER ================= */
class Customer implements Serializable 
{
    	String name;
    	String contact;

    	Customer(String name, String contact) 
	{
        	this.name = name;
        	this.contact = contact;
    	}
}

/* ================= FOOD ================= */
class Food implements Serializable 
{
    	String itemName;
    	int quantity;
    	double price;

    	Food(String itemName, int quantity, double price) 
	{
        	this.itemName = itemName;
        	this.quantity = quantity;
        	this.price = price;
    	}

    	double getTotalPrice() 
	{
        	return quantity * price;
    	}
}

/* ================= BOOKING ================= */
class Booking implements Serializable 
{
    	Room room;
    	Customer customer;
    	ArrayList<Food> foodOrders = new ArrayList<>();

    	Booking(Room room, Customer customer) 
	{
        	this.room = room;
        	this.customer = customer;
    	}

    	void addFood(Food food) 
	{
        	foodOrders.add(food);
    	}

    	double calculateTotalBill() 
	{
        	double total = room.price;
        	for (Food food : foodOrders) 
		{
            		total += food.getTotalPrice();
        	}
        return total;
    	}
}

/* ================= DATA STORAGE (SERIALIZATION) ================= */
class DataStore 
{
    	private static final String FILE_NAME = "hotel_data.ser";

    	static void save(HashMap<Integer, Booking> bookings) 
	{
        	try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) 
		{
            	oos.writeObject(bookings);
        	}
		catch (IOException e) 
		{
            		System.out.println("Error while saving data.");
        	}
    	}
	@SuppressWarnings("unchecked")
	static HashMap<Integer, Booking> load() 
	{
        	try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) 
		{
            		return (HashMap<Integer, Booking>) in.readObject();
        	} 
		catch (Exception e) 
		{
            	    return new HashMap<>();
		}
	}
}




/* ================= MAIN CONTROLLER ================= */
public class HotelManagementSystem 
{

    	static Scanner sc = new Scanner(System.in);
    	static ArrayList<Room> rooms = new ArrayList<>();
    	static HashMap<Integer, Booking> bookings;

    	public static void main(String[] args) 
	{

        	bookings = DataStore.load();
        	initializeRooms();

        	while (true) 
		{
            		try 
			{
                	showMenu();
                	int choice = sc.nextInt();

                		switch (choice) 
				{
                    			case 1 -> bookRoom();
                    			case 2 -> orderFood();
                    			case 3 -> generateBill();
                    			case 4 -> viewBookings();
                    			case 5 -> exitSystem();
                    			default -> throw new HotelException("Invalid menu choice");
                		}
            		} 
			catch (HotelException e) 
			{
                		System.out.println("Error: " + e.getMessage());
            		} 
			catch (Exception e) 
			{
                		System.out.println("Invalid input. Please try again.");
                		sc.nextLine();
            		}
        	}
	}

    /* ================= MENU ================= */
    	static void showMenu() 
	{
        	System.out.println("\n===== HOTEL MANAGEMENT SYSTEM =====");
        	System.out.println("1. Book Room");
        	System.out.println("2. Order Food");
        	System.out.println("3. Generate Bill");
        	System.out.println("4. View All Bookings");
        	System.out.println("5. Exit");
        	System.out.print("Enter choice: ");
    	}

    /* ================= INITIALIZATION ================= */
    	static void initializeRooms() 
	{
        	if (!rooms.isEmpty()) return;

        	for (int i = 1; i <= 5; i++)
            		rooms.add(new LuxuryRoom(i));

        	for (int i = 6; i <= 10; i++)
            rooms.add(new DeluxeRoom(i));
    	}

    /* ================= BOOK ROOM ================= */
    	static void bookRoom() throws HotelException 
	{
        	System.out.print("Enter room number: ");
        	int roomNo = sc.nextInt();

        	Room room = rooms.stream()
                	.filter(r -> r.roomNumber == roomNo)
                	.findFirst()
                	.orElseThrow(() -> new HotelException("Room does not exist"));

        	if (room.isBooked)
            	throw new HotelException("Room already booked");

        	sc.nextLine();
        	System.out.print("Customer Name: ");
        	String name = sc.nextLine();
        	System.out.print("Contact Number: ");
        	String contact = sc.nextLine();

        	room.isBooked = true;
        	bookings.put(roomNo, new Booking(room, new Customer(name, contact)));

        	System.out.println("Room booked successfully!");
    	}

    /* ================= ORDER FOOD ================= */
    	static void orderFood() throws HotelException 
	{
        	System.out.print("Enter room number: ");
        	int roomNo = sc.nextInt();

        	Booking booking = bookings.get(roomNo);
        	if (booking == null)
            		throw new HotelException("No booking found for this room");

        	sc.nextLine();
        	System.out.print("Food item: ");
        	String item = sc.nextLine();
        	System.out.print("Quantity: ");
        	int qty = sc.nextInt();

        	booking.addFood(new Food(item, qty, 200));
        	System.out.println("Food added successfully!");
    	}

    /* ================= GENERATE BILL ================= */
    	static void generateBill() throws HotelException 
	{
        	System.out.print("Enter room number: ");
        	int roomNo = sc.nextInt();

        	Booking booking = bookings.get(roomNo);
        	if (booking == null)
            		throw new HotelException("No booking found");

        	System.out.println("\n----- BILL DETAILS -----");
        	System.out.println("Customer: " + booking.customer.name);
        	System.out.println("Room Type: " + booking.room.getRoomType());
        	System.out.println("Room Charges: ₹" + booking.room.price);

        	double foodTotal = 0;
        	for (Food food : booking.foodOrders) 
		{
            		System.out.println(food.itemName + " x " + food.quantity + " = ₹" + food.getTotalPrice());
            		foodTotal += food.getTotalPrice();
        	}

        	System.out.println("Food Charges: ₹" + foodTotal);
        	System.out.println("Total Bill: ₹" + booking.calculateTotalBill());
    	}

    /* ================= VIEW BOOKINGS ================= */
    	static void viewBookings() 
	{
        	if (bookings.isEmpty()) 
		{
            		System.out.println("No bookings found.");
            		return;
        	}

        	for (Map.Entry<Integer, Booking> entry : bookings.entrySet()) 
		{
            		Booking b = entry.getValue();
            		System.out.println("Room " + entry.getKey() + " | " + b.customer.name + " | " + b.room.getRoomType());
        	}
    	}

    /* ================= EXIT ================= */
    	static void exitSystem() 
	{
        	DataStore.save(bookings);
        	System.out.println("Data saved. Exiting system...");
        	System.exit(0);
    	}
}
