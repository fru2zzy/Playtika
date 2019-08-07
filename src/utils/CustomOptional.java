package utils;

import java.util.Random;

public class CustomOptional<T> {

    private static final Random RANDOM = new Random();
    private final T value;

    public CustomOptional(T value) {
        if (RANDOM.nextBoolean()) {
            this.value = value;
        } else {
            this.value = null;
        }
    }

    public T get() {
        if (value == null) {
            throw new IllegalArgumentException();
        }
        return value;
    }

    public boolean isPresent() {
        return value != null;
    }

    public static CustomOptional empty() {
        return new CustomOptional<>(null);
    }

    @Override
    public String toString() {
        return "CustomOptional{value=" + value + '}';
    }
}
