
public class Call implements Runnable {

	private int amountPizzas;
	private String address;
	private int creditCardNum;
	private double ArriveTime;
	private double callTime;
	protected boolean EndCall;
	private Queue<Call> callLine;

	// ----------------------------------------------------------------------------------------------------------------

	public Call(String credit_card_number, String amount_pizzas, String arrive_time, String call_time, String address,
			Queue<Call> callLine) { // Call constructor that gets all the data from an external file
		this.amountPizzas = Integer.parseInt(amount_pizzas); // cast to Integer from String
		this.address = address;
		this.creditCardNum = Integer.parseInt(credit_card_number);
		this.ArriveTime = Double.parseDouble(arrive_time);
		this.callTime = Double.parseDouble(call_time);
		this.callLine = callLine;
		this.EndCall = false;
	}
	
	// ----------------------------------------------------------------------------------------------------------------

	public int getAmountPizzas() { // getter for the amount of pizzas
		return this.amountPizzas;
	}

	// ----------------------------------------------------------------------------------------------------------------
	
	public void run() {
		while (true) {
			try {
				Thread.sleep((long) this.ArriveTime); // sleeps throughout time of call arrival
				callLine.insert(this); // inserts to the call line the current call
				synchronized (this) {
					while (!EndCall) {
						try {
							this.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					if (EndCall) {//when the manager or clerk end the call-finish the thread
						break;
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	// ----------------------------------------------------------------------------------------------------------------
	
	public double getCallTime() { // getter for the Call time
		return this.callTime;
	}

	// ----------------------------------------------------------------------------------------------------------------
	
	public String getAddress() { // getter for the address of the delivery
		return this.address;
	}

	// ----------------------------------------------------------------------------------------------------------------
	
	public int getCreditCardNum() { // getter for the cred num
		return this.creditCardNum;
	}

	// ----------------------------------------------------------------------------------------------------------------
	
	private void UpdateEndCall() { // change the boolean variable to true
		this.EndCall = true;
	}

	// ----------------------------------------------------------------------------------------------------------------
	
	public synchronized void CallEnd() { // method that cause to end call
		this.UpdateEndCall();
		this.notifyAll();
	}
}