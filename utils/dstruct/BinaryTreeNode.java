package utils.dstruct;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mjmcc on 3/30/2017.
 */
public class BinaryTreeNode<E extends Comparable>
{
	protected E element;
	protected List<E> elementContainer;
	protected BinaryTreeNode left;
	protected BinaryTreeNode right;
	protected BinaryTreeNode parent;

	public BinaryTreeNode(BinaryTreeNode parent, E element)
	{
		this.element = element;
		this.parent = parent;

		this.elementContainer = new ArrayList<>();
		this.elementContainer.add(element);
	}

	public boolean insert(BinaryTreeNode<E> tree)
	{
		int c = tree.element.compareTo(this.element);
		tree.parent = this;

		if (c == -1)
		{
			if (left == null) left = tree;
			else return left.insert(tree);
		}
		if (c == 1)
		{
			if (right == null) right = tree;
			else return right.insert(tree);
		}
		if (c == 0)
		{
			elementContainer.add(tree.element);
			return true;
		}

		return false;
	}

	/**
	 * FIXME tree will be destroyed!
	 * */
	public boolean remove(E element)
	{
		int c = element.compareTo(this.element);

		if (c == -1)
		{
			if (left == null) return false;
			else return left.remove(element);
		}

		if (c == 1)
		{
			if (right == null) return false;
			else return right.remove(element);
		}

		if (c == 0)
		{
			for (int i = elementContainer.size() - 1; i>=0; i--)
			{
				E e = elementContainer.get(i);

				if (e.equals(element))
				{
					elementContainer.remove(i);
					return true;
				}
			}
		}

		// fix tree!!!

		return false;
	}

	public BinaryTreeNode searchFor(E element)
	{
		int c = element.compareTo(this.element);

		if (c == -1 && left != null) return left.searchFor(element);
		if (c == 1 && right != null) return right.searchFor(element);
		if (c == 0) return this;

		return null;
	}
}
