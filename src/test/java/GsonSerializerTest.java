import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import servers.Cluster;
import utils.GsonSerializer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static utils.Const.*;

class GsonSerializerTest {

    private static GsonSerializer serializer;
    private static Cluster cluster;

    @BeforeAll
    static void beforeClassGsonSerializerTest() throws IOException {
        cluster = new Cluster(DEFAULT_SERVERS_COUNT, DEFAULT_NODES_COUNT);
        serializer = new GsonSerializer();

        serializer.writeClusterToJSON(cluster);
    }

    @AfterAll
    static void afterClassGsonSerializerTest() {
        File file = new File(CLUSTER_JSON_FILE_NAME);
        file.delete();
    }

    @Test
    void writeClusterToJsonTest() throws IOException {
        File file = new File(CLUSTER_JSON_FILE_NAME);
        assertTrue(file.exists(), "File '" + CLUSTER_JSON_FILE_NAME + "' is missing in the root directory");

        String clusterFileText;
        try (BufferedReader br = new BufferedReader(new FileReader(CLUSTER_JSON_FILE_NAME))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            clusterFileText = sb.toString();
        }

        JSONObject clusterJson = new JSONObject(clusterFileText);

        JSONArray servers = clusterJson.getJSONArray("servers");
        assertEquals(DEFAULT_SERVERS_COUNT, servers.length(), "We should have " + DEFAULT_SERVERS_COUNT + " servers in our " + CLUSTER_JSON_FILE_NAME);

        JSONObject firstServer = servers.getJSONObject(0);
        assertEquals(DEFAULT_NODES_COUNT, firstServer.length(), "We should have " + DEFAULT_NODES_COUNT + " Servers in our " + CLUSTER_JSON_FILE_NAME);
        assertEquals(0, firstServer.getInt("id"), "We should have id = 0 of the first Server object in our " + CLUSTER_JSON_FILE_NAME);

        JSONArray nodes = firstServer.getJSONArray("nodes");
        assertEquals(DEFAULT_NODES_COUNT, nodes.length(), "We should have " + DEFAULT_NODES_COUNT + " Nodes in our " + DEFAULT_NODES_COUNT);

        JSONObject firstNode = nodes.getJSONObject(0);
        assertEquals(0, firstNode.getInt("id"), "We should have id = 0 of the first Node object in our " + CLUSTER_JSON_FILE_NAME);
        assertEquals(0, firstNode.getInt("parentId"), "We should have parentId = 0 of the first Node object in our " + CLUSTER_JSON_FILE_NAME);
        assertFalse(firstNode.getBoolean("failed"), "We should have failed = false of the first Node object in our " + CLUSTER_JSON_FILE_NAME);
    }

    @Test
    void readClusterFromJsonTest() throws IOException {
        Cluster clusterFromFile = serializer.readClusterFromJSON();
        assertNotNull(clusterFromFile, "Cluster from " + CLUSTER_JSON_FILE_NAME + " should not be null");
        assertEquals(DEFAULT_SERVERS_COUNT, clusterFromFile.getSize(), "Cluster Servers count from " +
                CLUSTER_JSON_FILE_NAME + " file should be " + DEFAULT_SERVERS_COUNT);
        assertEquals(cluster.toString(), clusterFromFile.toString(), "Cluster from file " + CLUSTER_JSON_FILE_NAME +
                " after de-serialization should be equal to the original file before serialization");
    }
}