import java.util.Vector;

public class Manager extends EmployeeUpdatesOrder implements Runnable {

	private InformationSystem IS;
	private Queue<Call> callLine;
	private Queue<Call> CallLineByManager;
	private Queue<Order> OrderLine;
	private BoundedQueue<PizzaDelivery> DeliveryLine;
	private boolean lastlessten;
	private int TotalDeliveriesDone;
	private int numTotalCalls;
	private int numOfGuyDelivery;
	private Vector<Employee> AllEmployees;
	private double sumOfTotalPrices; // from the call that the clerk took care, sum the prices of the order, for sum the income later


// ----------------------------------------------------------------------------------------------------------------

	public Manager(InformationSystem Is, Queue<Call> callLine, Queue<Call> CallLineByManager, Queue<Order> OrderLine,
			BoundedQueue<PizzaDelivery> DeliveryLine, int num_total_calls, int num_of_guy_delivery,
			Vector<Employee> AllEmployees) {//manager constructor
		super();
		this.callLine = callLine;
		this.CallLineByManager = CallLineByManager;
		this.DeliveryLine = DeliveryLine;
		this.OrderLine = OrderLine;
		this.IS = Is;
		this.lastlessten = false;
		this.TotalDeliveriesDone = 0;
		this.numTotalCalls = num_total_calls;
		this.numOfGuyDelivery = num_of_guy_delivery;
		this.AllEmployees = (Vector<Employee>) AllEmployees.clone();
		this.sumOfTotalPrices = 0;
	}

// ----------------------------------------------------------------------------------------------------------------

	public void run() {
		if (numTotalCalls <= 10) // to deal with with less than 10 pizzas at beginning
			this.lastlessten = true;
		while (true) {
			while (!this.CallLineByManager.isEmpty()) {
				Call incomeCall = this.CallLineByManager.extract();
				if (incomeCall.getAmountPizzas() == -1) {//if the thread get "defectiveØ order
					break;
				}
				this.createOrderProccess(incomeCall);
			}
			if (numTotalCalls == TotalDeliveriesDone) {
				this.endDayProccess();
				break; 
			}
		}
	}
	
// ----------------------------------------------------------------------------------------------------------------

	private void createOrderProccess(Call incomeCall) { //method that controls creating new order when you get Call
		Order curr_order = CreateOrder(incomeCall);
		this.sumOfTotalPrices += curr_order.getTotalPrice();
		this.CalcDist(curr_order);
		SleepTimeSimu((long) 2);
		IS.setOrdVec(curr_order);
		incomeCall.CallEnd();
		curr_order.PrintMessage();
	}
	
// ----------------------------------------------------------------------------------------------------------------

	private void endDayProccess () {//method that put "unReal" objects in the lines
		this.InputToDeliveryLine();
		this.InputToInfoSys();
		this.InputToOrderLine();
		PrintReportPizzeria(this);
	}

// ----------------------------------------------------------------------------------------------------------------

	private synchronized static Order CreateOrder(Call income_call) {//create object of big order
		Order temp_or = new Order(income_call, UpdateTotalPriceOfPizzas(income_call));
		return temp_or;
	}

// ----------------------------------------------------------------------------------------------------------------

	private static double UpdateTotalPriceOfPizzas(Call call_deatails) { // returns the total price to all pizzas
		if(call_deatails.getAmountPizzas() <= 20) {
			return call_deatails.getAmountPizzas() * 15;
		}
		else {
			return (call_deatails.getAmountPizzas() * 15.0 * 0.9);
		}
	}

// ----------------------------------------------------------------------------------------------------------------
	
	private void InputToDeliveryLine() {//method that put "defective" pizza delivery in the delivery line
		Call temp = new Call("1", "1", "1", "1", "F", this.callLine);
		Order temp_OR = new Order(temp, 0);
		for (int i = 0; i < this.numOfGuyDelivery; i++) {
			this.createDefectivePizzaDelivery(temp_OR);
		}
	}
	
// ----------------------------------------------------------------------------------------------------------------

	private void createDefectivePizzaDelivery(Order temp_OR) {//insert and creat the defective delivery
		PizzaDelivery temp_PD = new PizzaDelivery(temp_OR, this.DeliveryLine);
		temp_PD.getOrder().setDistance(-1);
		this.DeliveryLine.insert(temp_PD);
	}
// ----------------------------------------------------------------------------------------------------------------
	
	private void InputToInfoSys() {//method that put "defective" orders in the IS line
		Call temp = new Call("1", "1", "1", "1", "F", this.callLine);
		for (int i = 0; i < 3; i++) {
			Order temp_OR = new Order(temp, 3);
			temp_OR.setDistance(-1);
			this.IS.setOrdVec(temp_OR);
		}
	}
			
// ----------------------------------------------------------------------------------------------------------------

	private void InputToOrderLine() {//method that put "defective" order in the order line
				Call temp = new Call("1", "1", "1", "1", "F", this.callLine);
				for (int i = 0; i < 2; i++) {
					Order temp_OR = new Order(temp, 0);
					temp_OR.setDistance(-1);
					OrderLine.insert(temp_OR);
				}
			}


// ----------------------------------------------------------------------------------------------------------------

	private static void PrintReportPizzeria(Manager x1) {//print the report at the end of the day
		System.out.println("Total Outcome: " + x1.calcSumOutCome() + "\n" + "Total deliveries: " + x1.numTotalCalls
				+ "\n" + "Total Income: " + x1.CalcSumInCome());
	}

// ----------------------------------------------------------------------------------------------------------------
	
	private double calcSumOutCome() {//calc sum of the employees salary
		double sumOutCome = 0;
		for (int i = 0; i < AllEmployees.size(); i++) {
			sumOutCome += AllEmployees.elementAt(i).getSalary();
		}
		return sumOutCome;
	}

// ----------------------------------------------------------------------------------------------------------------
	
	private double CalcSumInCome() {//calc the daily income of the pizzeria
		double sumInCome = 0;
		for (int i = 0; i < AllEmployees.size(); i++) {
			if (AllEmployees.elementAt(i) instanceof Clerk) {//because only the clerk and manager calc order price
				sumInCome += ((Clerk) AllEmployees.elementAt(i)).getsumOfTotalPrices();
			}
		}
		sumInCome += this.getsumOfTotalPrices();
		return sumInCome;
	}
	
// ----------------------------------------------------------------------------------------------------------------

	public synchronized void UpdateTotalDeliveriesDone(int capacity) {//when pizza guy update the manager that he came back from delivery
		int x= this.TotalDeliveriesDone;
		if (this.numTotalCalls - (x+capacity) <= 10) {
			this.lastlessten = true;
		}
		this.TotalDeliveriesDone += capacity;
	}
		
// ----------------------------------------------------------------------------------------------------------------
	
	public Queue<Call> getQueue() {//getter for the callLine
		return this.callLine;
	}

// ----------------------------------------------------------------------------------------------------------------
	
	public double getsumOfTotalPrices() {//getter for sumOfTotalPrices
		return this.sumOfTotalPrices;
	}
		
// ----------------------------------------------------------------------------------------------------------------
	
	public boolean getlessthanten() {//getter for the boolean variable if there is less then 10 delivery to end the day
		return this.lastlessten;
		}
// ----------------------------------------------------------------------------------------------------------------
	
	public void setAllEmployeesVec(Vector<Employee> temp_emp) {//setter for the Employees vector after we create all the Employees in the Pizzeria
		this.AllEmployees = (Vector<Employee>) temp_emp.clone();
	}
}