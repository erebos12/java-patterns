package net.request;

public interface Request {

    public abstract Response send() throws RetryException;
}
