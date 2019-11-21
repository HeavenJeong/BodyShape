package com.example.haewonjeong.bs;

/**
 * Created by samsung on 2015-08-19.
 */
public class CustomListInfo {

    private boolean mChecked;
    private int mImageName;
    private String mListName;
    private String mKcal;

    public CustomListInfo(boolean _check, int _Imagename, String _listname, String _kcal)
    {
        this.mChecked = _check;
        this.mImageName = _Imagename;
        this.mListName = _listname;
        this.mKcal = _kcal;
    }

    public boolean getCheck()
    {
        return mChecked;
    }
    public void setCheck(boolean i)
    {
        if(i == true)
        {
            mChecked = true;
        }
        else
        {
            mChecked = false;
        }
    }
    public int getImageName()
    {
        return mImageName;
    }
    public String getListName()
    {
        return mListName;
    }
    public String getKcal()
    {
        return mKcal;
    }
}
