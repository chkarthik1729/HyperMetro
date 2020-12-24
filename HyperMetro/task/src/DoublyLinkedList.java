import java.util.NoSuchElementException;

public class DoublyLinkedList<E> {
    private Node<E> head;
    private Node<E> tail;
    private int size;

    public DoublyLinkedList() {
        this.size = 0;
    }

    public Node<E> getHead() {
        return head;
    }

    public Node<E> getTail() {
        return tail;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public String toString() {
        Node<E> tmp = head;
        StringBuilder result = new StringBuilder();
        while (tmp != null) {
            result.append(tmp.getValue()).append(" ");
            tmp = tmp.getNext();
        }
        return result.toString();
    }

    public void addFirst(E e) {
        Node<E> newNode = new Node<>(e, null, head);
        if (head != null) head.setPrev(newNode);
        head = newNode;

        if (tail == null) tail = newNode;
        size++;
    }

    public void addLast(E e) {
        Node<E> newNode = new Node<>(e, tail, null);
        if (tail != null) tail.setNext(newNode);
        tail = newNode;

        if (head == null) head = newNode;
        size++;
    }

    public void addBefore(Node<E> node, E e) {
        if (node == null) throw new NoSuchElementException();

        Node<E> newNode = new Node<>(e, null, node);

        if (node.getPrev() == null) {
            node.setPrev(newNode);
            newNode.setNext(node);
            head = newNode;
        } else {
            var temp = node.getPrev();
            node.setPrev(newNode);
            newNode.setNext(node);
            temp.setNext(newNode);
        }

        size++;
    }

    public void removeFirst() {
        if (size == 0) throw new NoSuchElementException();

        head = head.getNext();
        head.setPrev(null);
        size--;
    }

    public void removeLast() {
        if (size == 0) throw new NoSuchElementException();

        tail = tail.getPrev();
        tail.setNext(null);
        size--;
    }

    public void remove(Node<E> e) {
        if (e == null) throw new NoSuchElementException();

        if (e.getPrev() == null) {
            removeFirst();
        } else if (e.getNext() == null) {
            removeLast();
        } else {
            e.getPrev().setNext(e.getNext());
            e.getNext().setPrev(e.getPrev());
            size--;
        }
    }

    public Node<E> find(E e) {
        Node<E> curr = head;
        while (curr != null) {
            if (curr.getValue().equals(e))
                return curr;
            curr = curr.getNext();
        }
        return null;
    }
}
