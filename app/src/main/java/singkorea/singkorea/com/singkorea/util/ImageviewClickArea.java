package singkorea.singkorea.com.singkorea.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.Collections;

import static android.graphics.Bitmap.createBitmap;


public class ImageviewClickArea extends android.support.v7.widget.AppCompatImageView {

    public ImageviewClickArea(Context context) {
        super(context);
    }

    public ImageviewClickArea(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageviewClickArea(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public View.OnClickListener onMyClickListener;
    public void setMyOnClickListener(View.OnClickListener onClickLisner) {
        this.onMyClickListener = onClickLisner;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        return super.onTouchEvent(event);
//
//        if (x >= xOfYourBitmap && x < (xOfYourBitmap + yourBitmap.getWidth())
//                && y >= yOfYourBitmap && y < (yOfYourBitmap + yourBitmap.getHeight())) {
//            //tada, if this is true, you've started your click inside your bitmap
//        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                Log.e("test", "ACTION_DOWN ");

                Bitmap bitmap = createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                draw(canvas);
                int x = (int) event.getX();
                int y = (int) event.getY();

                if(x > bitmap.getWidth() || y > bitmap.getHeight() ) {
                    Log.e("test", "벗어난 영역 ??");
                    break;
                }

                if (bitmap.getPixel(x, y) != Color.TRANSPARENT) {
                    Log.e("test", "투명색도 아님, 고로 내영역임." + bitmap.getPixel(x, y));

                    if(onMyClickListener != null) {
                        onMyClickListener.onClick(this);
                    }
                    return true;
                } else {
                    Log.e("test", "투명색임");
                }
                break;
            }
            case MotionEvent.ACTION_UP:
                break;
        }
        return false;
        //return false;
    }

    public static <T> Iterable<T> emptyIfNull(Iterable<T> iterable) {
        return iterable == null ? Collections.emptyList() : iterable;
    }
}
