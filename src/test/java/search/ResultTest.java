package search;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static utils.Const.DEFAULT_NODES_COUNT;
import static utils.Const.DEFAULT_SERVERS_COUNT;

public class ResultTest {

    private static int iterationsCount = 3;


    @Test
    void setFailedServerTest() {
        Result result = new Result();
        assertTrue(result.isEmpty(), "We do not initialized Result yet, isEmpty should return true");

        result.setFailedServer(DEFAULT_SERVERS_COUNT);

        assertFalse(result.isEmpty(), "We've initialized Result by setFailedServer, isEmpty should return false");
        assertEquals(DEFAULT_SERVERS_COUNT, result.getFailedServer(),
                "We've set server #" + DEFAULT_SERVERS_COUNT + " as failed, but get another server as failed");
        assertEquals(0, result.getIterationsCount(), "The iterations count variable wasn't set, it should be 0");
        assertEquals(0, result.getFailedNode(), "We've set only 1 failed server, getFailedNode should return 0");
    }

    @Test
    void setFailedNodeTest() {
        Result result = new Result();
        assertTrue(result.isEmpty(), "We do not initialized Result yet, isEmpty should return true");

        result.setFailedNode(DEFAULT_NODES_COUNT);

        assertFalse(result.isEmpty(), "We've initialized Result by setFailedNode, isEmpty should return false");
        assertEquals(DEFAULT_NODES_COUNT, result.getFailedNode(),
                "We've set node #" + DEFAULT_NODES_COUNT + " as failed, but get another node as failed");
        assertEquals(0, result.getIterationsCount(), "The iterations count variable wasn't set, it should be 0");
        assertEquals(0, result.getFailedServer(), "We've set only 1 failed node, getFailedServer should return 0");
    }

    @Test
    void setIterationsCountTest() {
        Result result = new Result();
        assertTrue(result.isEmpty(), "We do not initialized Result yet, isEmpty should return true");

        result.setIterationsCount(iterationsCount);

        assertTrue(result.isEmpty(), "We've initialized Result by iterations count, isEmpty should return true");
        assertEquals(iterationsCount, result.getIterationsCount(), "We've set " + iterationsCount + " iterations count in the Result, but get another value");
        assertEquals(0, result.getFailedServer(), "We've set only iterations count, no servers should be failed");
        assertEquals(0, result.getFailedNode(), "We've set only iterations count, no nodes should be failed");
    }

    @Test
    void toStringTest() {
        Result result = new Result();
        result.setFailedServer(DEFAULT_SERVERS_COUNT);
        result.setFailedNode(DEFAULT_NODES_COUNT);
        result.setIterationsCount(iterationsCount);
        assertEquals("Failed items were: {failedServer=" + DEFAULT_SERVERS_COUNT + ", failedNode=" + DEFAULT_NODES_COUNT + "}",
                result.toString(), "toString() on Result item is modified or broken");
    }
}