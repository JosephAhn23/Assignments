/**
 * @author Marcel Turcotte, Guy-Vincent Jourdan and Mehrdad Sabetzadeh
 *         (University of Ottawa)
 * 
 * 
 */

// TODO:
// 1) keep track of size and implement a size() method
// 2) write a peek method

public class LinkedQueue<D> implements Queue<D> {

	private static class Elem<T> {
		private T value;
		private Elem<T> next;

		private Elem(T value, Elem<T> next) {
			this.value = value;
			this.next = next;
		}
	}
	private Elem<D> front;
	private Elem<D> rear;

	public LinkedQueue() {
		front = rear = null;
	}

	public boolean isEmpty() {
		return front == null;
	}

	public void enqueue(D newElement) {

		if (newElement == null) {
			throw new NullPointerException("no null object in my queue !");
			// could have been IllegalArgumentException but NPE seems
			// to be the norm
		}

		Elem<D> newElem;
		newElem = new Elem<D>(newElement, null);
		if (isEmpty()) {
			front = newElem;
			rear = newElem;
		} else {
			rear.next = newElem;
			rear = newElem;
		}
	}

	public D dequeue() {

		if (isEmpty()) {
			throw new IllegalStateException("Empty queue");
		}

		D val;
		val = front.value;

		if (front.next == null) {
			front = rear = null;
		} else {
			front = front.next;
		}

		return val;
	}

	public D peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Empty queue");
        }
        return front.value;
    }

	public int size() {
		int count = 0;
		Elem<D> current=front;
		while (current != null) {
			count++;
			current = current.next;
		}
		return count;
	}
	
	

	public String toString() {

		StringBuffer returnedValue = new StringBuffer("[");

		if (!isEmpty()) {
			Elem<D> cursor = front;
			returnedValue.append(cursor.value);
			while (cursor.next != null) {
				cursor = cursor.next;
				returnedValue.append(", " + cursor.value);
			}
		}

		returnedValue.append("]");
		return returnedValue.toString();

	}


	
}
