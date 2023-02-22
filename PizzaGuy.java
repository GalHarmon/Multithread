import java.util.Random;
import java.util.Vector;

public class PizzaGuy extends Employee implements Runnable {
	private int PizzaGuyCapacity;
	private int PizzaGuyNumOfDeliv;
	private static int CounterDelivereis = 0;
	private int TotalDelivereis;
	private BoundedQueue<PizzaDelivery> DeliveryLine;
	private Manager manager;

// ----------------------------------------------------------------------------------------------------------------
	
	public PizzaGuy(BoundedQueue<PizzaDelivery> DeliveryLine, int counter_delivreis, int total_deliveries,
			Manager manager) {//PizzaGuy constructor
		super();
		this.PizzaGuyCapacity = randInt(2,4);
		this.PizzaGuyNumOfDeliv = 0;
		this.DeliveryLine = DeliveryLine;
		this.TotalDelivereis = total_deliveries;
		this.manager = manager;
	}

// ----------------------------------------------------------------------------------------------------------------
	
	private static int CalcTip() {//calc random tip
		int tip = randInt(0, 15);
		return tip;
	}
	
// ----------------------------------------------------------------------------------------------------------------

	private static int randInt(int min, int max) {//get int between range
		Random rand = new Random();
		int random_num = rand.nextInt((max - min) + 1) + min;
		return random_num;
	}

// ----------------------------------------------------------------------------------------------------------------

	public void run() {
		while (true) {
			if (this.manager.getlessthanten()) {
				this.PizzaGuyCapacity = 1;//if left less then 10 orders, change capacity
				while (CounterDelivereis <= this.TotalDelivereis) {
					PizzaDelivery curr_delivery = ProcceessExtract(this.DeliveryLine, this);
					if (CounterDelivereis == TotalDelivereis) {
						this.CreateDefectiveCallForManager();
					}
					if (curr_delivery.getOrder().getDistance() == -1) {//if the call "defective"
						break;
					}
					this.sleepTime(curr_delivery.getOrder().getDistance(), 1); //method that sends this thread to sleep
					synchronized (this) {
						this.manager.UpdateTotalDeliveriesDone(this.PizzaGuyCapacity); 
					}
				}
			}
			if (this.ProcceessExtractIfCapacity()) {
				break;
			}
			this.manager.UpdateTotalDeliveriesDone(this.PizzaGuyCapacity);

		}
	}
	
// ----------------------------------------------------------------------------------------------------------------
	
	private synchronized  void CreateDefectiveCallForManager() {//method for wake the manager
		Call temp = new Call("1", "-1", "1", "1", "F", null);
		this.manager.getQueue().insert(temp);
	}
	
// ----------------------------------------------------------------------------------------------------------------
	
	private synchronized  void sleepTime(double drive, int capacity) {//simulate the sleep of the pizza guy
		SleepTimeSimu((long) drive ); // drive time
		SleepTimeSimu(capacity); // tip time sleep
	}

// ----------------------------------------------------------------------------------------------------------------
	
	private synchronized static PizzaDelivery ProcceessExtract(BoundedQueue<PizzaDelivery> DeliveryLine,
			PizzaGuy curr_guy) {//activities after extract delivery
		PizzaDelivery temp_delivery = DeliveryLine.extract();
		if (temp_delivery.getOrder().getDistance() == -1) {
			return temp_delivery;
		}
		CounterDelivereis++;
		curr_guy.PizzaGuyNumOfDeliv++;
		curr_guy.Salary += CalcSalaryPerDelivery(temp_delivery, curr_guy);
		return temp_delivery;
	}

// ----------------------------------------------------------------------------------------------------------------
	
	private synchronized boolean ProcceessExtractIfCapacity() {//if capacity more than 1
		int cnt = 1; // minimum pizza amount at delivery
		double drive_time = 0;
		while (cnt <= this.PizzaGuyCapacity) {
			PizzaDelivery curr_delivery = ProcceessExtract(this.DeliveryLine, this);
			cnt++;
			if (curr_delivery.getOrder().getDistance() == -1) {//if delivery "defective"
				return true;
			}
			drive_time = curr_delivery.getOrder().getDistance() * 1;
		}
		this.sleepTime(drive_time, this.PizzaGuyCapacity);
		return false;
}

// ----------------------------------------------------------------------------------------------------------------

	private static double CalcSalaryPerDelivery(PizzaDelivery curr_delivery, PizzaGuy curr_guy) {//calc the salary of the pizza guy
		double distance_temp = curr_delivery.getOrder().getDistance();
		return (3 + (4 * distance_temp) + CalcTip());
	}
}