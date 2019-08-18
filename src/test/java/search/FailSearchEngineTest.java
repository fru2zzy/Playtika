package search;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import servers.Cluster;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.Const.DEFAULT_NODES_COUNT;
import static utils.Const.DEFAULT_SERVERS_COUNT;

@TestInstance(TestInstance.Lifecycle.PER_CLASS) // I don't want to be forced to use static in @BeforeAll
public class FailSearchEngineTest {

    private Cluster failedClusterWithLastNode;
    private Cluster failedClusterWithMidNode;
    private Cluster failedClusterWithFirstNode;
    private FailSearchEngine searchEngine;
    private int midServerIndex;
    private int midNodeIndex;
    private int lastServerIndex;
    private int lastNodeIndex;

    @BeforeAll
    void beforeClassFailSearchEngineTest() {
        failedClusterWithLastNode = new Cluster(DEFAULT_SERVERS_COUNT, DEFAULT_NODES_COUNT);
        failedClusterWithMidNode = new Cluster(DEFAULT_SERVERS_COUNT, DEFAULT_NODES_COUNT);
        failedClusterWithFirstNode = new Cluster(DEFAULT_SERVERS_COUNT, DEFAULT_NODES_COUNT);

        // Fail last server and last node
        lastServerIndex = DEFAULT_SERVERS_COUNT - 1; // -1 because index starts from 0
        lastNodeIndex = DEFAULT_NODES_COUNT - 1;
        failedClusterWithLastNode.sendMessage(lastServerIndex, lastNodeIndex);

        // Fail mid server and mid node
        midServerIndex = DEFAULT_SERVERS_COUNT / 2;
        midNodeIndex = DEFAULT_NODES_COUNT / 2;
        failedClusterWithMidNode.sendMessage(midServerIndex, midNodeIndex);

        // Fail first server and first node
        failedClusterWithFirstNode.sendMessage(0, 0);
    }

    private void verifyFindFail(Cluster cluster, int failedServer, int failedNode, int iterationsCount) {
        searchEngine = new FailSearchEngine();
        Result result = searchEngine.findFail(cluster);
        assertEquals(failedServer, result.getFailedServer(), "findFail method found incorrect failed server");
        assertEquals(failedNode, result.getFailedNode(), "findFail method found incorrect failed node");
        assertEquals(iterationsCount, result.getIterationsCount(), "The iterations count isn't correct");
    }

    private void verifyFindFailIterativelly(Cluster cluster, int failedServer, int failedNode, int iterationsCount) {
        searchEngine = new FailSearchEngine();
        Result result = searchEngine.findFailIterativelly(cluster);
        assertEquals(failedServer, result.getFailedServer(), "findFailIterativelly method found incorrect failed server");
        assertEquals(failedNode, result.getFailedNode(), "findFailIterativelly method found incorrect failed node");
        assertEquals(iterationsCount, result.getIterationsCount(), "The iterations count isn't correct");
    }

    @Test
    void findFailOfLastNodeTest() {
        verifyFindFail(failedClusterWithLastNode, lastServerIndex, lastNodeIndex, 3);
    }

    @Test
    void findFailOfLastNodeIterativellyTest() {
        verifyFindFailIterativelly(failedClusterWithLastNode, lastServerIndex, lastNodeIndex, 8);
    }

    @Test
    void findFailOfMidNodeTest() {
        verifyFindFail(failedClusterWithMidNode, midServerIndex, midNodeIndex, 3);
    }

    @Test
    void findFailOfMidNodeIterativellyTest() {
        verifyFindFailIterativelly(failedClusterWithMidNode, midServerIndex, midNodeIndex, 5);
    }

    @Test
    void findFailOfFirstNodeTest() {
        verifyFindFail(failedClusterWithFirstNode, 0, 0, 3);
    }

    @Test
    void findFailOfFirstNodeIterativellyTest() {
        verifyFindFailIterativelly(failedClusterWithFirstNode, 0, 0, 2);
    }
}