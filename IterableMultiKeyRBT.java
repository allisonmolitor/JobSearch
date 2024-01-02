import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

public class IterableMultiKeyRBT<T extends Comparable<T>>
        extends RedBlackTree<KeyListInterface<T>>
        implements IterableMultiKeySortedCollectionInterface<T> {

    protected Comparable<T> startPoint = null;
    protected int numKeys = 0;

    @Override
    public boolean insertSingleKey(T key) {
        KeyList<T> keyListToInsert = new KeyList<>(key);
        Node<KeyListInterface<T>> target = this.findNode(keyListToInsert);
        if (target == null) {
            this.insert(keyListToInsert);
            this.numKeys++;
            return true;
        }
        else {
            target.data.addKey(key);
            this.numKeys++;
            return false;
        }
    }

    @Override
    public int numKeys() {
        return numKeys;
    }
    
    @Override
    public void clear() {
        super.clear();
        numKeys = 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            Stack<Node<KeyListInterface<T>>> stack = getStartStack();
            Iterator<T> klit = null;

            @Override
            public boolean hasNext() {
                return (! this.stack.empty()) || (this.klit != null && this.klit.hasNext());
            }

            @Override
            public T next() {
                if (this.klit != null && this.klit.hasNext()) {
                    return this.klit.next();
                }
                else if (this.stack.empty()) {
                    throw new NoSuchElementException();
                }
                else {
                    Node<KeyListInterface<T>> current = this.stack.pop();
                    this.klit = current.data.iterator();
                    if (current.down[1] != null) {
                        current = current.down[1];
                        while (current != null) {
                            this.stack.push(current);
                            current = current.down[0];
                        }
                    }
                    return this.klit.next();
                }

            }
        };

    }
    protected Stack<Node<KeyListInterface<T>>> getStartStack() {
        Stack<Node<KeyListInterface<T>>> stack = new Stack<>();
        Node<KeyListInterface<T>> current = this.root;
        if (startPoint == null) {
            while (current != null) {
                stack.push(current);
                current = current.down[0];
            }
        }
        else {
            while (current != null) {
                int comparison = startPoint.compareTo(current.data.iterator().next());

                if (comparison == 0) {
                    stack.push(current);
                    break;
                }
                else if (comparison < 0) {
                    stack.push(current);
                    current = current.down[0];
                }
                else {
                    current = current.down[1];
                }
            }
        }
        return stack;
    }
    
    @Override
    public void setIterationStartPoint(Comparable<T> startPoint) {
        this.startPoint = startPoint;
    }

}