package sse.ngts.testrobot.application.component;

public abstract class XFTableBO
{

    /*****************************************************************************
     * Function:  getItems
     * ------------------------------------------------------------------------
        /**
      * ����Table BO �е�ֵ����
      * @return  ����Table BO �е�ֵ����
      */
     /*****************************************************************************/
     public abstract Object[] getItems();

     public abstract String getTableField(int col);

}