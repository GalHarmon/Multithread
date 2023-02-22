import java.util.Vector;

public class InformationSystem {

	protected Vector<Order> SmalldisOrdersVec;//divide the order to 3 different vector
	protected Vector<Order> MediumdisOrdersVec;
	protected Vector<Order> LargedisOrdersVec;

	// ----------------------------------------------------------------------------------------------------------------

	public InformationSystem() { // information system constructor
		this.SmalldisOrdersVec = new Vector<Order>();
		this.MediumdisOrdersVec = new Vector<Order>();
		this.LargedisOrdersVec = new Vector<Order>();
	}

	// ----------------------------------------------------------------------------------------------------------------
	
	public synchronized void setOrdVec(Order curr_ord) {// set the orders in the match vector by distance
		if (curr_ord.getDistance() <= 3) {
			SmalldisOrdersVec.addElement(curr_ord);
			notifyAll();
		} else if (curr_ord.getDistance() > 3 && curr_ord.getDistance() <= 8) {
			MediumdisOrdersVec.addElement(curr_ord);
			notifyAll();
		} else if (curr_ord.getDistance() > 8) {
			LargedisOrdersVec.addElement(curr_ord);
			notifyAll();
		}
	}

	// ----------------------------------------------------------------------------------------------------------------
	
	public synchronized Order getOrd() {//get the order by the rule from the instructions
		Order temp = null;
		while (temp == null) {
			if (!SmalldisOrdersVec.isEmpty()) {
				temp = SmalldisOrdersVec.remove(0);
			} else if (!MediumdisOrdersVec.isEmpty()) {
				temp = MediumdisOrdersVec.remove(0);
			} else if (!LargedisOrdersVec.isEmpty()) {
				temp = LargedisOrdersVec.remove(0);
			} else if (temp == null) {
				this.WaitCondition();
			}
		}
		return temp;
	}

	// ----------------------------------------------------------------------------------------------------------------
	
	public void WaitCondition() {//wait condition while all the vectors are empty and the kitchen worker can't do "get"
		try {
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}