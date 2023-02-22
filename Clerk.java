import java.util.Random;

public class Clerk extends EmployeeUpdatesOrder implements Runnable {

	private int numTotalCalls;
	private Queue<Call> callLine;
	private Queue<Call> CallLineByManager;
	private Queue<Order> OrderLine;
	private double sumOfTotalPrices; // from the call that the clerk took care, sum the prices of the order, for sum the income later
	private static int CallsDoneByClerk = 0; //static variable to sum the calls that the clerk already done

	// ----------------------------------------------------------------------------------------------------------------
	public Clerk(Queue<Call> callLine, Queue<Call> CallLineByManager, Queue<Order> OrderLine, int numTotalCalls) { // Clerk
																													// constructor
		super();
		this.callLine = callLine;
		this.CallLineByManager = CallLineByManager;
		this.OrderLine = OrderLine;
		this.numTotalCalls = numTotalCalls;
		this.sumOfTotalPrices = 0;
	}
	// ----------------------------------------------------------------------------------------------------------------

	public void run() {
		while (CallsDoneByClerk < numTotalCalls) {
			Call incomeCall = this.callLine.extract();
			CallsDoneByClerk++; // update the amount of the calls that done
			if (incomeCall.getAmountPizzas() < 10) { //if the clerk allows to take care in the order
				if (incomeCall.getAmountPizzas() == -1) { // check every iteration if the order is Unreal to "end" the day
					break;
				}
				this.ProccessCondition(incomeCall); //method that do all the action after extract call
			} else {
				SleepTimeSimu((long) 0.5);
				this.CallLineByManager.insert(incomeCall);
			}
			if (CallsDoneByClerk == this.numTotalCalls) {
				break;
			}
		}
	}

	// ----------------------------------------------------------------------------------------------------------------
	private synchronized static Order CreateOrder(Call income_call) { // creates new order object
		Order temp_or = new Order(income_call, UpdateTotalPriceOfPizzas(income_call));
		return temp_or;
	}

	// ----------------------------------------------------------------------------------------------------------------
	private static int UpdateTotalPriceOfPizzas(Call call_deatails) { // returns the total price to all pizzas
		return (call_deatails.getAmountPizzas() * 25);
	}

	// ----------------------------------------------------------------------------------------------------------------
	private synchronized void InputToCallLine() { //when we want to insert call with data to end the day
		for (int i = 0; i < 2; i++) {
			Call temp = new Call("1", "-1", "1", "1", "F", this.callLine);
			this.callLine.insert(temp);
		}
	}

	// ----------------------------------------------------------------------------------------------------------------
	public double getsumOfTotalPrices() { // getter to the field of sumOfTotalPrices
		return this.sumOfTotalPrices;
	}

	// ----------------------------------------------------------------------------------------------------------------
	private synchronized void ProccessCondition(Call incomeCall) {//method that do all the action after extract call
		SleepTimeSimu((long) incomeCall.getCallTime());
		Order order_by_clerk = CreateOrder(incomeCall);
		this.sumOfTotalPrices += order_by_clerk.getTotalPrice();
		this.Salary += 2;
		this.OrderLine.insert(order_by_clerk);
		incomeCall.CallEnd();
	}
}