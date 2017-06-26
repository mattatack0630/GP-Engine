package utils.dstruct;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * Created by mjmcc on 3/31/2017.
 */
public class BinaryTree<E extends Comparable> implements Iterator<E>, Iterable<E>
{
	private BinaryTreeNode<E> root;
	private BinaryTreeNode<E> previous;
	private BinaryTreeNode<E> current;
	private int size;


	private Stack<BinaryTreeNode> stack = new Stack<>();
	private int currElementSize;
	private int currElement;
	public BinaryTree()
	{
		this.currElementSize = 0;
		this.currElement = 0;
		this.size = 0;
	}

	public void balance()
	{
	}

	public List<E> searchFor(E element)
	{

		return root.searchFor(element).elementContainer;
	}

	public void insert(E element)
	{
		if(root == null)
			current = root = new BinaryTreeNode<E>(null, element);
		else
			root.insert(new BinaryTreeNode<E>(root, element));

		size++;
	}

	public void remove(E element)
	{
		root.remove(element);
		size--;
	}

	@Override
	public E next()
	{
		if (currElement > currElementSize - 1)
		{
			while (current != null)
			{
				stack.push(current);
				current = current.left;
			}

			current = stack.pop();
			BinaryTreeNode<E> node = current;
			current = current.right;

			previous = node;
			currElement = 0;
			currElementSize = node != null ? node.elementContainer.size() : 0;
		}

		return previous.elementContainer.get(currElement++);
	}

	@Override
	public boolean hasNext()
	{
		boolean hasNext = (!stack.isEmpty() || current != null);

		if (!hasNext)
			current = root;

		return hasNext;
	}

	@Override
	public Iterator<E> iterator()
	{

		return this;
	}

	public int size()
	{
		return size;
	}
}
