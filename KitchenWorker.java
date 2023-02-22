
public class KitchenWorker extends Employee implements Runnable {

	private InformationSystem IS;
	private double WorkTime;
	private BoundedQueue<PizzaDelivery> DeliveryLine;

	// ----------------------------------------------------------------------------------------------------------------

	public KitchenWorker(InformationSystem IS, BoundedQueue<PizzaDelivery> DeliveryLine, double WorkTime) {//KW constructor
		super();
		this.DeliveryLine = DeliveryLine;
		this.IS = IS;
		this.WorkTime = WorkTime;
	}

	// ----------------------------------------------------------------------------------------------------------------
	
	public void run() {
		while (true) {
			Order curr_ord = IS.getOrd();
			if (curr_ord.getDistance() == -1) {//if the thread get "Unreal" order
				break;
			}
			this.Salary += 2 * (curr_ord.getCallDetails().getAmountPizzas());
			PrintPross(curr_ord, this);
			PizzaDelivery temp = CreatePizzaDelivery(curr_ord, this.DeliveryLine);
			SleepTimeSimu((long) (this.WorkTime * curr_ord.getCallDetails().getAmountPizzas()));
			this.DeliveryLine.insert(temp);
		}
	}

	// ----------------------------------------------------------------------------------------------------------------

	private synchronized static void PrintPross(Order curr_ord, KitchenWorker K) {//print the ditails
		System.out.println("Kitchen worker name: " + K.getName() + ", Kitchen worker Salary: " + K.getSalary());
		System.out.println("Order Details: " + " Pizzas: " + curr_ord.getCallDetails().getAmountPizzas() + ", Price: "
				+ curr_ord.getTotalPrice() + ", Distance: " + curr_ord.getDistance() + ", Address: "
				+ curr_ord.getCallDetails().getAddress() + ", Cred: " + curr_ord.getCallDetails().getCreditCardNum());
	}

	// ----------------------------------------------------------------------------------------------------------------

	private static PizzaDelivery CreatePizzaDelivery(Order curr_Order, BoundedQueue<PizzaDelivery> delivery_line) {//create object of delivery
		PizzaDelivery curr_delivery = new PizzaDelivery(curr_Order, delivery_line);
		return curr_delivery;
	}
}