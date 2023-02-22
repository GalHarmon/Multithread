import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

public class Pizzeria {


	private String File;
	private int NumOfDelivreyGuy;
	private double KWtime;
	protected Queue<Call> callLine;
	protected Queue<Call> CallLineByManager;
	protected Queue<Order> OrderLine;
	protected BoundedQueue<PizzaDelivery> DeliveryLine;
	protected int numTotalCalls;
	protected int CounterDelivereis;
	
// ----------------------------------------------------------------------------------------------------------------

	public Pizzeria(String file, int num_of_delivrey_guy, double kwtime) throws FileNotFoundException {//pizzeria constructor, input from gui
		this.File = file;
		this.NumOfDelivreyGuy = num_of_delivrey_guy;
		this.KWtime = kwtime;
		this.callLine = new Queue<Call>();
		this.CallLineByManager = new Queue<Call>();
		this.OrderLine = new Queue<Order>();
		this.DeliveryLine = new BoundedQueue<PizzaDelivery>(12);
		this.CounterDelivereis = 0;
		this.numTotalCalls = 0;
		this.startRunning(file);
	}

// ----------------------------------------------------------------------------------------------------------------

	private void startRunning(String File) throws FileNotFoundException {//function that creat the all program
		Vector<Employee> AllEmployees = new Vector<Employee>();
		InformationSystem S = new InformationSystem();
		Queue<Call> callVec = ReadFromFile(File, this);
		this.setTotalCalls(callVec.size());//set the num of all the calls that supposed to come
		CreateCallsThread(callVec);
		Manager M = CreateManagerThread(this, S, AllEmployees);
		this.CreateAllThreads(S, M, AllEmployees);
		M.setAllEmployeesVec(AllEmployees);
	}

// ----------------------------------------------------------------------------------------------------------------
	
	private static Queue<Call> ReadFromFile(String file, Pizzeria P) throws FileNotFoundException { // function that
		// read from a file
		// and order the
		// values in an
		// array
		Queue<Call> temp = new Queue<Call>();
		Scanner scan = CreateObjectScanner(file);
		String line;
		while (scan.hasNextLine()) {
			line = scan.nextLine();
			String[] pair = line.split("\t");
			temp.addElement(new Call(pair[0], pair[1], pair[2], pair[3], pair[4], P.callLine));
		}
		return temp;
	}
	
// ----------------------------------------------------------------------------------------------------------------

	
		private static Scanner CreateObjectScanner(String File) throws FileNotFoundException { // returns scanner object
			File fr = new File(File); // create an object File
			Scanner scan = new Scanner(fr);
			scan.nextLine(); // skip the head of the file (the first line)
			return scan;
		}

// ----------------------------------------------------------------------------------------------------------------

		private static void CreateCallsThread(Queue<Call> callvec) {//create call threads from the vector
			for (int i = 0; i < callvec.size(); i++) {
				Thread temp = new Thread(callvec.elementAt(i));
				temp.start();
			}
		}
		
// ----------------------------------------------------------------------------------------------------------------
		
		private static Manager CreateManagerThread(Pizzeria P, InformationSystem S, Vector<Employee> allEmployees) {//create manager thread
			Manager M = new Manager(S, P.callLine, P.CallLineByManager, P.OrderLine, P.DeliveryLine, P.numTotalCalls,
					P.NumOfDelivreyGuy, allEmployees);
			Thread TM = new Thread(M);
			TM.start();
			return M;
		}

// ----------------------------------------------------------------------------------------------------------------
	
		private void CreateAllThreads(InformationSystem S ,Manager M, Vector<Employee> AllEmployees) {//create all threads of worker
			CreateClerkThread(this, AllEmployees);
			CreateSchedulerThread(this, S, AllEmployees);
			CreateKWThread(this, S, AllEmployees);
			CreatePizzaGuyThread(this, M, AllEmployees);
	}


	

	
	// ----------------------------------------------------------------------------------------------------------------
		
		private static void CreateClerkThread(Pizzeria P, Vector<Employee> allEmployees) {//create Clerk thread
			for (int i = 0; i < 3; i++) {
				Clerk C1 = new Clerk(P.callLine, P.CallLineByManager, P.OrderLine, P.numTotalCalls);
				Thread TC1 = new Thread(C1);
				TC1.start();
				allEmployees.addElement(C1);
			}
		}

		// ----------------------------------------------------------------------------------------------------------------
		
		private static void CreateSchedulerThread(Pizzeria P, InformationSystem S, Vector<Employee> allEmployees) {//create Scheduler thread
			for (int i = 0; i < 2; i++) {
				Scheduler S1 = new Scheduler(P.OrderLine, S);
				Thread TS1 = new Thread(S1);
				TS1.start();
				allEmployees.addElement(S1);
			}
		}

// ----------------------------------------------------------------------------------------------------------------
		
		private static void CreateKWThread(Pizzeria P, InformationSystem S, Vector<Employee> allEmployees) {//create KitchenWorker thread
			for (int i = 0; i < 3; i++) {
				KitchenWorker K1 = new KitchenWorker(S, P.DeliveryLine, P.KWtime);
				Thread TK1 = new Thread(K1);
				TK1.start();
				allEmployees.addElement(K1);
			}
		}

// ----------------------------------------------------------------------------------------------------------------
		
		private static void CreatePizzaGuyThread(Pizzeria P, Manager M, Vector<Employee> allEmployees) {//create PizzaGuy thread
			for (int i = 0; i < P.NumOfDelivreyGuy; i++) {
				PizzaGuy temp = new PizzaGuy(P.DeliveryLine, P.CounterDelivereis, P.numTotalCalls, M);
				Thread temp1 = new Thread(temp);
				temp1.start();
				allEmployees.addElement(temp);
			}
		}
		
// ----------------------------------------------------------------------------------------------------------------

		public void setTotalCalls(int Tot_x) {//getter for
			this.numTotalCalls = Tot_x;
		}
	}