package servers;

import exceptions.NoDataException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static utils.Const.DEFAULT_NODES_COUNT;
import static utils.Const.DEFAULT_SERVERS_COUNT;

public class ClusterTest {

    private static Cluster cluster;
    private static Cluster clusterToFail;
    private static Cluster emptyCluster;
    private static Failable firstInner;
    private static Failable node;

    @BeforeAll
    static void beforeClassClusterTest() {
        cluster = new Cluster(DEFAULT_SERVERS_COUNT, DEFAULT_NODES_COUNT);
        clusterToFail = new Cluster(DEFAULT_SERVERS_COUNT, DEFAULT_NODES_COUNT);
        emptyCluster = new Cluster(0, 0);
        firstInner = cluster.getInnerFailable(0);
        node = firstInner.getInnerFailable(3);
    }

    @Test
    void getIdTest() {
        assertEquals(0, cluster.getId(), "'Cluster' is the highest item in the hierarchy, his id always should be '0'");
    }

    @Test
    void getParentIdTest() {
        assertEquals(0, cluster.getParentId(), "'Cluster' is the highest item in the hierarchy, his parent id always should be '0'");
    }

    @Test
    void isFailedTest() {
        assertFalse(cluster.isFailed(), "Newly created 'Cluster' object should be initialized with 'failed' = false value");
    }

    @Test
    void getFirstInnerFailableTest() {
        assertNotNull(firstInner, "inner object should not be null because we've successfully initialized Cluster item");
        assertEquals(0, firstInner.getId(), "getId() from the first inner object should return '0'");
        assertEquals(0, firstInner.getParentId(), "getParentId() from the first inner object should return '0'");
        assertFalse(firstInner.isFailed(), "isFailed() from the first inner object should return 'false' because we didn't perform any disconnect");
        assertEquals(DEFAULT_NODES_COUNT, firstInner.getSize(), "Size of inner objects from our Cluster inner should be equals to " + DEFAULT_NODES_COUNT + " (" + DEFAULT_NODES_COUNT + " Nodes in first Server)");
    }

    @Test
    void getInnerFailableOfServer() {
        assertNotNull(node, "Node should not be null");
        assertEquals(DEFAULT_SERVERS_COUNT, node.getId(), "We've get 3rd innerFailable, so we should get node.getId() = " + DEFAULT_SERVERS_COUNT);
        assertEquals(0, node.getParentId(), "We've get 3rd innerFailable from the first server, so we should get node.getParentId() = 0");
        assertEquals(0, node.getParentId(), "isFailed() from the first node should return 'false' because we didn't perform any disconnect");
    }

    @Test
    void getOutOfBoundariesInnerFailableTest() {
        boolean exceptionIsCatch = false;
        try {
            cluster.getInnerFailable(99);
        } catch (NoDataException e) {
            assertEquals("Cannot get 99 inner Failable because servers size = " + DEFAULT_SERVERS_COUNT, e.getMessage(),
                    "We should receive correct 'NoDataException' after attempt of getting innerFailable");
            exceptionIsCatch = true;
        }
        assertTrue(exceptionIsCatch, "We should get an 'NoDataException' after getting IndexOutOfBound inner Cluster");
    }

    @Test
    void getSizeTest() {
        assertEquals(DEFAULT_SERVERS_COUNT, cluster.getSize(), "We have " + DEFAULT_SERVERS_COUNT + " servers in our Cluster");
    }

    @Test
    void sendMessageTest() {
        clusterToFail.sendMessage();
        assertTrue(clusterToFail.isFailed(), "After sending message we should get disconnected status (failed = true)");
        getSizeTest();

        Failable server = clusterToFail.getInnerFailable(0);
        assertNotNull(server, "Server after message sending should not be null");
        assertEquals(DEFAULT_NODES_COUNT, server.getSize(), "Count of nodes shoul not change after message send");
        getOutOfBoundariesInnerFailableTest();
        serverToStringTest();
        nodeToStringTest();
    }

    @Test
    void emptyClusterInnerTest() {
        boolean exceptionIsCatch = false;
        try {
            emptyCluster.getInnerFailable(0);
        } catch (NoDataException e) {
            assertEquals("Cannot get 0 inner Failable because servers size = 0", e.getMessage(),
                    "Exception Message is incorrect after getting some inner of empty Cluster");
            exceptionIsCatch = true;
        }
        assertTrue(exceptionIsCatch, "We should get an 'NoDataException' after getting some inner of empty Cluster");
    }

    @Test
    void emptyClusterSizeTest() {
        assertEquals(0, emptyCluster.getSize(), "Size of empty Cluster should be 0");
    }

    @Test
    void emptyClusterSendMessageTest() {
        boolean exceptionIsCatch = false;
        try {
            emptyCluster.sendMessage();
        } catch (NoDataException e) {
            assertEquals("Cannot perform search in empty servers array!", e.getMessage(),
                    "We should receive correct message error after sending a message in empty Cluster");
            exceptionIsCatch = true;
        }
        assertTrue(exceptionIsCatch, "We should get an 'NoDataException' after sending a message in empty Cluster");
    }

    @Test
    void emptyClusterGetIdTest() {
        assertEquals(0, emptyCluster.getId(), "Id of an empty Cluster should be 0");
    }

    ///// toString tests /////

    @Test
    void clusterToStringTest() {
        String hardCodedString = "Cluster{failed=false, servers=[\n" +
                "Server{id=0, failed=false, nodes=[Node{id=0, parentId=0, failed=false}, Node{id=1, parentId=0, failed=false}, Node{id=2, parentId=0, failed=false}, Node{id=3, parentId=0, failed=false}, Node{id=4, parentId=0, failed=false}]}, \n" +
                "Server{id=1, failed=false, nodes=[Node{id=0, parentId=1, failed=false}, Node{id=1, parentId=1, failed=false}, Node{id=2, parentId=1, failed=false}, Node{id=3, parentId=1, failed=false}, Node{id=4, parentId=1, failed=false}]}, \n" +
                "Server{id=2, failed=false, nodes=[Node{id=0, parentId=2, failed=false}, Node{id=1, parentId=2, failed=false}, Node{id=2, parentId=2, failed=false}, Node{id=3, parentId=2, failed=false}, Node{id=4, parentId=2, failed=false}]}]}";
        assertEquals(hardCodedString, cluster.toString(), "toString() on Cluster item is modified or broken");
    }

    @Test
    void emptyClusterToStringTest() {
        Cluster emptyCluster = new Cluster(0, 0);
        assertEquals("Cluster{failed=false, servers=[]}", emptyCluster.toString(),
                "toString() on empty Cluster item is modified or broken");
    }

    @Test
    void serverToStringTest() {
        String hardCodedString = "\n" + "Server{id=0, failed=false, nodes=[Node{id=0, parentId=0, failed=false}, " +
                "Node{id=1, parentId=0, failed=false}, Node{id=2, parentId=0, failed=false}, Node{id=3, parentId=0, " +
                "failed=false}, Node{id=4, parentId=0, failed=false}]}";
        assertEquals(hardCodedString, firstInner.toString(), "toString() on Server item is modified or broken");
    }

    @Test
    void nodeToStringTest() {
        assertEquals("Node{id=3, parentId=0, failed=false}", node.toString(),
                "toString() on Node item is modified or broken");
    }
}
