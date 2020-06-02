package metrics;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private String name;
    private String parent;
    private boolean isMain;

    // List of children
    private List<Node> children;

    Node(String name)
    {
        this.name = name;
        parent = "";
        isMain = false;
        children = new ArrayList<>();
    }

    public String getParent() {
        return parent;
    }

    public Node getChild(int i) {
        return children.get(i);
    }

    public Node getChild(String name)
    {
        for (int i = 0; i < children.size(); i++)
        {
            if (children.get(i).getName().equals(name))
            {
                return children.get(i);
            }
        }
        return null;
    }

    public int getNumChildren()
    {
        return children.size();
    }

    public String getName() {
        return name;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public void addChild(Node child) {
        this.children.add(child);
    }

    public void setMain(boolean main) {
        isMain = main;
    }

    public boolean isMain()
    {
        return isMain;
    }
}