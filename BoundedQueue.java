import java.util.Vector;

public class BoundedQueue<T> {

	private Vector<T> buffer;
	private int maxSize;
	
// ----------------------------------------------------------------------------------------------------------------

	public BoundedQueue(int maxSize) {// BoundedQueue constructor
		buffer = new Vector<T>();
		this.maxSize = maxSize;
	}
	
// ----------------------------------------------------------------------------------------------------------------

	public synchronized void insert(T item)  {
		while (buffer.size() >= maxSize)// to avoid more object that the max in the Queue 
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		buffer.add(item);
		notifyAll();
	}
	
// ----------------------------------------------------------------------------------------------------------------

	public synchronized T extract()  {//method to extract object from the Queue
		while (buffer.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		T item = buffer.elementAt(0);
		buffer.remove(item);
		notifyAll();
		return item;
		
	}
	
// ----------------------------------------------------------------------------------------------------------------
	
	public synchronized boolean isEmpty() {//check if the Queue is empty
		if(buffer.isEmpty())
			return true;
		return false;
	}
}