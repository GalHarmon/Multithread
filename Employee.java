import java.util.Random;

public abstract class Employee {

	protected String name;
	protected double Salary = 0;

	// ----------------------------------------------------------------------------------------------------------------
	
	public Employee() {//Employee constructor
		this.name = setNameRandomly();
	}

	// ----------------------------------------------------------------------------------------------------------------

	private static String setNameRandomly() { // sets randomly the name to all objects that need one
		Random random = new Random();
		StringBuffer r = new StringBuffer(3);
		for (int i = 0; i < 3; i++) {
			int nextRandomChar = 97 + (int) (random.nextFloat() * (122 - 97 + 1));
			r.append((char) nextRandomChar);
		}
		return r.toString();
	}

	// ----------------------------------------------------------------------------------------------------------------

	protected static void SleepTimeSimu(long time) {//method that simulate the sleep time of the Employee
		try {
			Thread.sleep(time * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// ----------------------------------------------------------------------------------------------------------------
	
	public String getName() {//getter for the Employee name
		return this.name;
	}

	//----------------------------------------------------------------------------------------------------------------
	
	public double getSalary() {// getter for the Employee salary
		return this.Salary;
	}
	
	// ----------------------------------------------------------------------------------------------------------------s		
}