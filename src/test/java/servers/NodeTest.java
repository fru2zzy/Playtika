package servers;

import exceptions.NoDataException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NodeTest {

    private static Node node;
    private static Node emptyNode;

    @BeforeAll
    static void beforeClassNodeTest() {
        node = new Node(1, 2);
        emptyNode = new Node(0, 0);
    }

    @Test
    void getNodeInnerFailableTest() {
        boolean exceptionIsCatch = false;
        try {
            emptyNode.getInnerFailable(0);
        } catch (NoDataException e) {
            assertEquals("Cannot get inner from Node, Nodes doesn't have any inner child", e.getMessage(),
                    "Exception Message is incorrect after getting some inner of empty Node");
            exceptionIsCatch = true;
        }
        assertTrue(exceptionIsCatch, "We should get an 'NoDataException' after getting some inner of empty Node");
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
    void emptyNodeSizeTest() {
        assertEquals(0, emptyNode.getSize(), "Size of empty Node should be 0");
    }

    @Test
    void emptyNodeGetIdTest() {
        assertEquals(0, emptyNode.getId(), "Id of an empty Node should be 0");
    }

    @Test
    void failNodeTest() {
        Node nodeToFail = new Node(0, 0);
        nodeToFail.failNode();
        assertTrue(nodeToFail.isFailed(), "After calling node.failNode() field 'failed' should be become true");
    }

    @Test
    void failEmptyNodeTest() {
        boolean exceptionIsCatch = false;
        try {
            emptyNode.getInnerFailable(0);
        } catch (NoDataException e) {
            assertEquals("Cannot get inner from Node, Nodes doesn't have any inner child", e.getMessage(),
                    "Exception Message is incorrect after getting some inner of empty Server");
            exceptionIsCatch = true;
        }
        assertTrue(exceptionIsCatch, "We should get an 'NoDataException' after getting some inner of empty Server");
    }

    ///// toString tests /////

    @Test
    void nodeToStringTest() {
        assertEquals("Node{id=2, parentId=1, failed=false}", node.toString(),
                "toString() on Node item is modified or broken");
    }

    @Test
    void emptyNodeToStringTest() {
        assertEquals("Node{id=0, parentId=0, failed=false}", emptyNode.toString(),
                "toString() on empty Node item is modified or broken");
    }
}