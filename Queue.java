import java.util.*;

public class Queue<T> {

	private Vector<T> buffer;

	// ----------------------------------------------------------------------------------------------------------------

	public Queue() {//Queue constructor
		buffer = new Vector<T>();
	}
	// ----------------------------------------------------------------------------------------------------------------

	public synchronized void insert(T item) {//insert function
		buffer.add(item);
		this.notify();
	}

	// ----------------------------------------------------------------------------------------------------------------

	public synchronized T extract() {//extract function
		while (buffer.isEmpty()) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		T t = buffer.elementAt(0);
		buffer.remove(t);
		return t;
	}

	// ----------------------------------------------------------------------------------------------------------------
	
	public synchronized boolean isEmpty() {//function to check if the Queue is empty 
		if (buffer.isEmpty()) {
			return true;
		}
		return false;
	}

	// ----------------------------------------------------------------------------------------------------------------
	
	public int size() {//return the size of the Queue
		return buffer.size();
	}
	
	// ----------------------------------------------------------------------------------------------------------------

	public void addElement(T t) {//add to Queue
		buffer.addElement(t);

	}
	// ----------------------------------------------------------------------------------------------------------------

	public T elementAt(int i) {//return the element in the index
		return buffer.elementAt(i);
	}

	// ----------------------------------------------------------------------------------------------------------------
}