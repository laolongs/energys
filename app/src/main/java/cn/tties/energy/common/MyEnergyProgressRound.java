package cn.tties.energy.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import cn.tties.energy.R;

/**
 * 在xml里面可以设置 进度圆环的属性
 * 1、颜色
 * 2、文本大小
 * 3、粗细
 * 4、圆环半径
 *
 * ***** values文件夹里面  创建一个attrs.xml
 * ***** 命名控件 namesp ?
 * <>
 *     <attr name="layout_width" format=""><attr/>
 * </>
 */

public class MyEnergyProgressRound extends View{

    Paint paint;
    Paint painttext;
    Paint paintCenter;
    Paint paintMin;

    private int mProgressMax = 0;
    private int mProgressCenter = 0;
    private int mProgressMin = 0;
    private int mCountProgress = 0;

    private float mRadiuSize = 0;
    private float mRingSize = 0;
    private float mRingSizetwo = 0;
    private float mTextSize = 0;
    private int mProgressColor = 0;


    public MyEnergyProgressRound(Context context) {
        super(context);
        init();
    }

    /**
     * 所有在xml布局文件中 标签里面声明的属性 都可以在AttributeSet类的对象中获取出来
     * @param context
     * @param attrs
     */
    public MyEnergyProgressRound(Context context, @Nullable AttributeSet attrs) {
        //在该构造方法中可以获取到  所有参数
        //把参数传出去  在onDraw方法中可以操作  onMeasure中也可以操作
        super(context, attrs);
        getCustomAttr(context, attrs);
        init();
    }

    private void getCustomAttr(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MyProgressRound);
        mRadiuSize = array.getDimension(R.styleable.MyProgressRound_radiuSize, 130);
        mRingSize = array.getDimension(R.styleable.MyProgressRound_ringSize, 10);
        mRingSizetwo = array.getDimension(R.styleable.MyProgressRound_ringSizetwo, 10);
        mTextSize = array.getDimension(R.styleable.MyProgressRound_textSize, 10);
        mProgressColor = array.getColor(R.styleable.MyProgressRound_progressColor, Color.BLACK);

    }


    public MyEnergyProgressRound(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init(){
        Paint.Cap cap=Paint.Cap.ROUND;
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeCap(cap);
        painttext = new Paint();
        painttext.setAntiAlias(true);

    }

    //widthMeasureSpec／heightMeasureSpec 是一个32为的int类型
    //01000000 00000000 00000000 00000000
    //高两位 代表类型
    //warpcontent类型 MeasureSpec.AT_MOST
    //matchparent类型 MeasureSpec.EXACTLY 或者具体的长度 100dp 200dp
    // 其他类型
    //
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = 0;
        int height = 0;
        if(widthMode == MeasureSpec.AT_MOST){
            width = (int) (mRadiuSize * 2);
        }else{
            width = Math.max(widthSize, (int) (mRadiuSize * 2));
        }

        if(heightMode == MeasureSpec.AT_MOST){
            height = (int) (mRadiuSize * 2);
        }else{
            height = Math.max(heightSize, (int) (mRadiuSize * 2));
        }
        setMeasuredDimension(width, height);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        //在布局文件中设置的圆环半径大小就可以不用写死
        int paintMaxcolor = Color.parseColor("#649FE3");
        int paintMaxRadiucolor = Color.parseColor("#BFDDFF");

        paint.setStrokeWidth(mRingSize);
        paint.setColor(paintMaxcolor);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(getMeasuredWidth()/2, getMeasuredHeight()/2, mRadiuSize, paint);


        painttext.setTextSize(mTextSize);
        painttext.setStrokeWidth(mRingSize);
        painttext.setColor(paintMaxRadiucolor);
        String text = mCountProgress+"";
        float textWidth = painttext.measureText(text);
        canvas.drawText(text, getMeasuredWidth()/2-textWidth/2-30 , getMeasuredHeight()/2 + mTextSize/2-10, painttext);
        painttext.setTextSize(35);
        painttext.setStrokeWidth(mRingSizetwo);
        String text2 = "分";
        float textWidth2 = painttext.measureText(text);
        canvas.drawText(text2, getMeasuredWidth()/2-textWidth2/2 +60, getMeasuredHeight()/2 + mTextSize/2-10, painttext);
//
        RectF rectF = new RectF(getMeasuredWidth()/2 - mRadiuSize,getMeasuredHeight()/2 - mRadiuSize,getMeasuredWidth()/2 + mRadiuSize  ,getMeasuredHeight()/2 + mRadiuSize);
        paint.setStrokeWidth(mRingSizetwo);
        paint.setColor(paintMaxRadiucolor);
        canvas.drawArc(rectF, -90, mProgressMax, false, paint);
    }
    public void setProgressMax(int progress){
        mProgressMax = (360/100)*progress;
        mCountProgress = progress;
        invalidate();

    }

}
