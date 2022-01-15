/**
 * @author Zhengzheng Yu
 *
 */

public class Node {
    private int i;
    private int j;
    private boolean mark;

    /**
     * Constructor which initializes a new Node object with the specified
     * parameters.
     *
     */
    Node(int i,int j) {
        this.i = i;
        this.j = j;
        mark=false;
    }

    /**
     * marks the node with the specified value.
     *
     * @param mark the mark of the Node
     */
    void setMark(boolean mark) {
        this.mark = mark;
    }

    /**
     * returns the value with which the node has been marked.
     *
     * @return the mark of the Node
     */
    int getI() {
        return i;
    }

    /**
     * returns the name of the node.
     *
     * @return the name of the Node
     */
    int getJ() {
        return j;
    }

    /**
     * returns true if the node is marked.
     *
     * @return if the node is marked.
     */
    boolean isMarked() {
        return mark;
    }
}
