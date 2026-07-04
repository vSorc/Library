import java.io.Serializable;

public class List<V> implements Serializable {
    private Node<V> firstNode;
    private Node<V> lastNode;
    private String name;
    public List() {this("list");}
    public List(String listName) {
        name = listName;
        firstNode = lastNode = null;
    }
    public void insertAtFront(V insertItem) {
        if(isEmpty()) {
            firstNode = lastNode = new Node<V>(insertItem);
        }
        else {firstNode = new Node<V>(insertItem, firstNode);}
    }
    public boolean isEmpty() {return firstNode == null;}
    public void insertAtBack(V insertItem) {
        if (isEmpty()) {
            firstNode = lastNode = new Node<V>(insertItem);
        }
        else {lastNode = lastNode.nextNode = new Node<V>(insertItem);}

    }
    public V removeFromFront() {
        V removedItem = firstNode.getData();
        if (firstNode == lastNode) {
            firstNode = lastNode = null;
        }
        else {
            firstNode = firstNode.nextNode;
        }
        return removedItem;
    }
    public V getFromFront() {
        return firstNode.getData();
    }
    public V removeFromBack() {
        V removedItem = lastNode.getData();
        if (firstNode == lastNode)
            firstNode = lastNode = null;
        else {
            Node<V> current = firstNode;
            while(current.nextNode != lastNode) 
                current = current.nextNode;
            lastNode = current;
            current.nextNode = null;

        }
        return removedItem;
           
    }

    public void print() {
        if(isEmpty()) {
            System.out.printf("Empty %s\n", name);
            return;
        }
        System.out.printf("The %s is: ", name);
        Node<V> current = firstNode;
        
        while(current != null) {
            System.out.printf("%s ", current.getData());
            current = current.nextNode;
        }
        System.out.println("\n");
    }
    public boolean remove(V item) {
    if (isEmpty()) return false;

    if (firstNode.getData().equals(item)) {
        removeFromFront();
        return true;
    }

    Node<V> current = firstNode;
    while (current.nextNode != null) {
        if (current.nextNode.getData().equals(item)) {
            if (current.nextNode == lastNode) {
                lastNode = current;
            }
            current.nextNode = current.nextNode.nextNode;
            return true;
        }
        current = current.nextNode;
    }
    return false;
}
    public Node<V> getFirstNode() {
        return firstNode;
    }

}
