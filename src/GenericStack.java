import java.util.ArrayList;
import java.util.EmptyStackException;

// TODO: Auto-generated Javadoc
/**
 * The Class GenericStack. Implements a generic software stack for any element
 * IMPORTANT: You ned to replace with YOUR GenericStack Implementation!!!
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
	

}
