/*
 * Node<T>
 * A generic container object which can be used for a doubly-linked list data structure
 * Author: Prajna Sariputra
 * Student Number: 3273420
 * Course: SENG2200
 * E-mail address: c3273420@uon.edu.au
 */
package main.java.coupling.tests;

//started 2:15PM 2 April
public class Node<T> {
	private Node<T> next, prev;
	private T data;
	
	/*
	 * Constructor
	 * 
	 * Preconditions:
	 * None
	 * Postconditions:
	 * An empty Node object is returned with the member vatriables initialized to null
	 */
	public Node() {
		next = null;
		prev = null;
		data = null;
	}
	
	/*
	 * Preconditions:
	 * The next member variable has been properly initialized
	 * Postconditions:
	 * The value of the next member variable is returned
	 */
	public Node<T> getNext() {
		return next;
	}
	
	/*
	 * Preconditions:
	 * The prev member variable has been properly initialized
	 * Postconditions:
	 * The value of the prev member variable is returned
	 */
	public Node<T> getPrev() {
		return prev;
	}
	
	/*
	 * Preconditions:
	 * The data member variable has been properly initialized
	 * Postconditions:
	 * The data of the next member variable is returned
	 */
	public T getData() {
		return data;
	}
	
	/*
	 * Preconditions:
	 * The parameter refers to either a valid Node object of the same type or null
	 * Postconditions:
	 * The value of the next member variable is set to the parameter
	 */
	public void setNext(Node<T> newNext) {
		next = newNext;
	}
	
	/*
	 * Preconditions:
	 * The parameter refers to either a valid Node object of the same type or null
	 * Postconditions:
	 * The value of the prev member variable is set to the parameter
	 */
	public void setPrev(Node<T> newPrev) {
		prev = newPrev;
	}
	
	/*
	 * Preconditions:
	 * The parameter refers to either a valid object of the correct type or null
	 * Postconditions:
	 * The value of the data member variable is set to the parameter
	 */
	public void setData(T newData) {
		data = newData;
	}
}
