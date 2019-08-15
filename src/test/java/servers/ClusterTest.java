package servers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClusterTest {

    private static Cluster cluster;
    private static Cluster clusterToFail;
    private static Failable firstInner;
    private static Failable node;
    private final static int serversCount = 3;
    private final static int nodesCount = 5;

    @BeforeAll
    static void beforeClassClusterTest() {
        cluster = new Cluster(serversCount, nodesCount);
        clusterToFail = new Cluster(serversCount, nodesCount);
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
        assertEquals(nodesCount, firstInner.getSize(), "Size of inner objects from our Cluster inner should be equals to 5 (5 Nodes in first Server)");
    }

    @Test
    void getInnerFailableOfServer() {
        assertNotNull(node, "Node should not be null");
        assertEquals(3, node.getId(), "We've get 3rd innerFailable, so we should get node.getId() = 3");
        assertEquals(0, node.getParentId(), "We've get 3rd innerFailable from the first server, so we should get node.getParentId() = 0");
        assertEquals(0, node.getParentId(), "isFailed() from the first node should return 'false' because we didn't perform any disconnect");
    }

    @Test
    void getOutOfBoundariesInnerFailableTest() {
        try {
            cluster.getInnerFailable(99);
        } catch (Exception e) {
            assertEquals("Index: 99, Size: " + serversCount, e.getMessage(),
                    "We should receive correct IndexOutOfBoundException after attempt of getting innerFailable");
        }
    }

    @Test
    void getSizeTest() {
        assertEquals(serversCount, cluster.getSize(), "We have 3 servers in our Cluster");
    }

    @Test
    void sendMessageTest() {
        clusterToFail.sendMessage();
        assertTrue(clusterToFail.isFailed(), "After sending message we should get disconnected status (failed = true)");
        getSizeTest();

        Failable server = clusterToFail.getInnerFailable(0);
        assertNotNull(server, "Server after message sending should not be null");
        assertEquals(nodesCount, server.getSize(), "Count of nodes shoul not change after message send");
        getOutOfBoundariesInnerFailableTest();
        serverToStringTest();
        nodeToStringTest();
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
        assertEquals("Node{id=3, parentId=0, failed=false}", node.toString(), "toString() on Node item is modified or broken");
    }
}