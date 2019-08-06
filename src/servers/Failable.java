package servers;

public interface Failable {

    int getId();

    int getParentId();

    boolean isFailed();

    Failable getInnerFallible(int number);

    int getSize();
}
