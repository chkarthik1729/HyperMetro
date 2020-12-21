public class SinglyLinkedListNode<T> {

    private T e;
    private SinglyLinkedListNode<T> next;

    public SinglyLinkedListNode() {
    }

    public SinglyLinkedListNode(T e) {
        this.e = e;
    }

    public T getData() {
        return e;
    }

    public void setData(T e) {
        this.e = e;
    }

    public SinglyLinkedListNode<T> getNext() {
        return next;
    }

    public void setNext(SinglyLinkedListNode<T> next) {
        this.next = next;
    }
}
