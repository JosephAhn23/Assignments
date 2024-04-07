import java.util.Iterator;
import java.util.NoSuchElementException;

public class SinglyLinkedList<E> implements List<E>, Iterable<E> {
    public int count(E fromElement, E toElement) {
        if (fromElement == null) {
            return 0;
        } else {
            int totalCount = 0;
            Node<E> current = this.head;
            return numOfCounts(current, fromElement, toElement, totalCount);
        }
    }
    
    private static class Node<T> {
        private T value;
        private Node<T> next;

        private Node(T value, Node<T> next) {
            this.value = value;
            this.next = next;
        }
    }

    private Node<E> head;
    private int size;
    //private numOfCounts for count method.
    private int numOfCounts(Node<E> node, E fromElement, E toElement, int count) {
        if (node.value.equals(fromElement) && node.next != null) {
            fromElement = node.next.value;
            count++;
            if (node.value.equals(toElement)) {
                return count;
            }
            else if (!node.value.equals(toElement) && (node.next == null)) {
                return count + 1;
            }
            else if (node.next.value.equals(toElement)) {
                return count+1;
            }
        }
        boolean returningZero=!node.value.equals(fromElement) && node.next == null;
        if (returningZero) {
            return 0;
        } else if (node.next != null) {
            return numOfCounts(node.next, fromElement, toElement, count);
        }
        return count+1;
    }
    
    public SinglyLinkedList() {
        head = null;
        size = 0;
    }
    
    public void add(E element) {
        if (head == null) {
            head = new Node<>(element, null);
        } else {
            Node<E> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = new Node<>(element, null);
        }
        size++;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<E> current = head;
        while (current != null) {
            sb.append(current.value);
            if (current.next != null) {
                sb.append(" ");
            }
            current = current.next;
        }
        sb.append("]");
        return sb.toString();
    }
    public Iterator<E> iterator() {
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements Iterator<E> {
        private Node<E> currentIterator;

        public LinkedListIterator() {
            currentIterator = head;
        }

        public boolean hasNext() {
            return currentIterator != null;
        }

        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            E value = currentIterator.value;
            currentIterator = currentIterator.next;
            return value;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public void addFirst(E element) {
        head = new Node<>(element, head);
        size++;
    }

    public void add(int index, E element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        if (index == 0) {
            addFirst(element);
        } else {
            Node<E> current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            current.next = new Node<>(element, current.next);
            size++;
        }
    }

    public E removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("List is empty");
        }
        E removedValue = head.value;
        head = head.next;
        size--;
        return removedValue;
    }

    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if (index == 0) {
            return removeFirst();
        } else {
            Node<E> current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            E removedValue = current.next.value;
            current.next = current.next.next;
            size--;
            return removedValue;
        }
    }

    public boolean remove(E element) {
        if (isEmpty()) {
            return false;
        }
        if (head.value.equals(element)) {
            head = head.next;
            size--;
            return true;
        } else {
            Node<E> current = head;
            while (current.next != null && !current.next.value.equals(element)) {
                current = current.next;
            }
            if (current.next != null) {
                current.next = current.next.next;
                size--;
                return true;
            }
            return false;
        }
    }

    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Node<E> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.value;
    }

    public E set(int index, E element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Node<E> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        E oldValue = current.value;
        current.value = element;
        return oldValue;
    }

    public boolean contains(E element) {
        Node<E> current = head;
        while (current != null) {
            if (current.value.equals(element)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }
}
