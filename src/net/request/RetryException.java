package net.request;

public class RetryException extends Exception {

    /**
     * Field <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -2929427313014001602L;

    public RetryException ()
    {
    }

    public RetryException (String string)
    {
        super(string);
    }

}
