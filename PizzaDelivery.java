
public class PizzaDelivery {
	private Order currOrder;
	private BoundedQueue<PizzaDelivery> DeliveryLine;

	// ----------------------------------------------------------------------------------------------------------------

	public PizzaDelivery(Order curr_Order, BoundedQueue<PizzaDelivery> delivery_line) { //constructor for PizzaDelivery
			this.currOrder = curr_Order;
			this.DeliveryLine = delivery_line;
	}
	

	// ----------------------------------------------------------------------------------------------------------------
	
	public Order getOrder() {//getter for 
		return this.currOrder;
	}

	// ----------------------------------------------------------------------------------------------------------------

}
