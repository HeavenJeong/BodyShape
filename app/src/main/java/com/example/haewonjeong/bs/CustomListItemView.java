package com.example.haewonjeong.bs;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by samsung on 2015-08-19.
 */
public class CustomListItemView extends LinearLayout {

    private Context mContext;
    private Context mContext2;
    private View childView = null;
    private FoodActivity gAct;
    private Food_Search gSct;
    private CustomListInfo mItem;

    private CheckBox mCheckBox;
    private ImageView mListImage;
    private TextView mListName;
    private TextView mListKcal;

    boolean mCheckState;
    int mImageName;
    String mstrListName;
    String mstrListKcal;

    public CustomListItemView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        mContext = context;
        // gAct = (FoodActivity)mContext;
        // gSct = (Food_Search)mContext2;
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        childView = inflater.inflate(R.layout.listitem,null);
        LayoutParams ll = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        addView(childView, ll);

        mCheckBox = (CheckBox)findViewById(R.id.checkstate);
        mListImage = (ImageView)findViewById(R.id.listimage);
        mListName = (TextView)findViewById(R.id.listname);
        mListKcal = (TextView)findViewById(R.id.listkcal);
    }

    public void setMessage(CustomListInfo msg)
    {
        mItem = msg;

        mCheckState = mItem.getCheck();
        mImageName = mItem.getImageName();
        mstrListName = mItem.getListName();
        mstrListKcal = mItem.getKcal();

        if(mCheckState == true)
        {
            mCheckBox.setChecked(true);
        }
        else
        {
            mCheckBox.setChecked(false);
        }
        mListImage.setImageResource(mImageName);
        mListName.setText(mstrListName);
        mListKcal.setText(mstrListKcal);


    }


}
