package ac.hurley.ai.view.custom;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.Scroller;

import androidx.annotation.NonNull;

/**
 * <pre>
 *      @author hurley
 *      date    : 2020/11/15 22:58
 *      github  : https://github.com/HurleyJames
 *      desc    :
 * </pre>
 */
public class ScrollLayout extends FrameLayout {

    private static final String TAG = "ScrollLayout";

    Scroller mScroller;

    public ScrollLayout(@NonNull Context context) {
        super(context);
        mScroller = new Scroller(context);
    }

    /**
     * 判断滚动是否结束
     */
    @Override
    public void computeScroll() {
        // 通过computeScrollOffset方法判断滚动是否完成
        if (mScroller.computeScrollOffset()) {
            // 滚动到此，View应该滚动到x,y坐标上
            this.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            // 请求重绘该View，从而又会导致computeScroll被调用，然后继续滚动，直到computeScrollOffset返回false
            this.postInvalidate();
        }
    }

    /**
     * 调用这个方法进行滚动，这里只滚动竖直方向
     *
     * @param y
     */
    public void scrollTo(int y) {
        // 参数1和参数2分别为滚动的起始点水平、竖直方向的滚动偏移量
        // 参数3和参数4为在水平和竖直方向上滚动的距离
        mScroller.startScroll(getScrollX(), getScrollY(), 0, y);
        // 实现View的重绘
        this.invalidate();
    }


}
