import java.util.LinkedList;
import java.util.Stack;

public class BinarySearchTree<T extends Comparable<T>> implements SortedCollectionInterface<T> {


    protected static class Node<T> {
        public T data;

        public Node<T> up;

        @SuppressWarnings("unchecked")
        public Node<T>[] down = (Node<T>[])new Node[2];
        public Node(T data) { this.data = data; }
        

        public boolean isRightChild() {
            return this.up != null && this.up.down[1] == this;
        }

    }

    protected Node<T> root; 
    protected int size = 0; 


    public boolean insert(T data) throws NullPointerException {
        if (data == null)
			throw new NullPointerException("Cannot insert data value null into the tree.");
		return this.insertHelper(new Node<>(data));
    }

    protected boolean insertHelper(Node<T> newNode) throws NullPointerException {
        if(newNode == null) throw new NullPointerException("new node cannot be null");

        if (this.root == null) {
            root = newNode;
            size++;
            return true;
        } else {
            Node<T> current = this.root;
            while (true) {
                int compare = newNode.data.compareTo(current.data);
                if (compare == 0) {
                	return false;
				} else if (compare < 0) {
                    if (current.down[0] == null) {
                        current.down[0] = newNode;
                        newNode.up = current;
                        this.size++;
                        return true;
                    } else {
                        current = current.down[0];
                    }
                } else {
                    if (current.down[1] == null) {
                        current.down[1] = newNode;
                        newNode.up = current;
                        this.size++;
                        return true;
                    } else {
                        current = current.down[1]; 
                    }
                }
            }
        }
    }

    protected void rotate(Node<T> child, Node<T> parent) throws IllegalArgumentException {
        
         
        
        if (child != parent.down[0] && child != parent.down[1]) {
            throw new IllegalArgumentException("the child and parent are not related.");
        }
        if (child == null || parent == null) {
            throw new IllegalArgumentException("the child and parent cannot be null.");
        }
        if (parent.down[0] == child) { 
            Node<T> right = child.down[1];
            child.down[1] = parent; 
            parent.down[0] = right; 
            if (right != null) { 
                right.up = parent; 
            }
        }
            
    else if (parent.down[1] == child) { 
        Node<T> left = child.down[0]; 
        child.down[0] = parent; 
        parent.down[1] = left;
        if (left != null) { 
            left.up = parent; 
        }
  
        
        }
        if (parent.up != null) { 
            if (parent == parent.up.down[0]) { 
                parent.up.down[0] = child; 
            }
            else { 
                parent.up.down[1] = child; 
            }
        } 
        else { 
            root = child;
        }
        child.up = parent.up; 
        parent.up = child;
             
        }

    public int size() {
        return size;
    }


    public boolean isEmpty() {
        return this.size() == 0;
    }

    public boolean contains(Comparable<T> data) {
        if (data == null) {
            throw new NullPointerException("This tree cannot store null references.");
        } else {
            Node<T> nodeWithData = this.findNode(data);
            return (nodeWithData != null);
        }
    }


    public void clear() {
        this.root = null;
        this.size = 0;
    }

    protected Node<T> findNode(Comparable<T> data) {
        Node<T> current = this.root;
        while (current != null) {
            int compare = data.compareTo(current.data);
            if (compare == 0) {
                return current;
            } else if (compare < 0) {
                if (current.down[0] == null) {
                    return null;
                }
                current = current.down[0];
            } else {
                if (current.down[1] == null) {
                    return null;
                }
                current = current.down[1];
            }
        }
        return null;
    }

    public String toInOrderString() {

        StringBuffer sb = new StringBuffer();
        sb.append("[ ");
        if (this.root != null) {
            Stack<Node<T>> nodeStack = new Stack<>();
            Node<T> current = this.root;
            while (!nodeStack.isEmpty() || current != null) {
                if (current == null) {
                    Node<T> popped = nodeStack.pop();
                    sb.append(popped.data.toString());
                    if(!nodeStack.isEmpty() || popped.down[1] != null) sb.append(", ");
                    current = popped.down[1];
                } else {
                    nodeStack.add(current);
                    current = current.down[0];
                }
            }
        }
        sb.append(" ]");
        return sb.toString();
    }

    public String toLevelOrderString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[ ");
        if (this.root != null) {
            LinkedList<Node<T>> q = new LinkedList<>();
            q.add(this.root);
            while(!q.isEmpty()) {
                Node<T> next = q.removeFirst();
                if(next.down[0] != null) q.add(next.down[0]);
                if(next.down[1] != null) q.add(next.down[1]);
                sb.append(next.data.toString());
                if(!q.isEmpty()) sb.append(", ");
            }
        }
        sb.append(" ]");
        return sb.toString();
    }

    public String toString() {
        return "level order: " + this.toLevelOrderString() +
                "\nin order: " + this.toInOrderString();
    }

}
