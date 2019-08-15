package servers;

public interface Failable {

    int getId();

    int getParentId();

    boolean isFailed();

    Failable getInnerFailable(int number);

    int getSize();
}
