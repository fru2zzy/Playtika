package servers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NodeTest {

    private static Node node;

    @BeforeAll
    static void beforeClassNodeTest() {
        node = new Node(1, 2);
    }

    @Test
    void getNodeInnerFailableTest() {
        Failable nodeInner = node.getInnerFailable(0);
        assertNull(nodeInner, "Node item doesn't have any inners, it should return null in case of node.getInnerFailable");
    }

    @Test
    void getSizeTest() {
        assertEquals(0, node.getSize(), "Node item doesn't have any inners, getSize() method should always return 0");
    }

    @Test
    void getIdTest() {
        assertEquals(2, node.getId(), "We've hardcoded Node id = 1, but method getId() returned another value");
    }

    @Test
    void getParentIdTest() {
        assertEquals(1, node.getParentId(), "We've hardcoded Node parent id = 1, but method getParentId() returned another value");
    }

    @Test
    void isFailedTest() {
        assertFalse(node.isFailed(), "Newly created Node item should be initialized as 'failed' = false");
    }

    @Test
    void failNodeTest() {
        Node nodeToFail = new Node(0, 0);
        nodeToFail.failNode();
        assertTrue(nodeToFail.isFailed(), "After calling node.failNode() field 'failed' should be become true");
    }
}