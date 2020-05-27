/*
 * LinkedList<E>
 * A generic data structure that is implemented using a circular doubly-linked list with a sentinel node,
 * and does not accept null elements
 * Author: Prajna Sariputra
 * Student Number: 3273420
 * Course: SENG2200
 * E-mail address: c3273420@uon.edu.au
 */

//append started 2:37PM 2 April, done 2:41PM 2 April
package main.java.coupling.tests;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedList<E> implements Iterable<E> {
	protected Node<E> sentinel;
	protected int size;
	protected int modCount;
	
	/*
	 * Constructor
	 * 
	 * Preconditions:
	 * None
	 * Postconditions:
	 * An empty LinkedList object is created and its member variables initialized to defaults
	 */
	public LinkedList() {
		sentinel = new Node<E>();
		sentinel.setNext(sentinel);
		sentinel.setPrev(sentinel);
		size = 0;
		modCount = 0;
	}
	
	/*
	 * Preconditions:
	 * The element to be inserted must not be null, and all member variables have been initialized properly
	 * Postconditions:
	 * The element is added to the start of the LinkedList
	 */
	public void prepend(E element) {
		if (element == null) { //do not allow null
			throw new IllegalArgumentException("Input element must not be null.");
		}
		Node<E> oldHead = sentinel.getNext();
		Node<E> newHead = new Node<E>();
		newHead.setData(element);
		newHead.setPrev(sentinel);
		newHead.setNext(oldHead);
		sentinel.setNext(newHead);
		oldHead.setPrev(newHead);
		size++;
		modCount++;
	}
	
	/*
	 * Preconditions:
	 * The element to be inserted must not be null, and all member variables have been initialized properly
	 * Postconditions:
	 * The element is added to the end of the LinkedList
	 */
	public void append(E element) {
		if (element == null) {
			throw new IllegalArgumentException("Input element must not be null.");
		}
		Node<E> oldTail = sentinel.getPrev();
		Node<E> newTail = new Node<E>();
		newTail.setData(element);
		newTail.setPrev(oldTail);
		newTail.setNext(sentinel);
		oldTail.setNext(newTail);
		sentinel.setPrev(newTail);
		size++;
		modCount++;
	}
	
	/*
	 * Preconditions:
	 * All member variables have been initialized properly
	 * Postconditions:
	 * The element at the start of the list is removed and returned if there is one,
	 * otherwise null is returned
	 */
	public E removeHead() {
		if (size != 0) {
			Node<E> toBeRemoved = sentinel.getNext();
			Node<E> newHead = toBeRemoved.getNext();
			E data = toBeRemoved.getData();
			sentinel.setNext(newHead);
			newHead.setPrev(sentinel);
			size--;
			modCount++;
			return data;
		}
		else {
			return null;
		}
	}
	
	/*
	 * An implementation of an Iterator<E> for this LinkedList
	 */
	private class LLIterator implements Iterator<E> {
		private Node<E> current;
		private int iterModCount;
		
		/*
		 * Constructor, accessible only to LinkedList
		 */
		private LLIterator() {
			current = sentinel;
			iterModCount = modCount;
		}
		
		/*
		 * Preconditions:
		 * The current and sentinel member variables have been initialized properly
		 * Postconditions:
		 * Returns true if there is an element after the iterator's current position in the LinkedList
		 */
		@Override
		public boolean hasNext() {
			if (current.getNext() != sentinel) {
				return true;
			}
			else {
				return false;
			}
		}

		/*
		 * Preconditions:
		 * All member variables have been initialized properly,
		 * hasNext() returns true, and no mutators outside this Iterator instance has been called
		 * Postconditions:
		 * The iterator's position is moved forward one step, and the element the iterator is now on is returned
		 */
		@Override
		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			if (iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}
			current = current.getNext();
			return current.getData();
		}
		
		/*
		 * This method is unsupported by this iterator, so this is simply a stub
		 * 
		 * Preconditions:
		 * None
		 * Postconditions:
		 * An UnsupportedOperationException is thrown
		 */
		@Override
		public void remove() {
			throw new UnsupportedOperationException("remove() is unsupported by this iterator.");
		}
		
	}
	
	/*
	 * Preconditions:
	 * All member variables have been initialized properly
	 * Postconditions:
	 * A new object which implements the Iterator<E> interface is returned
	 */
	@Override
	public Iterator<E> iterator() {
		return new LLIterator();
	}

}
