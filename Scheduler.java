
public class Scheduler extends EmployeeUpdatesOrder implements Runnable {


		private Queue<Order> OrderLine;
		private InformationSystem IS;

		//----------------------------------------------------------------------------------------------------------------

		public Scheduler(Queue<Order> OrderLine, InformationSystem IS) {  // Scheduler constructor 
			super();
			this.OrderLine = OrderLine;
			this.IS = IS;
		}

		// ----------------------------------------------------------------------------------------------------------------

		public void run() {
			while (true) {
				Order curr_order = this.OrderLine.extract();
				if (curr_order.getDistance() == -1) {// check every iteration if the order with data to "end" the day
					break;
				}
				this.ProccessRun(curr_order);
				curr_order.PrintMessage();//print "new order"
			}
		}
		
		// ----------------------------------------------------------------------------------------------------------------
		
		private void ProccessRun(Order curr_order){
			this.CalcDist(curr_order);//method in class EmployeeUpdatesOrder that calc the distance of the order
			this.Salary += curr_order.getDistance(); 
			SleepTimeSimu((long) (curr_order.getDistance() * 0.25));//simulate the work time of the Scheduler
			IS.setOrdVec(curr_order);//set order in the information system
		}
	}