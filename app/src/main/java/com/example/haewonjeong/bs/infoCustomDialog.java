package com.example.haewonjeong.bs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

/**
 * Created by HaeWon Jeong on 9/25/2015.
 */
public class infoCustomDialog extends Dialog implements OnClickListener {

    Button btnback;

    public infoCustomDialog(Context context)
    {
        super(context);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.info);

        btnback = (Button)findViewById(R.id.button6);
        btnback.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        if(view ==btnback)
        {
            dismiss();
        }
    }
}
