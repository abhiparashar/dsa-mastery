import java.util.HashMap;
import java.util.Map;

/**
 * LeetCode 146 - LRU Cache ⭐⭐ (Meta, Amazon, Google — #1 design problem)
 *
 * Design a data structure that supports get and put in O(1).
 * When capacity is exceeded, evict the Least Recently Used item.
 *
 * WHY IT'S THE GOAT:
 * Combines HashMap (O(1) lookup) + Doubly Linked List (O(1) remove/insert).
 * Tests data structure DESIGN, not just algorithm knowledge.
 *
 * ARCHITECTURE:
 * - HashMap<key, Node> for O(1) access to any node
 * - Doubly Linked List to maintain usage order
 *   - Head = most recently used
 *   - Tail = least recently used
 * - get(key): move node to head, return value
 * - put(key, val): insert at head. If over capacity, evict from tail.
 */
public class LRUCache {

    class Node {
        int key, val;
        Node prev, next;
        Node(int k, int v) { key = k; val = v; }
    }

    int capacity;
    Map<Integer, Node> map = new HashMap<>();
    Node head = new Node(0, 0); // sentinel
    Node tail = new Node(0, 0); // sentinel

    public LRUCache(int capacity) {
        this.capacity = capacity;
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        if (!map.containsKey(key)) return -1;
        Node node = map.get(key);
        remove(node);
        insertAtHead(node);
        return node.val;
    }

    public void put(int key, int value) {
        if (map.containsKey(key)) {
            remove(map.get(key));
        }
        Node node = new Node(key, value);
        insertAtHead(node);
        map.put(key, node);

        if (map.size() > capacity) {
            Node lru = tail.prev; // least recently used = right before tail sentinel
            remove(lru);
            map.remove(lru.key); // THIS is why Node stores the key!
        }
    }

    private void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void insertAtHead(Node node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }

    public static void main(String[] args) {
        LRUCache cache = new LRUCache(2);

        cache.put(1, 1);
        cache.put(2, 2);
        assert cache.get(1) == 1;       // returns 1, moves key 1 to front
        System.out.println("get(1) = 1 ✓");

        cache.put(3, 3);                 // evicts key 2 (LRU)
        assert cache.get(2) == -1;       // key 2 was evicted
        System.out.println("get(2) = -1 ✓ (evicted)");

        cache.put(4, 4);                 // evicts key 1
        assert cache.get(1) == -1;       // key 1 was evicted
        System.out.println("get(1) = -1 ✓ (evicted)");

        assert cache.get(3) == 3;
        System.out.println("get(3) = 3 ✓");

        assert cache.get(4) == 4;
        System.out.println("get(4) = 4 ✓");

        // Test update existing key
        LRUCache cache2 = new LRUCache(2);
        cache2.put(1, 1);
        cache2.put(2, 2);
        cache2.put(1, 10);              // update key 1
        assert cache2.get(1) == 10;     // should return updated value
        System.out.println("get(1) = 10 ✓ (updated)");

        cache2.put(3, 3);               // should evict key 2 (not 1, since 1 was just accessed)
        assert cache2.get(2) == -1;
        System.out.println("get(2) = -1 ✓ (evicted after update)");

        System.out.println("\n✓ All tests passed!");
    }
}
