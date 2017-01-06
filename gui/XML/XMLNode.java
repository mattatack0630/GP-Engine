package gui.XML;

import java.util.ArrayList;

/**
 * XMLNode Class
 * A data class that stores an element in a XML tree
 */
public class XMLNode
{
	public static final XMLNode FILE_ROOT = new XMLNode("FILE");

	// The parent node, root nodes don't have this
	public XMLNode parent;
	// Children nodes, Leaf nodes don't have this
	public ArrayList<XMLNode> children;
	// List of attributes as Strings
	public ArrayList<String> attribList;
	// The Type of Node (Panel, Button, Component, etc)
	public String type;

	public XMLNode(String innerAttribs)
	{
		this.children = new ArrayList<>();
		this.attribList = new ArrayList<>();

		// Split the Attribute String into single attributes
		String[] tokens = innerAttribs.split("[ ]+(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
		// Set the type to the first attribute String
		type = tokens[0];
		// Add the attributes to a list
		for (int i = 1; i < tokens.length; i++)
			this.attribList.add(tokens[i]);
	}

	/**
	 * Add a child node
	 * @param node the child node
	 * */
	public void addChild(XMLNode node)
	{
		children.add(node);
		node.parent = this;
	}

	// Test if this node is a branch (Child and parent)
	public boolean isBranch()
	{
		return children.size() > 0;
	}

	// Test if this node is a leaf (No Children only parent)
	public boolean isLeaf()
	{
		return children.size() == 0;
	}

	// Test if this node is a root (No Parent)
	public boolean isRoot()
	{
		return parent == null;
	}

	/**
	 * Get a list of children nodes of this node
	 * @return the child node list
	 * */
	public ArrayList<XMLNode> getChildren()
	{
		return children;
	}

	public String toString()
	{
		String name = this.type + "@" + hashCode() / 1000000;

		String s = name + "( " + attribList + " )" + "\n";

		for (XMLNode n : children)
			s += name + " -> " + n;

		return s;
	}

}
