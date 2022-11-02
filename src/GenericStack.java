import java.util.ArrayList;
import java.util.EmptyStackException;

// TODO: Auto-generated Javadoc
/**
 * The Class GenericStack. Implements a generic software stack for any element
 * IMPORTANT: You need to replace with YOUR GenericStack Implementation!!!
 *
 *
 * @param <E> the element type
 */
public class GenericStack<E>  {
	
	/** The stack.  The stack will be built on a generic ArrayList, but will only
	 *  expose stack methods push, pop, peek, isEmpty and getSize.
	 */
	private ArrayList<E> stack;
	
	/**
	 * Instantiates a new generic stack. The stack is empty at the beginning
	 */
	public GenericStack() {
		stack = new ArrayList<>();
	}
	
	/**
	 * My implementation
	 * a) empty() - returns true if the element is empty
	 * b) size() - returns the size of the Stack
	 * c) peek() - returns the object that is at the top of the stack. Must throw
	 *             appropriate exception if attempt to peek empty stack
	 * d) pop() - gets the object at the top of stack, then removes it from 
	 *            the stack and returns the object. Must throw appropriate exception if
	 *            attempt to pop from empty stack.
	 * e) push(o) - adds the object to the top of stack/
	 * 
	 */
	public boolean empty() {
		return stack.size() == 0;
	}
	
	public int size() {
		return stack.size();
	}
	
	public E peek() {
		if (empty()) {
			throw new EmptyStackException();
		}
		return stack.get(size() - 1);
	}
	
	public E pop() {
		if (empty()) {
			throw new EmptyStackException();
		}
		E o = peek();
		stack.remove(size() - 1);
		return o;
	}
	
	public void push(E o) {
		stack.add((E)o);
	}
	
	/**
	 * 	 * To string
	 * 	 	 *
	 * 	 	 	 * @return the string
	 * 	 	 	 	 */
	@Override
   	public String toString() {
	   return("stack: "+stack.toString());
	}
}
