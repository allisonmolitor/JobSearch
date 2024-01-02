public class RedBlackTree<T extends Comparable<T>> extends BinarySearchTree<T> {

    protected static class RBTNode<T> extends Node<T> {
        public int blackHeight = 0;

      
        public RBTNode(T data) {
            super(data);
        }

        public RBTNode<T> getUp() {
            return (RBTNode<T>) this.up;
        }

        public RBTNode<T> getDownLeft() {
            return (RBTNode<T>) this.down[0];
        }

        public RBTNode<T> getDownRight() {
            return (RBTNode<T>) this.down[1];
        }
    }

    protected void enforceRBTreePropertiesAfterInsert(RBTNode<T> newRed) {

        // if new node is root and red
        if (newRed.up == null) {
            newRed.blackHeight = 1;
            return;
        }
        if (newRed.getUp().blackHeight == 1) {
            return;

        }
        // if parent is red(violation) and child's uncle is RIGHT
        if ((newRed.getUp().blackHeight == 0)
                && (newRed.getUp() == newRed.getUp().getUp().getDownLeft())) {

            // if RIGHT uncle is RED
            if ((newRed.getUp().getUp().getDownRight() != null)
                    && (newRed.getUp().getUp().getDownRight().blackHeight == 0)) {
                // re-color parent
                newRed.getUp().blackHeight = 1;
                // re-color grandparent
                newRed.getUp().getUp().blackHeight = 0;
                // re-color uncle
                newRed.getUp().getUp().getDownRight().blackHeight = 1;
                enforceRBTreePropertiesAfterInsert(newRed);
                return;
            }

            // if RIGHT uncle is BLACK and child is LEFT of parent (line)
            if ((((newRed.getUp().getUp().getDownRight() == null))
                    || ((newRed.getUp().getUp().getDownRight().blackHeight == 1)))
                    && (newRed == newRed.getUp().getDownLeft())) {
                // re-color parents and grandparent
                newRed.getUp().blackHeight = 1;
                newRed.getUp().getUp().blackHeight = 0;
                // rotate parent and grandparent
                rotate(newRed.getUp(), newRed.getUp().getUp());
                enforceRBTreePropertiesAfterInsert(newRed);
                return;

            }
            // if RIGHT uncle is BLACK and child is RIGHT of parent (triangle)
            if ((((newRed.getUp().getUp().getDownRight() == null))
                    || ((newRed.getUp().getUp().getDownRight().blackHeight == 1)))
                    && (newRed == newRed.getUp().getDownRight())) {
                // rotate child and parent
                rotate(newRed, newRed.getUp());
                enforceRBTreePropertiesAfterInsert(newRed);
                return;

            }
        }

        // if parent is red(violation) and child's uncle is LEFT
        if ((newRed.getUp().blackHeight == 0)
                && (newRed.getUp() == newRed.getUp().getUp().getDownRight())) {

            // if LEFT uncle is RED
            if ((newRed.getUp().getUp().getDownLeft() != null)
                    && (newRed.getUp().getUp().getDownLeft().blackHeight == 0)) {
                // re-color parent
                newRed.getUp().blackHeight = 1;
                // re-color grandparent
                newRed.getUp().getUp().blackHeight = 0;
                // re-color uncle
                newRed.getUp().getUp().getDownLeft().blackHeight = 1;
                enforceRBTreePropertiesAfterInsert(newRed);
                return;

            }

            // if LEFT uncle is BLACK and child is LEFT of parent (triangle)
            if ((((newRed.getUp().getUp().getDownLeft() == null))
                    || ((newRed.getUp().getUp().getDownLeft().blackHeight == 1)))
                    && (newRed == newRed.getUp().getDownLeft())) {
                // rotate child and parent
                rotate(newRed, newRed.getUp());
                enforceRBTreePropertiesAfterInsert(newRed);
                return;

            }
            // if LEFT uncle is BLACK and child is RIGHT of parent (line)
            if ((((newRed.getUp().getUp().getDownLeft() == null))
                    || ((newRed.getUp().getUp().getDownLeft().blackHeight == 1)))
                    && (newRed == newRed.getUp().getDownRight())) {
                // re-color parents and grandparent
                newRed.getUp().blackHeight = 1;
                newRed.getUp().getUp().blackHeight = 0;
                // rotate parent and grandparent
                rotate(newRed.getUp(), newRed.getUp().getUp());
                enforceRBTreePropertiesAfterInsert(newRed);
                return;

            }
        }
    }

    @Override
    public boolean insert(T data) throws NullPointerException {
        if (data == null)
            throw new NullPointerException("Cannot insert data value null into the tree.");

        RBTNode<T> newRed = new RBTNode<>(data);

        boolean insert = insertHelper(newRed);

        if (insert) {
            enforceRBTreePropertiesAfterInsert(newRed);
            if (root != null)
                ((RBTNode<T>) root).blackHeight = 1;
        }

        return insert;

    }

}