package servers;

import exceptions.NoDataException;
import utils.CustomOptional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Cluster implements Failable {

    private final Random random = new Random();
    private boolean failed;
    private final List<CustomOptional<Failable>> servers = new ArrayList<>();


    public Cluster(int serversCount, int nodesCount) {
        for (int i = 0; i < serversCount; i++) {
            Server server = new Server(i, i, nodesCount);
            CustomOptional<Failable> optionalServer = new CustomOptional<>(server);
            servers.add(optionalServer);
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
    public CustomOptional getInnerFallible(int number) {
        return servers.get(number);
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
        CustomOptional<Failable> optionalServer = servers.get(randomServer);
        if (optionalServer.isPresent()) {
            Server server = (Server) optionalServer.get();
            server.failRandomNode();
        }
        if (randomServer != getSize()) {
            for (int i = randomServer + 1; i < getSize(); i++) {
                optionalServer = servers.get(i);
                if (optionalServer.isPresent()) {
                    Server server = (Server) optionalServer.get();
                    server.failAllNodes();
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Cluster{failed=" + failed + ", servers=" + servers + '}';
    }
}
