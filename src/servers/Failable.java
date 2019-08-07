package servers;

import utils.CustomOptional;

public interface Failable {

    int getId();

    int getParentId();

    boolean isFailed();

    CustomOptional getInnerFallible(int number);

    int getSize();
}
