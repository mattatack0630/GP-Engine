package gui.XML;

import gui.Component.Component;
import gui.Component.Panel.Panel;
import gui.GuiScene;
import utils.ExtraUtils;

/**
 * The XMLParser Class
 *
 * Parses an XML document into a GuiScene
 */
public class XMLParser
{

	private static final String GUI_RES_PATH = "res/gui_files/";

	/**
	 * Parses an XML String into a GuiScene
	 * @param XML the xml string to parse
	 * @return the GuiScene created
	 * */
	public static GuiScene parseXML(String XML)
	{
		// Create a nodeTree from the xml string
		XMLNode nodeTree = generateNodeTree(XML);

		// If the root node has no children, return an empty GuiScene
		if (nodeTree.isLeaf())
			return new GuiScene();

		// Convert the nodeTree to a Panel component tree
		Component root = generateComponents(nodeTree.getChildren().get(0));

		// Make a GuiScene with the root component
		GuiScene scene = new GuiScene();
		scene.addPanel((Panel) root);


		return scene;
	}

	/**
	 * Parse XML from a file
	 * */
	public static GuiScene parseXMLFromFile(String fileAsString)
	{
		if (fileAsString.contains(GUI_RES_PATH))
			System.out.println("Don't include " + GUI_RES_PATH);
		return parseXML(ExtraUtils.getFileAsString(GUI_RES_PATH + fileAsString));
	}

	/**
	 * Generate Panels and components from a root XML Node, with parent tree relationship.
	 * Recursive.
	 * @param node the root XML Node
	 * @return the root component
	 * */
	public static Component generateComponents(XMLNode node)
	{
		Component c = Component.fromXML(node);

		// If the node is a branch, conversion to Panel should be valid.
		if (node.isBranch())
			if (c instanceof Panel)
				for (XMLNode child : node.getChildren())
					((Panel) c).addChild(generateComponents(child));

		// Apply Attribute to this node
		c.applyAttribList(node.attribList);

		return c;
	}

	/**
	 * Generate a node tree from an XML String
	 * @param xml the xml string
	 * @return the XML root node
	 * */
	private static XMLNode generateNodeTree(String xml)
	{
		// Hold whether or not the current node is in comments
		boolean inComments = false;
		// Tokenize the xml String into the <> tags
		String[] tokens = xml.split("(?<=>)");
		// The file root Node is parent of all elements
		XMLNode currNode = new XMLNode("FILE_ROOT");

		// For each token
		for (int i = 0; i < tokens.length; i++)
		{
			String token = tokens[i].trim(); // Trim the tokens

			// If there is a <!--> tag toggle the inComments variable
			if (token.contains(XMLKeyWords.COMMENT_TAG))
			{
				inComments = !inComments;
				continue;
			}
			// if there is not < and > tags or in comments, then don't continue.
			if (!token.contains("<") || !token.contains(">") || inComments)
				continue;
			// Closing the current token
			if (token.contains(XMLKeyWords.CLOSING_TAG))
			{
				currNode = currNode.parent;
				continue;
			}

			// Parse the token into a node
			String innerAttribs = ExtraUtils.stripString(token, "</", "/>", "<", ">", "\n");
			XMLNode node = new XMLNode(innerAttribs);

			// add this node as a child of the node that is currently open
			currNode.addChild(node);
			// Set this node to the new open tag
			currNode = node;
			// if this is a self closing token, instantly close this tag (Set it back to the parent)
			if (token.contains(XMLKeyWords.SELF_CLOSING_TAG))
				currNode = currNode.parent;
		}

		return currNode;
	}
}
