package BackEnd.Objects.List;

public class Node<T> {
    private Node<T> next; // pointer
    private Node<T> previous; // pointer
    private T data; // data

    public Node() {
    }

    // constructor to insert at head
    public Node(T data) {
        this(data, null, null);
    }

    // constructor to insert at beginning of list
    public Node(T data, Node<T> next, Node<T> previous) {
        this.next = next;
        this.previous = previous;
        this.data = data;
    }

    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }

    public Node<T> getPrevious() {
        return previous;
    }

    public void setPrevious(Node<T> previous) {
        this.previous = previous;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    

}
