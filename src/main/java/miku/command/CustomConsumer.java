package miku.command;

@FunctionalInterface
public interface CustomConsumer<T> {
    void accept(T t) throws Exception;
}
