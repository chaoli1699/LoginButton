package cn.cienet.loginbutton.weight;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import cn.cienet.loginbutton.R;

public class LoginButton extends View {

	private Paint mPaint;
    private Paint okPaint;
    private Paint textPaint;
    private int mColor;
    private int okColor;
    private int textColor;
    private int width;
    private int height;
    private int paddingLeft;
    private int paddingTop;
    private int paddingRight;
    private int paddingBottom;
    private ValueAnimator rect2RoundAnimation;
    private static final int rect2RoundDuration=1000*2;
    private ValueAnimator round2CircleAnimation;
    private static final int round2CircleDuration=1000*2;
    private ValueAnimator drawOkAnimation;
    private static final int drawOkDuration=1000*1;
    private ObjectAnimator moveUpAnimation;
    private static final int moveUpDuraton=1000*1;
    private PathMeasure pathMeasure;
    private PathEffect pathEffect;
    private float pathValue;
    private boolean startDrawOk=false;
    private Path okPath;
    private int rect2CircleAngle;
    private int defaultCircleDistance;
    private int circleDistance;
    private int moveDistance=250;
    private String btnString;
    private AnimatorSet animatorSet=new AnimatorSet();
    private AnimationBtnListener animationBtnListener=null;

    public void setAnimationBtnListener(AnimationBtnListener animationBtnListener){
        this.animationBtnListener=animationBtnListener;
    }

    public LoginButton(Context context) {
        this(context,null);
    }

    public LoginButton(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoginButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a=context.obtainStyledAttributes(attrs, R.styleable.LoginBtn);
        mColor=a.getColor(R.styleable.LoginBtn_main_color, Color.BLUE);
        okColor=a.getColor(R.styleable.LoginBtn_ok_color,Color.RED);
        textColor=a.getColor(R.styleable.LoginBtn_text_color,Color.WHITE);
        btnString=a.getString(R.styleable. LoginBtn_btn_string);

        String defaultBtnString=context.getResources().getString(R.string.defaule_button_name);
        btnString=(btnString==null)? defaultBtnString:btnString;
        a.recycle();

        initPaint();

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (animationBtnListener!=null){
                    animationBtnListener.onClick();
                }
            }
        });

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (animationBtnListener!=null){
                    animationBtnListener.onAnimationFinish();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void initPaint(){
        mPaint=new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth((float) 4.0);
        mPaint.setColor(mColor);
        mPaint.setAntiAlias(true);

        okPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        okPaint.setColor(okColor);
        okPaint.setAntiAlias(true);
        okPaint.setStrokeWidth((float) 10.0);
        okPaint.setStyle(Paint.Style.STROKE);

        textPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(textColor);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize((float) 40.0);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode= MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize= MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode=MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize=MeasureSpec.getSize(heightMeasureSpec);

        if (widthSpecMode==MeasureSpec.AT_MOST&&heightSpecMode==MeasureSpec.AT_MOST){
            setMeasuredDimension(200,200);
        }else if (widthSpecMode==MeasureSpec.AT_MOST){
            setMeasuredDimension(200,heightSpecSize);
        }else if (widthMeasureSpec==MeasureSpec.AT_MOST){
            setMeasuredDimension(widthSpecSize,200);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paddingLeft=getPaddingLeft();
        paddingRight=getPaddingRight();
        paddingTop=getPaddingTop();
        paddingBottom=getPaddingBottom();

        width=getWidth()-paddingLeft-paddingRight;
        height=getHeight()-paddingTop-paddingBottom;

        drawRoundRect(canvas);
        drawText(canvas);

        if (startDrawOk){
            canvas.drawPath(okPath,okPaint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        width=w;
        height=h;

        defaultCircleDistance=(w-h)/2;

        initOkPath();
        initAnimation();
    }

    private void drawRoundRect(Canvas canvas){

        RectF rectF=new RectF();
        rectF.left=paddingLeft+circleDistance;
        rectF.top=paddingTop;
        rectF.right=paddingLeft+width-circleDistance;
        rectF.bottom=paddingTop+height;
        canvas.drawRoundRect(rectF,rect2CircleAngle,rect2CircleAngle,mPaint);
    }

    private void drawText(Canvas canvas){
        Rect textRect=new Rect();
        textRect.left=paddingLeft;
        textRect.top=paddingTop;
        textRect.right=paddingLeft+width;
        textRect.bottom=paddingTop+height;
        Paint.FontMetricsInt fontMetricsInt=textPaint.getFontMetricsInt();
        int baseline=(textRect.top+textRect.bottom-fontMetricsInt.top-fontMetricsInt.bottom)/2;

        canvas.drawText(btnString,textRect.centerX(),baseline,textPaint);
    }

    private void initOkPath(){

        okPath=new Path();
        okPath.moveTo(defaultCircleDistance+height*3/11,height*2/5);
        okPath.lineTo(defaultCircleDistance+height*3/7,height*3/5);
        okPath.lineTo(defaultCircleDistance+height*3/4,height*3/10);

        pathMeasure=new PathMeasure(okPath,true);
    }

    private void initAnimation(){
        setRrect2RoundAnimation();
        setRound2CircleAnimation();
        setMoveUpAnimation();
        setDrawOkAnimation();

        animatorSet.play(moveUpAnimation)
                .before(drawOkAnimation)
                .after(round2CircleAnimation)
                .after(rect2RoundAnimation);
    }

    private void setRrect2RoundAnimation(){
        rect2RoundAnimation=ValueAnimator.ofInt(0,height/2);
        rect2RoundAnimation.setDuration(rect2RoundDuration);
        rect2RoundAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                rect2CircleAngle=(Integer) animation.getAnimatedValue();
                invalidate();
            }
        });
    }

    private void setRound2CircleAnimation(){
        round2CircleAnimation=ValueAnimator.ofInt(0,defaultCircleDistance);
        round2CircleAnimation.setDuration(round2CircleDuration);
        round2CircleAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                circleDistance=(Integer)animation.getAnimatedValue();

                int alpha=255-(circleDistance*255)/defaultCircleDistance;
                textPaint.setAlpha(alpha);

                invalidate();
            }
        });
    }

    private void setDrawOkAnimation(){
        drawOkAnimation=ValueAnimator.ofFloat(1,0);
        drawOkAnimation.setDuration(drawOkDuration);
        drawOkAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                startDrawOk=true;
                pathValue=(Float)animation.getAnimatedValue();

                pathEffect=new DashPathEffect(
                        new float[]{pathMeasure.getLength(),pathMeasure.getLength()}
                        ,pathValue*pathMeasure.getLength());
                okPaint.setPathEffect(pathEffect);
                invalidate();
            }
        });
    }

    private void setMoveUpAnimation(){
        final float currentTranslationY=this.getTranslationY();
        moveUpAnimation= ObjectAnimator.ofFloat(this,"TranslationY",currentTranslationY,currentTranslationY-moveDistance);
        moveUpAnimation.setDuration(moveUpDuraton);
        moveUpAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
    }

    public void startAnim(){
        animatorSet.start();
    }

    public interface AnimationBtnListener{
        void onClick();
        void onAnimationFinish();
    }
}
