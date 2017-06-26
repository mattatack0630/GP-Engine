package utils.dstruct;

import rendering.renderers.Gizmos3D;
import utils.math.Maths;
import utils.math.geom.AABB;
import utils.math.geom.AABBce;
import utils.math.linear.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mjmcc on 3/29/2017.
 */
public class OctTree<E extends OctItem>
{
	private static final Vector3f[] quadSubs = new Vector3f[]{
			new Vector3f(-1, -1, -1),
			new Vector3f(-1, -1, +1),
			new Vector3f(-1, +1, -1),
			new Vector3f(-1, +1, +1),
			new Vector3f(+1, -1, -1),
			new Vector3f(+1, -1, +1),
			new Vector3f(+1, +1, -1),
			new Vector3f(+1, +1, +1),
	};

	private List<E> pendingBuildItems;

	private int minSize;
	private int targetChildren;

	private AABB boundingRegion;
	private List<E> itemList;

	private OctTree<E> parent;
	private List<OctTree<E>> children;

	public OctTree(AABB boundingRegion, OctTree parent, int targetChildren, int minSize)
	{
		this.boundingRegion = boundingRegion;
		this.itemList = new ArrayList<>();

		this.parent = parent;
		this.children = new ArrayList<>();

		this.pendingBuildItems = new ArrayList<>();
		this.targetChildren = targetChildren;

		this.minSize = minSize;
	}

	public void build()
	{
		build(pendingBuildItems);
		pendingBuildItems.clear();
	}

	/**
	 * Recalculate this tree's topology/structure.
	 * Essentially rebuilding the tree, but only for moved items.
	 */
	public void recalcTree()
	{
		// Add all moved items to the pending list
		for (E item : itemList)
			if (item.changedTransform())
				pendingBuildItems.add(item);

		// For each in the pending items list
		for (E item : pendingBuildItems)
		{
			// Remove the item from the this trees items (if there)
			itemList.remove(item);

			// Get the region at this items position
			OctTree tree = getRegion(item.getPosition());

			// Find the nearest parent that completely contains this item
			while (tree != null)
			{
				if (item.contains(tree.boundingRegion))
				{
					// Add to the nearest parents list
					tree.itemList.add(item);
					break;
				}

				// move to next parent
				tree = tree.parent;
			}
		}

		// Clear the pending list
		pendingBuildItems.clear();

		// Make each child recalculate its tree
		for (int i = children.size() - 1; i >= 0; i--)
		{
			OctTree tree = children.get(i);

			tree.recalcTree();

			// prune dead branches
			if (tree.children.isEmpty() && tree.itemList.isEmpty())
				children.remove(i);
		}
	}

	/**
	 * Recalculate the position of this trees items.
	 * Only runs on items which return true on {@link OctItem#changedTransform()}
	 */
	public void recalcItems()
	{
		// Add all moved items to the pending list
		for (E item : itemList)
			if (item.changedTransform())
				pendingBuildItems.add(item);

		// For each in pending items list
		for (int i = pendingBuildItems.size() - 1; i >= 0; i--)
		{
			E item = pendingBuildItems.get(i);

			OctTree<E> currTree = this;

			// recursively search for the items containing tree
			while (!currTree.children.isEmpty())
			{
				boolean found = false;

				for (OctTree<E> tree : currTree.children)
				{
					if (item.contains(tree.boundingRegion))
					{
						currTree = tree;
						found = true;
						break;
					}
				}

				// break if not found in children
				// currTree is the smallest region/tree that contains the item
				if (!found) break;
			}

			// move to proper list
			if (currTree != this)
			{
				currTree.itemList.add(item);
				this.itemList.remove(item);
			}
		}

		// Make each child branch recalculate its items
		for (OctTree tree : children)
			tree.recalcItems();

		// clear pending items
		pendingBuildItems.clear();
	}

	/**
	 * Split the tree into its 8 octanes
	 * <p>
	 * This method does not set the children nodes, instead it just generates them and returns
	 * a list containing the trees.
	 *
	 * @return a list containing the splits
	 */
	private List<OctTree> getSplit()
	{
		Vector3f center = boundingRegion.getCenter();
		Vector3f dimensions = boundingRegion.getExtends();
		Vector3f childrenDim = new Vector3f(dimensions).scale(.5f);

		List<OctTree> trees = new ArrayList<>();

		for (int i = 0; i < 8; i++)
		{
			Vector3f childSub = Vector3f.multElements(childrenDim, quadSubs[i], null);
			Vector3f childCenter = Vector3f.add(center, childSub, null);
			AABB childBounds = new AABBce(childCenter, childrenDim);

			OctTree childTree = new OctTree(childBounds, this, targetChildren, minSize);
			trees.add(childTree);
		}

		return trees;
	}

	/**
	 * Recursively find the smallest region that contains the given point.
	 *
	 * @param pos the point to find the region for
	 * @return the smallest region, as a tree
	 */
	public OctTree getRegion(Vector3f pos)
	{
		Vector3f cen = boundingRegion.getCenter();
		Vector3f unit = Vector3f.sub(pos, cen, null);
		unit.set(Maths.getSign(unit.x()), Maths.getSign(unit.y()), Maths.getSign(unit.z()));

		OctTree t = null;

		for (OctTree tree : children)
		{
			Vector3f c = Vector3f.sub(tree.boundingRegion.getCenter(), cen, null);
			c.set(Maths.getSign(c.x()), Maths.getSign(c.y()), Maths.getSign(c.z()));

			if (c.equals(unit))
			{
				t = tree;
				break;
			}
		}

		return (t == null) ? this : t.getRegion(pos);
	}

	private List<E> build(List<E> items)
	{
		List<E> itemsAdded = new ArrayList<>();

		for (E item : items)
			if (item.contains(boundingRegion))
				itemsAdded.add(item);

		itemList.addAll(itemsAdded);

		Vector3f dim = new Vector3f(boundingRegion.getExtends()).scale(2.0f);

		if (dim.x() >= minSize && dim.y() >= minSize && dim.z() >= minSize && itemList.size() > targetChildren)
		{
			List<OctTree> trees = getSplit();

			for (OctTree tree : trees)
			{
				List<OctItem> childAdded = tree.build(itemsAdded);

				if (!childAdded.isEmpty())
				{
					itemList.removeAll(childAdded);
					children.add(tree);
				}
			}
		}

		return itemsAdded;
	}

	public List<E> getDecedentItems()
	{
		List<E> decendents = new ArrayList<>();
		decendents.addAll(itemList);

		for (OctTree<E> child : children)
			decendents.addAll(child.getDecedentItems());

		return decendents;
	}

	public void clear()
	{
		itemList.clear();
		children.clear();
	}

	public List<E> getItems()
	{
		return itemList;
	}

	public List<OctTree<E>> getChildren()
	{
		return children;
	}

	public AABB getBoundingRegion()
	{
		return boundingRegion;
	}

	public void insert(E item)
	{
		pendingBuildItems.add(item);
	}

	public void insertAll(List<E> items)
	{
		pendingBuildItems.addAll(items);
	}

	public void remove(E item)
	{
		if (itemList.contains(item))
		{
			itemList.remove(item);
		} else
		{
			for (OctTree<E> child : children)
			{
				child.remove(item);
			}
		}
	}

	public void removeAll(List<E> items)
	{
		for(E item : items)
			remove(item);
	}

	/////TESTS///////

	public void tRender()
	{
		Gizmos3D.drawWireAABB(boundingRegion.getMin(), boundingRegion.getMax(), 1f);

		for (OctTree t : children)
			if (t != null)
				t.tRender();
	}

	public void testPrint()
	{
		System.out.print(boundingRegion.getExtends().x() + " -> ");
		for (OctTree t : children)
			if (t != null)
				System.out.print(t.boundingRegion.getExtends().x() + ", ");

		System.out.print(" : " + itemList.size() + "\n");

		for (OctTree t : children)
			if (t != null)
				t.testPrint();
	}
}
