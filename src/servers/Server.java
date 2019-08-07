package servers;

import utils.CustomOptional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Server implements Failable {

    private int id;
    private int parentId;
    private boolean failed;
    private final List<CustomOptional<Failable>> nodes;
    private final Random random = new Random();
    private boolean transactionPassed;

    Server(int id, int parentId, int nodesCount) {
        this.id = id;
        this.parentId = parentId;
        nodes = new ArrayList<>(nodesCount);
        for (int i = 0; i < nodesCount; i++) {
            Node node = new Node(id, i);
            CustomOptional<Failable> optionalNode = new CustomOptional<>(node);
            nodes.add(optionalNode);
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
    public CustomOptional getInnerFallible(int number) {
        return nodes.get(number);
    }

    @Override
    public int getSize() {
        return nodes.size();
    }

    void failRandomNode() {
        failNodes(true);
    }

    void failAllNodes() {
        failNodes(false);
    }

    private void failNodes(boolean failRandomNode) {
        this.failed = true;
        int iteratorStart = failRandomNode ? random.nextInt(getSize()) : 0;
        for (int i = iteratorStart; i < getSize(); i++) {
            CustomOptional<Failable> tempNode = nodes.get(i);
            if (tempNode.isPresent()) {
                Node node = (Node) tempNode.get();
                node.failNode();
            }
        }
    }

    @Override
    public String toString() {
        return "\nServer{" + "id=" + id + ", failed=" + failed + ", nodes=" + nodes + '}';
    }
}
