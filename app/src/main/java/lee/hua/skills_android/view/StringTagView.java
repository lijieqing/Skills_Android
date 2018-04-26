package lee.hua.skills_android.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lee.hua.skills_android.R;
import lee.hua.skills_android.utils.DisplayUtils;

/**
 * 自定义 Tag ViewGroup，效果：将字符串集合传入，分割为Tag，并自动换行
 *
 * @author lijie
 */
public class StringTagView extends ViewGroup {
    private List<String> datas;

    private float textSize;
    private int bgColor;
    private int margin = 20;
    private int mWidth;

    private TagClickListener tagClickListener;

    public StringTagView(Context context) {
        super(context);
        init(null);
    }

    public StringTagView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public StringTagView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    /**
     * 初始化
     */
    private void init(@Nullable AttributeSet attrs) {
        if (attrs != null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.StringTagView);
            textSize = array.getDimension(R.styleable.StringTagView_tagTextSize, 14);
            bgColor = array.getColor(R.styleable.StringTagView_tagBackgroundColor, Color.LTGRAY);
            margin = array.getInteger(R.styleable.StringTagView_tagMargin, 10);
            array.recycle();
        } else {
            textSize = DisplayUtils.sp2px(getContext(),14);
            bgColor = Color.LTGRAY;
            margin = (int) DisplayUtils.dp2px(getContext(),10);
        }
        datas = new ArrayList<>();
        String[] values = new String[]{"飞狐外传", "雪山飞狐", "连城诀", "天龙八部", "射雕英雄传", "鹿鼎记",
                "笑傲江湖", "书剑恩仇录", "倚天屠龙记", "碧血剑", "鸳鸯蝴蝶梦", "神雕侠侣", "侠客行"};

        addDatas(false, Arrays.asList(values));
    }

    /**
     * 添加新数据
     *
     * @param append 是否累加
     * @param values 数据
     */
    public void addDatas(boolean append, @NonNull List<String> values) {
        if (!append) {
            datas.clear();
        }
        datas.addAll(values);
        removeAllViews();

        for (String data : datas) {
            TextView tv = new TextView(getContext());
            tv.setBackgroundColor(bgColor);
            tv.setText(data);
            tv.setTextSize(textSize);
            tv.setClickable(true);
            tv.setFocusable(true);
            tv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (tagClickListener!=null){
                        tagClickListener.onTagClickListener((TextView) v);
                    }
                }
            });
            addView(tv);
        }
    }

    /**
     * 添加标签点击监听器
     * @param tagClickListener 监听器
     */
    public void addTagClickListener(TagClickListener tagClickListener){
        this.tagClickListener = tagClickListener;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //获取到宽高
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        //测量子类
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        //设置好宽高
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (getChildCount() <= 0) {
            return;
        }
        //总行数已占用高度
        int totalHeight = margin;
        //当前行总宽度
        int totalWidth = margin;
        //当前行高度,以最大为准
        int currentLineHeight = 0;

        for (int i = 0; i < getChildCount(); i++) {
            int left, top, right, bottom;
            View child = getChildAt(i);
            //当前行宽度小于组件宽度
            if (totalWidth + child.getMeasuredWidth() < mWidth) {
                left = totalWidth;
                top = totalHeight;
                right = left + child.getMeasuredWidth();
                bottom = top + child.getMeasuredHeight();

                //记录当前行的高度
                currentLineHeight = child.getMeasuredHeight() > currentLineHeight ? child.getMeasuredHeight() : currentLineHeight;
                //当前行宽度累加
                totalWidth += child.getMeasuredWidth() + margin;
            } else {
                //重置当前宽度
                totalWidth = margin;
                //高度累加
                totalHeight += currentLineHeight + margin;

                left = totalWidth;
                top = totalHeight;
                right = left + child.getMeasuredWidth();
                bottom = top + child.getMeasuredHeight();

                //当前行宽度累加
                totalWidth += child.getMeasuredWidth() + margin;
            }
            child.layout(left, top, right, bottom);
        }
    }

    public interface TagClickListener{
        /**
         * 标签点击监听回调
         * @param textView 被点击的 view
         */
        void onTagClickListener(TextView textView);
    }
}
