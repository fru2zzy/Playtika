package servers;

import exceptions.NoDataException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Cluster implements Failable {

    private final Random random = new Random();
    private boolean failed;
    private final List<Server> servers = new ArrayList<Server>();


    public Cluster(int serversCount, int nodesCount) {
        for (int i = 0; i < serversCount; i++) {
            servers.add(new Server(i, i, nodesCount));
        }
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public int getParentId() {
        return 0;
    }

    @Override
    public boolean isFailed() {
        return failed;
    }

    @Override
    public Failable getInnerFailable(int number) {
        if (servers.size() > number) {
            return servers.get(number);
        } else {
            throw new NoDataException("Cannot get " + number + " inner Failable because servers size = " + servers.size());
        }
    }

    @Override
    public int getSize() {
        return servers.size();
    }

    public void sendMessage() {
        failed = true;
        if (getSize() == 0) {
            throw new NoDataException("Cannot perform search in empty servers array!");
        }
        int randomServer = random.nextInt(getSize());
        servers.get(randomServer).failRandomNode();
        if (randomServer != getSize()) {
            for (int i = randomServer + 1; i < getSize(); i++) {
                servers.get(i).failAllNodes();
            }
        }
    }

    public void sendMessage(int serverToFail, int nodeToFail) {
        failed = true;
        if (getSize() == 0) {
            throw new NoDataException("Cannot perform search in empty servers array!");
        }

        servers.get(serverToFail).failNode(nodeToFail);
        if (serverToFail != getSize()) {
            for (int i = serverToFail + 1; i < getSize(); i++) {
                servers.get(i).failAllNodes();
            }
        }
    }

    @Override
    public String toString() {
        return "Cluster{failed=" + failed + ", servers=" + servers + '}';
    }
}
