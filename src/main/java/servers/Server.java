package servers;

import exceptions.NoDataException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Server implements Failable {

    private int id;
    private int parentId;
    private boolean failed;
    private final List<Node> nodes;
    private final Random random = new Random();

    Server(int id, int parentId, int nodesCount) {
        this.id = id;
        this.parentId = parentId;
        if (nodesCount >= 0) {
            nodes = new ArrayList<>(nodesCount);
        } else {
            throw new IllegalArgumentException("Cannot initialize Server by " + nodesCount + " nodes count");
        }
        for (int i = 0; i < nodesCount; i++) {
            nodes.add(new Node(id, i));
        }
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getParentId() {
        return parentId;
    }

    @Override
    public boolean isFailed() {
        return failed;
    }

    @Override
    public Failable getInnerFailable(int number) {
        if (nodes.size() > number) {
            return nodes.get(number);
        } else {
            throw new NoDataException("Cannot get " + number + " inner Failable because nodes size = " + nodes.size());
        }
    }

    @Override
    public int getSize() {
        return nodes.size();
    }

    void failRandomNode() {
        this.failed = true;
        int randomNode = random.nextInt(getSize());
        for (int i = randomNode; i < getSize(); i++) {
            nodes.get(i).failNode();
        }
    }

    void failAllNodes() {
        this.failed = true;
        for (int i = 0; i < getSize(); i++) {
            nodes.get(i).failNode();
        }
    }

    @Override
    public String toString() {
        return "\nServer{" + "id=" + id + ", failed=" + failed + ", nodes=" + nodes + '}';
    }
}
