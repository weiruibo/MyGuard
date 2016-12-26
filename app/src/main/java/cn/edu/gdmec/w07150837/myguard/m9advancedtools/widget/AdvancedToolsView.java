package cn.edu.gdmec.w07150837.myguard.m9advancedtools.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.edu.gdmec.w07150837.myguard.R;

/**
 * Created by weiruibo on 12/23/16.
 */

public class AdvancedToolsView extends RelativeLayout {

    private TextView mDesriptionTV;
    private String desc = "";
    private Drawable drawable;
    private ImageView mLeftImgv;


    public AdvancedToolsView(Context context) {
        super(context);
        init(context);
    }


    public AdvancedToolsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public AdvancedToolsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.AdvancedToolsView);
        desc = mTypedArray.getString(R.styleable.AdvancedToolsView_desc);
        drawable = mTypedArray.getDrawable(R.styleable.AdvancedToolsView_android_src);
        mTypedArray.recycle();
        init(context);
    }

    /**
     * 控件初始化
     *
     * @param context
     */
    private void init(Context context) {
        //将资源转化成为view对象显示在自己身上

        View view = View.inflate(context, R.layout.ui_advancedtools_view, null);
        this.addView(view);
        mDesriptionTV = (TextView) findViewById(R.id.tv_decription);
        mLeftImgv = (ImageView) findViewById(R.id.imgv_left);
        mDesriptionTV.setText(desc);
        if (drawable != null) {
            mLeftImgv.setImageDrawable(drawable);
        }
    }
}
