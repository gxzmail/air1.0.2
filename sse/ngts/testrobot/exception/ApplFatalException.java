package sse.ngts.testrobot.exception;



/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class ApplFatalException
        extends ApplRunFailException
{
    public ApplFatalException(String act, String msg)
    {
        super(act, msg);
    }

    public ApplFatalException(ApplRunFailException e)
    {
        super(e);
    }


}
