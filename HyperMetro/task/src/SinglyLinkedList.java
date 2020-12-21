public class SinglyLinkedList<T> {

    private SinglyLinkedListNode<T> head;
    private SinglyLinkedListNode<T> tail;
    private int size = 0;

    public SinglyLinkedListNode<T> getHead() {
        return head;
    }

    public SinglyLinkedListNode<T> getTail() {
        return tail;
    }

    public void addFirst(T e) {
        size++;

        var node = new SinglyLinkedListNode<>(e);
        if (head == null) {
            head = tail = node;
            return;
        }
        node.setNext(head);
        head = node;
    }

    public void addLast(T e) {
        size++;

        var node = new SinglyLinkedListNode<>(e);
        if (tail == null) {
            head = tail = node;
            return;
        }
        tail.setNext(node);
        tail = node;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}
