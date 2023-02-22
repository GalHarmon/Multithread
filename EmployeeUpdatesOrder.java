
public abstract class EmployeeUpdatesOrder extends Employee {

protected InformationSystem InfoSys; 
	
	//----------------------------------------------------------------------------------------------------------------

	public EmployeeUpdatesOrder() {//EmployeeUpdatesOrder constructor
		super();
		this.InfoSys = new InformationSystem();
	}

	//----------------------------------------------------------------------------------------------------------------

	protected static void CalcDist(Order curr_order) {//method that calc distance for orders by address
		String temp_address = curr_order.getCallDetails().getAddress();
		double temp_dist = CountNumOfWords(temp_address) * 1 + DistanceByLetters(temp_address);
		curr_order.setDistance(temp_dist);
	}

	// ----------------------------------------------------------------------------------------------------------------

	private static int CountNumOfWords(String curr_address) {//function that Count Num Of Words in the address
		int counter = 1;
		for (int i = 0; i < curr_address.length(); i++) {
			if (curr_address.charAt(i) == ' ')
				counter++;
		}
		return counter;
	}

	// ----------------------------------------------------------------------------------------------------------------

	private static double DistanceByLetters(String address) {//calc the Distance by the rules
		double sum = 0;
		if (address.charAt(0) >= 'A' && (address.charAt(0) <= 'H'))
				sum += 0.5;
		else if(address.charAt(0) >= 'I' && (address.charAt(0) <= 'P'))
				sum += 2;
		else if(address.charAt(0) >= 'Q' && (address.charAt(0) <= 'Z'))
				sum += 7;		
		else if(address.charAt(0) >= '0' && (address.charAt(0) <= '9'))
				sum += Double.parseDouble(address.substring(0, 1));	
		return sum;
	}
	
	// ----------------------------------------------------------------------------------------------------------------
	
}