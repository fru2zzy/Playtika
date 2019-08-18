package servers;

import exceptions.NoDataException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static utils.Const.DEFAULT_NODES_COUNT;
import static utils.Const.DEFAULT_SERVERS_COUNT;

public class ServerTest {

    private static Failable firstInner;
    private static Failable server;
    private static Failable emptyServer;
    private final static int serverId = 2;

    @BeforeAll
    static void beforeClassServerTest() {
        Cluster cluster = new Cluster(DEFAULT_SERVERS_COUNT, DEFAULT_NODES_COUNT);
        server = cluster.getInnerFailable(serverId);
        emptyServer = new Server(0, 0, 0);
        firstInner = server.getInnerFailable(0);
    }

    @Test
    void getIdTest() {
        assertEquals(serverId, server.getId(), "'Server' should have id = " + serverId);
    }

    @Test
    void getParentIdTest() {
        assertEquals(serverId, server.getParentId(), "Parent id of first server should be equal to " + serverId);
    }

    @Test
    void isFailedTest() {
        assertFalse(server.isFailed(), "Server of newly created 'Cluster' object should be initialized with 'failed' = false value");
    }

    @Test
    void initializeServerWithNegativeNodesCount() {
        boolean exceptionIsCatch = false;
        try {
            new Server(0, 0, -4);
        } catch (IllegalArgumentException e) {
            assertEquals("Cannot initialize Server by -4 nodes count", e.getMessage(),
                    "We should receive correct IllegalArgumentException after initialize Server by -4 nodes count");
            exceptionIsCatch = true;
        }
        assertTrue(exceptionIsCatch, "We should receive correct IllegalArgumentException after initialize Server by -4 nodes count");
    }

    @Test
    void getFirstInnerFailableTest() {
        assertNotNull(firstInner, "inner object should not be null because we've successfully initialized Cluster->Server->Node item");
        assertEquals(0, firstInner.getId(), "getId() from the first inner object should return '0'");
        assertEquals(serverId, firstInner.getParentId(), "getParentId() from the first inner object should return '0'");
        assertFalse(firstInner.isFailed(), "isFailed() from the first inner object should return 'false' because we didn't perform any disconnect");
        assertEquals(0, firstInner.getSize(), "Size of inner objects from our Server inner should be equals to 0 (Nodes has no inners)");
    }

    @Test
    void getSizeTest() {
        assertEquals(DEFAULT_NODES_COUNT, server.getSize(), "We have " + DEFAULT_NODES_COUNT + " nodes in our Server");
    }

    @Test
    void failRandomNodeTest() {
        Server serverToFail = new Server(0, 0, DEFAULT_NODES_COUNT);
        assertFalse(serverToFail.isFailed(), "newly created 'Server' should be initialized with 'failed' = false value");
        serverToFail.failRandomNode();
        assertTrue(serverToFail.isFailed(), "After calling failRandomNode() method 'Server' should have 'failed' = true value");

        boolean atLeastOneNodeIsFailed = false;
        for (int i = 0; i < serverToFail.getSize(); i++) {
            if (serverToFail.getInnerFailable(i).isFailed()) {
                atLeastOneNodeIsFailed = true;
            }
        }
        assertTrue(atLeastOneNodeIsFailed, "After calling failRandomNode() method at least one Node should have 'failed' = true status");
    }

    @Test
    void failAllNodesTest() {
        Server serverToFail = new Server(0, 0, DEFAULT_NODES_COUNT);
        assertFalse(serverToFail.isFailed(), "newly created 'Server' should be initialized with 'failed' = false value");
        serverToFail.failAllNodes();
        assertTrue(serverToFail.isFailed(), "After calling failAllNodes() method 'Server' should have 'failed' = true value");
        for (int i = 0; i < serverToFail.getSize(); i++) {
            boolean isNodeFailed = serverToFail.getInnerFailable(i).isFailed();
            assertTrue(isNodeFailed, "After calling failAllNodes() method all Nodes should have 'failed' = true status");
        }
    }

    @Test
    void emptyServerInnerTest() {
        boolean exceptionIsCatch = false;
        try {
            emptyServer.getInnerFailable(0);
        } catch (NoDataException e) {
            assertEquals("Cannot get 0 inner Failable because nodes size = 0", e.getMessage(),
                    "Exception Message is incorrect after getting some inner of empty Server");
            exceptionIsCatch = true;
        }
        assertTrue(exceptionIsCatch, "We should get an 'NoDataException' after getting some inner of empty Server");
    }

    @Test
    void emptyServerSizeTest() {
        assertEquals(0, emptyServer.getSize(), "Size of empty Server should be 0");
    }

    @Test
    void emptyServerGetIdTest() {
        assertEquals(0, emptyServer.getId(), "Id of an empty Server should be 0");
    }

    ///// toString tests /////

    @Test
    void emptyServerToStringTest() {
        assertEquals("\n" + "Server{id=0, failed=false, nodes=[]}", emptyServer.toString(),
                "toString() on empty Cluster item is modified or broken");
    }

    @Test
    void toStringTest() {
        String hardCodedString = "\n" + "Server{id=2, failed=false, nodes=[Node{id=0, parentId=2, failed=false}, Node{id=1, parentId=2, failed=false}, Node{id=2, parentId=2, failed=false}, Node{id=3, parentId=2, failed=false}, Node{id=4, parentId=2, failed=false}]}";
        assertEquals(hardCodedString, server.toString(), "toString() on Server item is modified or broken");
    }
}