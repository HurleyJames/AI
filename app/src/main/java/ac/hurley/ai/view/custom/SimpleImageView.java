package ac.hurley.ai.view.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.blankj.utilcode.util.ImageUtils;

import java.util.jar.Attributes;

import ac.hurley.ai.R;

/**
 * <pre>
 *      @author hurley
 *      date    : 2020/11/15 21:28
 *      github  : https://github.com/HurleyJames
 *      desc    :
 * </pre>
 */
public class SimpleImageView extends View {


    /**
     * 对于继承至View类的自定义控件来说，核心的步骤分别为onLayout、onMeasure、onDraw等
     *
     * 1. 继承自View，创建自定义控件
     * 2. 如果有自定义View的属性，也就是在values/attrs.xml中定义属性集
     * 3. 在xml中引入命名文件，设置属性
     * 4. 在代码中读取xml中的属性，初始化视图
     * 5. 测量视图的大小
     * 6. 绘制视图的内容
     */

    /**
     * 画笔
     */
    private Paint mBitmapPaint;
    /**
     * 图片Drawable
     */
    private Drawable mDrawable;
    /**
     * View的宽
     */
    private int mWidth;
    /**
     * View的长
     */
    private int mHeight;

    public SimpleImageView(Context context) {
        super(context);
    }

    public SimpleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 根据属性初始化
        initAttrs(attrs);
        // 初始化画笔
        mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);
    }

    /**
     * 初始化属性
     *
     * @param attrs
     */
    private void initAttrs(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray array = null;
            try {
                // 在value/attr.xml中定义了这个View的属性
                array = getContext().obtainStyledAttributes(attrs, R.styleable.SimpleImageView);
                // 根据图片id获取到Drawable对象
                mDrawable = array.getDrawable(R.styleable.SimpleImageView_src);
                // 测量Drawable对象的宽、高
                measureDrawable();
            } finally {
                if (array != null) {
                    array.recycle();
                }
            }
        }
    }

    private void measureDrawable() {
        if (mDrawable == null) {
            throw new RuntimeException("drawable不能为空！");
        }
        mWidth = mDrawable.getIntrinsicWidth();
        mHeight = mDrawable.getIntrinsicHeight();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //设置View的宽和高
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mDrawable == null) {
            return;
        }
        // 绘制图片
        canvas.drawBitmap(ImageUtils.drawable2Bitmap(mDrawable), getLeft(), getRight(), mBitmapPaint);
    }
}
