package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import servers.Cluster;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import static utils.Const.CLUSTER_JSON_FILE_NAME;

public class GsonSerializer {

    private Gson gson = new GsonBuilder().create();

    public void writeClusterToJSON(Cluster originalCluster) throws IOException {
        Writer writer = new FileWriter(CLUSTER_JSON_FILE_NAME);
        gson.toJson(originalCluster, writer);
        writer.flush();
        writer.close();
    }

    public Cluster readClusterFromJSON() throws IOException {
        FileReader reader = new FileReader(CLUSTER_JSON_FILE_NAME);
        Cluster cluster = gson.fromJson(reader, Cluster.class);
        reader.close();
        return cluster;
    }

}
