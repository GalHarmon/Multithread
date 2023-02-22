
public class Order {

	private int OrderNum;
	private Call CallDetails;
	private double TotalPrice;
	private double Distance;
	private static int counter = 0;
	
	// ----------------------------------------------------------------------------------------------------------------

	public Order(Call call_deatails, double totalPrice) { // constructor Order
		synchronized(this) {
		this.OrderNum = UpdateOrderNum(counter); // updates the order number
		}
		this.CallDetails = call_deatails;
		this.TotalPrice = totalPrice;
		this.Distance = 0;	
	}
	
	// ----------------------------------------------------------------------------------------------------------------

	private synchronized static int UpdateOrderNum(int or_num) { // updates the order number
		if (counter == 0) {
			counter++;
			return 1;
		} else {
			counter++; // updates the static variable
			or_num++;
			return or_num;
		}
	}

	// ----------------------------------------------------------------------------------------------------------------
	
	public synchronized void setDistance(double curr_dis) { // setter for Distance order
		this.Distance = curr_dis;
	}

	// ----------------------------------------------------------------------------------------------------------------
	
	public double getDistance() { // getter for Distance order
		return this.Distance;
	}

	// ----------------------------------------------------------------------------------------------------------------
	
	public Call getCallDetails() {//getter for the call  details
		return this.CallDetails;
	}
	
	// ----------------------------------------------------------------------------------------------------------------

	public synchronized void PrintMessage() {//print "new order"
		System.out.println("New Order Arrived: " + this.OrderNum);
	}

	// ----------------------------------------------------------------------------------------------------------------
	
	public double getTotalPrice() {//getter for total price of the order
		return this.TotalPrice;
	}		
}