
/*
Simple project for the emoji keyboard service.
 It is free to use this file and associated files
 for the educational use only as long as this block exist
 unchanged. For the commercial use, 
 contact with auther/developer of this project.
 Developer: Win Aung Cho, winaungcho@gmail.com
            StructSoftLab.com
            21-Oct, 2019
 LICENSE:  https://github.com/winaungcho/android/blob/master/LICENSE
 */
 
package com.structsoftlab.mykeyboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.Keyboard.Key;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.inputmethod.InputMethodSubtype;

import java.util.List;
import android.view.*;
import android.content.*;
import android.net.*;
import android.widget.*;
import android.inputmethodservice.*;
import android.os.*;

public class MyUniKeyboardView extends KeyboardView {
    static final int KEYCODE_OPTIONS = -100;
	
	private Handler mHandler = new Handler ();
    public MyUniKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
		
    }

    public MyUniKeyboardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
		
    }

	public void initEmoji(View view)
	{
		ImageButton delete = (ImageButton)view.findViewById(R.id.delete);
		delete.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					switch(event.getAction()) {
						case MotionEvent.ACTION_DOWN:
							mHandler.postDelayed(mAction, 20);
							break;
						case MotionEvent.ACTION_UP:
							mHandler.removeCallbacks(mAction);
							break;
					}

					return true;

				}
				Runnable mAction = new Runnable() {
					@Override
					public void run() {
						// Do continuous task.
						getOnKeyboardActionListener().onKey(Keyboard.KEYCODE_DELETE, null);
						mHandler.postDelayed(this, 200);
					}
				};
			});
			
		ImageButton abc = (ImageButton)view.findViewById(R.id.changekeyboard);
        abc.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					getOnKeyboardActionListener().onKey(-7, null);
				}
			});
		final ImageButton emoji1 = (ImageButton)view.findViewById(R.id.emoji1);
		final ImageButton emoji2 = (ImageButton)view.findViewById(R.id.emoji2);
		
		
		emoji1.setPressed(false);
		emoji2.setPressed(true);
		
		emoji1.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					getOnKeyboardActionListener().onKey(-15, null);
					emoji1.setPressed(false);
					emoji2.setPressed(true);
					
				}
			});
		
		emoji2.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					getOnKeyboardActionListener().onKey(-16, null);
					emoji1.setPressed(true);
					emoji2.setPressed(false);
					
				}
			});
		
		
	}
	
    @Override
    protected boolean onLongPress(Key key) {
        if (key.codes[0] == Keyboard.KEYCODE_CANCEL) {
            getOnKeyboardActionListener().onKey(KEYCODE_OPTIONS, null);
            return true;
        
        } else {
			switch (key.codes[0]){
				case 'a':
					getOnKeyboardActionListener().onKey('!', null);
					return true;
				case 'q':
					getOnKeyboardActionListener().onKey('1', null);
					return true;
				case 'w':
					getOnKeyboardActionListener().onKey('2', null);
					return true;
				case 'e':
					getOnKeyboardActionListener().onKey('3', null);
					return true;
				case 'r':
					getOnKeyboardActionListener().onKey('4', null);
					return true;
				case 't':
					getOnKeyboardActionListener().onKey('5', null);
					return true;
				case 'y':
					getOnKeyboardActionListener().onKey('6', null);
					return true;
				case 'u':
					getOnKeyboardActionListener().onKey('7', null);
					return true;
				case 'i':
					getOnKeyboardActionListener().onKey('8', null);
					return true;
				case 'o':
					getOnKeyboardActionListener().onKey('9', null);
					return true;
				case 'p':
					getOnKeyboardActionListener().onKey('0', null);
					return true;
			}
		}
        return super.onLongPress(key);
    }

    void setSubtypeOnSpaceKey(final InputMethodSubtype subtype) {
        final Keyboard keyboard = (Keyboard)getKeyboard();
        //keyboard.setSpaceIcon(getResources().getDrawable(subtype.getIconResId()));
        invalidateAllKeys();
    }
	
    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(30);
        paint.setColor(Color.GREEN);
	
        List<Key> keys = getKeyboard().getKeys();
        for(Key key: keys) {
            if(key.label != null) {
                if (key.label.equals("q")) {
                    canvas.drawText("1", key.x + (key.width - 25), key.y + 40, paint);
                } else if (key.label.equals("w")) {
                    canvas.drawText("2", key.x + (key.width - 25), key.y + 40, paint);
                } else if (key.label.equals("e")) {
                    canvas.drawText("3", key.x + (key.width - 25), key.y + 40, paint);
                } else if (key.label.equals("r")) {
                    canvas.drawText("4", key.x + (key.width - 25), key.y + 40, paint);
                } else if (key.label.equals("t")) {
                    canvas.drawText("5", key.x + (key.width - 25), key.y + 40, paint);
                } else if (key.label.equals("y")) {
                    canvas.drawText("6", key.x + (key.width - 25), key.y + 40, paint);
                } else if (key.label.equals("u")) {
                    canvas.drawText("7", key.x + (key.width - 25), key.y + 40, paint);
                } else if (key.label.equals("i")) {
                    canvas.drawText("8", key.x + (key.width - 25), key.y + 40, paint);
                } else if (key.label.equals("o")) {
                    canvas.drawText("9", key.x + (key.width - 25), key.y + 40, paint);
                } else if (key.label.equals("p")) {
                    canvas.drawText("0", key.x + (key.width - 25), key.y + 40, paint);
                }
            }
        }
    }
}
