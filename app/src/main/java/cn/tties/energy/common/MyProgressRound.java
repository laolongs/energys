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
 *
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

public class MyProgressRound extends View{

    Paint paint;
    Paint paintCenter;
    Paint paintMin;

    private float mProgressMax = 0;
    private float mProgressCenter = 0;
    private float mProgressMin = 0;
    private int mCountProgress = 0;

    private float mRadiuSize = 0;
    private float mRingSize = 0;
    private float mTextSize = 0;
    private int mProgressColor = 0;
    private float mNumdistance = 0;
    private float mScoredistance = 0;

    public MyProgressRound(Context context) {
        super(context);
        init();
    }

    /**
     * 所有在xml布局文件中 标签里面声明的属性 都可以在AttributeSet类的对象中获取出来
     * @param context
     * @param attrs
     */
    public MyProgressRound(Context context, @Nullable AttributeSet attrs) {
        //在该构造方法中可以获取到  所有参数
        //把参数传出去  在onDraw方法中可以操作  onMeasure中也可以操作
        super(context, attrs);
        getCustomAttr(context, attrs);
        init();
    }

    private void getCustomAttr(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MyProgressRound);
        mRadiuSize = array.getDimension(R.styleable.MyProgressRound_radiuSize, 190);
        mRingSize = array.getDimension(R.styleable.MyProgressRound_ringSize, 15);
        mTextSize = array.getDimension(R.styleable.MyProgressRound_textSize, 10);
        mProgressColor = array.getColor(R.styleable.MyProgressRound_progressColor, Color.BLACK);
        //中间圆距最大圆的距离
        mNumdistance = array.getDimension(R.styleable.MyProgressRound_numdistance, 10);
        //最小圆距最大圆的距离
        mScoredistance = array.getDimension(R.styleable.MyProgressRound_scoredistance, 10);

    }


    public MyProgressRound(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init(){
        Paint.Cap round = Paint.Cap.ROUND;
        paint = new Paint();
        paint.setStrokeCap(round);
        paint.setAntiAlias(true);
        paintCenter = new Paint();
        paintCenter.setStrokeCap(round);
        paintCenter.setAntiAlias(true);
        paintMin = new Paint();
        paintMin.setStrokeCap(round);
        paintMin.setAntiAlias(true);
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
        int paintMaxcolor = Color.parseColor("#515B5E");
        int paintMaxRadiucolor = Color.parseColor("#FFCA57");
        int paintCentercolor = Color.parseColor("#44596E");
        int paintCenterRadiucolor = Color.parseColor("#74BDFF");
        int paintMincolor = Color.parseColor("#435E60");
        int paintMinRadiucolor = Color.parseColor("#6BE96A");

        paint.setStrokeWidth(mRingSize);
        paint.setColor(paintMaxcolor);
        paint.setStyle(Paint.Style.STROKE);
        paintCenter.setStrokeWidth(mRingSize);
        paintCenter.setColor(paintCentercolor);
        paintCenter.setStyle(Paint.Style.STROKE);
        paintMin.setStrokeWidth(mRingSize);
        paintMin.setColor(paintMincolor);
        paintMin.setStyle(Paint.Style.STROKE);
//        canvas.drawCircle(getMeasuredWidth()/2, getMeasuredHeight()/2, mRadiuSize, paint);
//        canvas.drawCircle(getMeasuredWidth()/2, getMeasuredHeight()/2, mRadiuSize-mNumdistance, paintCenter);
//        canvas.drawCircle(getMeasuredWidth()/2, getMeasuredHeight()/2, mRadiuSize-mScoredistance, paintMin);
        RectF rectF = new RectF(getMeasuredWidth()/2 - mRadiuSize,getMeasuredHeight()/2 - mRadiuSize,getMeasuredWidth()/2 + mRadiuSize  ,getMeasuredHeight()/2 + mRadiuSize);
        paint.setStrokeWidth(mRingSize);
//        paint.setColor(paintMaxRadiucolor);
//        canvas.drawArc(rectF, -90,  mProgressMax, false, paint);
        RectF rectF2 = new RectF(getMeasuredWidth()/2 - mRadiuSize +mNumdistance,getMeasuredHeight()/2 - mRadiuSize +mNumdistance,getMeasuredWidth()/2 + mRadiuSize - mNumdistance,getMeasuredHeight()/2 + mRadiuSize - mNumdistance);
        paintCenter.setStrokeWidth(mRingSize);
//        paintCenter.setColor(paintCenterRadiucolor);
//        canvas.drawArc(rectF2, -90,  mProgressCenter, false, paintCenter);
        RectF rectF3 = new RectF(getMeasuredWidth()/2 - mRadiuSize +mScoredistance,getMeasuredHeight()/2 - mRadiuSize +mScoredistance,getMeasuredWidth()/2 + mRadiuSize - mScoredistance,getMeasuredHeight()/2 + mRadiuSize - mScoredistance);
        paintMin.setStrokeWidth(mRingSize);
//        paintMin.setColor(paintMinRadiucolor);
//        canvas.drawArc(rectF3, -90,  mProgressMin, false, paintMin);
//        确定最大 最小
        if(mProgressMax>mProgressCenter&&mProgressMax>mProgressMin){
            canvas.drawCircle(getMeasuredWidth()/2, getMeasuredHeight()/2, mRadiuSize, paint);
            paint.setColor(paintMaxRadiucolor);
            canvas.drawArc(rectF, -90,  mProgressMax, false, paint);
        }
        if(mProgressMax<mProgressCenter&&mProgressMax<mProgressMin){
            canvas.drawCircle(getMeasuredWidth()/2, getMeasuredHeight()/2, mRadiuSize-mScoredistance, paint);
            paint.setColor(paintMaxRadiucolor);
            canvas.drawArc(rectF3, -90,  mProgressMax, false, paint);
        }
        if(mProgressCenter>mProgressMax&&mProgressCenter>mProgressMin){
            canvas.drawCircle(getMeasuredWidth()/2, getMeasuredHeight()/2, mRadiuSize, paintCenter);
            paintCenter.setColor(paintCenterRadiucolor);
            canvas.drawArc(rectF, -90,  mProgressCenter, false, paintCenter);
        }
        if(mProgressCenter<mProgressMax&&mProgressCenter<mProgressMin){
            canvas.drawCircle(getMeasuredWidth()/2, getMeasuredHeight()/2, mRadiuSize-mScoredistance, paintCenter);
            paintCenter.setColor(paintCenterRadiucolor);
            canvas.drawArc(rectF3, -90,  mProgressCenter, false, paintCenter);
        }
        if(mProgressMin>mProgressMax&&mProgressMin>mProgressCenter){
            canvas.drawCircle(getMeasuredWidth()/2, getMeasuredHeight()/2, mRadiuSize, paintMin);
            paintMin.setColor(paintMinRadiucolor);
            canvas.drawArc(rectF, -90,  mProgressMin, false, paintMin);
        }
        if(mProgressMin<mProgressMax&&mProgressMin<mProgressCenter){
            canvas.drawCircle(getMeasuredWidth()/2, getMeasuredHeight()/2, mRadiuSize-mScoredistance, paintMin);
            paintMin.setColor(paintMinRadiucolor);
            canvas.drawArc(rectF3, -90,  mProgressMin, false, paintMin);
        }
        //确定中间
        if(mProgressMax>mProgressCenter&&mProgressMax<mProgressMin||mProgressMax<mProgressCenter&&mProgressMax>mProgressMin){
            canvas.drawCircle(getMeasuredWidth()/2, getMeasuredHeight()/2, mRadiuSize-mNumdistance, paint);
            paint.setColor(paintMaxRadiucolor);
            canvas.drawArc(rectF2, -90,  mProgressMax, false, paint);
        }
        if(mProgressCenter>mProgressMax&&mProgressCenter<mProgressMin||mProgressCenter<mProgressMax&&mProgressCenter>mProgressMin){
            canvas.drawCircle(getMeasuredWidth()/2, getMeasuredHeight()/2, mRadiuSize-mNumdistance, paintCenter);
            paintCenter.setColor(paintCenterRadiucolor);
            canvas.drawArc(rectF2, -90,  mProgressCenter, false, paintCenter);
        }
        if(mProgressMin>mProgressMax&&mProgressMin<mProgressCenter||mProgressMin<mProgressMax&&mProgressMin>mProgressCenter){
            canvas.drawCircle(getMeasuredWidth()/2, getMeasuredHeight()/2, mRadiuSize-mNumdistance, paintMin);
            paintMin.setColor(paintMinRadiucolor);
            canvas.drawArc(rectF2, -90,  mProgressMin, false, paintMin);
        }
//        RectF rectF = new RectF(getMeasuredWidth()/2 - mRadiuSize,getMeasuredHeight()/2 - mRadiuSize,getMeasuredWidth()/2 + mRadiuSize  ,getMeasuredHeight()/2 + mRadiuSize);
//        paint.setStrokeWidth(mRingSize);
//        paint.setColor(paintMaxRadiucolor);
//        canvas.drawArc(rectF, -90,  mProgressMax, false, paint);
//        RectF rectF2 = new RectF(getMeasuredWidth()/2 - mRadiuSize +mNumdistance,getMeasuredHeight()/2 - mRadiuSize +mNumdistance,getMeasuredWidth()/2 + mRadiuSize - mNumdistance,getMeasuredHeight()/2 + mRadiuSize - mNumdistance);
//        paintCenter.setStrokeWidth(mRingSize);
//        paintCenter.setColor(paintCenterRadiucolor);
//        canvas.drawArc(rectF2, -90,  mProgressCenter, false, paintCenter);
//        RectF rectF3 = new RectF(getMeasuredWidth()/2 - mRadiuSize +mScoredistance,getMeasuredHeight()/2 - mRadiuSize +mScoredistance,getMeasuredWidth()/2 + mRadiuSize - mScoredistance,getMeasuredHeight()/2 + mRadiuSize - mScoredistance);
//        paintMin.setStrokeWidth(mRingSize);
//        paintMin.setColor(paintMinRadiucolor);
//        canvas.drawArc(rectF3, -90,  mProgressMin, false, paintMin);
    }
    public void setAllPregerssData(int progress1,int progress2,int progress3,double num){
        mProgressMax = (float) ((360/num)*progress1);
        mProgressCenter = (float) ((360/num)*progress2);
        mProgressMin = (float) ((360/num)*progress3);
        invalidate();
    }
//    public void setProgressMax(int progress,double num){
//        mProgressMax = (float) ((360/num)*progress);
//        mCountProgress = progress*100/360;
//        invalidate();
//    }
//    public void setProgressCenter(int progress,double num){
//        mProgressCenter =(float) ((360/num)*progress);
//        mCountProgress = progress*100/360;
//        invalidate();
//    }
//    public void setProgressMin(int progress,double num){
//        mProgressMin = (float) ((360/num)*progress);
//        mCountProgress = progress*100/360;
//        invalidate();
//    }
}
