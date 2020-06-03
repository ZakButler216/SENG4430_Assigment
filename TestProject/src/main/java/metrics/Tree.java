package metrics;

public class Tree {
    private Node root;

    public Tree()
    {
        root = null;
    }

    public static Node search(Node node, String parentName){
        if (node==null)
            return null;
        else if (node.getName().equals(parentName))
        {
            return node;
        }
        else
        {
            for (int i = 0; i < node.getNumChildren(); i++)
            {
                if(node.getChild(parentName) != null)
                {
                    return node.getChild(parentName);
                }
            }

            for (int i = 0; i < node.getNumChildren(); i++)
            {
                Node temp = search(node.getChild(i), parentName);
                if (temp != null && temp.getName().equals(parentName))
                {
                    return temp;
                }
            }
        }
        return null;
    }

    //number of children (return to CherrenSection)
    public int getNumOfChildren(String fileName)
    {
        Node file = search(root, fileName);
        if (file == null)
        {
            return -1;
        }
        return file.getNumChildren();
    }


    public int maxDepth(String fileName)
    {
        Node file = search(root, fileName);
        int depth = 0;

        if (file == null)
        {
            return -1;
        }

        while (file != null && !file.getParent().equals(""))
        {
            file = search(root, file.getParent());
            depth++;
        }
        return depth;
    }

    //max of Depth (return to CherrenSection)
    public int maxDepth()
    {
        int maxDepth = 0;
        int tempDepth = 0;


        for (int i = 0; i < root.getNumChildren(); i++)
        {
            tempDepth = maxDepth(root.getChild(i));

            if (tempDepth > maxDepth)
            {
                maxDepth = tempDepth;
            }
        }
        maxDepth++;

        return maxDepth;
    }

    //depth of tree
    public int maxDepth(Node node)
    {
        int maxDepth = 0;
        int tempDepth = 0;

        if (node == null)
            return 0;
        else
        {
            for (int i = 0; i < node.getNumChildren(); i++)
            {
                tempDepth = maxDepth(node.getChild(i));

                if (tempDepth > maxDepth)
                {
                    maxDepth = tempDepth;
                }
            }
            maxDepth++;
        }
        return maxDepth;
    }

    // method to insert a new Node
    public boolean insert(Node node){
        if (this.root == null)
            this.root = node;

            //If no parent, then normal class attached to the main class
        else if(node.getParent().equals("") || node.getParent().equals(root.getName())) {
            root.addChild(node);
        }
        //Else, node is a child of a previously used node
        else {
            //Do search function
            Node temp = search(root, node.getParent());
            if (temp == null)
            {
                return true;
            }
            temp.addChild(node);
        }
        return false;
    }

    public Node getRoot() {
        return root;
    }
}