package sse.ngts.testrobot.exception;



public class ApplRunFailException
        extends Error
{
    private String act;
    private String msg;


    public ApplRunFailException(String act, String msg)
    {
        super(msg);
        this.act = act;
        this.msg = msg;
    }

    /**
     * NGTSTestRunFailException
     *
     * @param nGTSTestRunFailException NGTSTestRunFailException
     */
    public ApplRunFailException(ApplRunFailException
                                nGTSTestRunFailException)
    {
        this.act = nGTSTestRunFailException.getAct();
        this.msg = nGTSTestRunFailException.getMsg();
    }

    public String getAct()
    {
        return act;
    }

    public String getMsg()
    {
        return msg;
    }


}
