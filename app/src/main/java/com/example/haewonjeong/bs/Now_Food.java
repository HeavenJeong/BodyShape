package com.example.haewonjeong.bs;

import java.util.ArrayList;

/**
 * Created by samsung on 2015-08-31.
 */
public class Now_Food {

    ArrayList<String> array_s;
    ArrayList<Integer> array_i;

    public void Now_Food()
    {
        array_s = new ArrayList<String>();
        array_i = new ArrayList<Integer>();
    }

    public void set_s(String s)
    {
        array_s.add(s);
    }

    public void set_i(int i)
    {
        array_i.add(i);
    }

    public String get_s(int id)
    {
        return array_s.get(id);
    }
    public int get_i(int id)
    {
        return array_i.get(id);
    }

}
