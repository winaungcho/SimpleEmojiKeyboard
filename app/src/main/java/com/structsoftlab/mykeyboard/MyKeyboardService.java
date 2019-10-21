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

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.media.AudioManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.view.*;
import android.content.*;

public class MyKeyboardService extends InputMethodService
	implements OnKeyboardActionListener{
	
	private View mView;
	private View mScrolltab;
	private View mEmojiScroll;
	static int emojino=0, maxemoji=2;
	private Keyboard mEmoji1Keyboard;
	private Keyboard mEmoji2Keyboard;
	
	private MyUniKeyboardView kv, mEmojiView;
	private Keyboard keyboard, mCurKeyboard;
	
	private boolean caps = false;
	
	@Override
	public View onCreateInputView() {
		
        LayoutInflater inflater1 = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater1.inflate(R.layout.emojiview, null);
        mView.invalidate();
        kv = (MyUniKeyboardView) mView.findViewById(R.id.keyboard);
        kv.setOnKeyboardActionListener(this);
		kv.setPreviewEnabled(false);
		
		mEmojiView = (MyUniKeyboardView) mView.findViewById(R.id.emojikeyboard);
        
        mEmojiView.setOnKeyboardActionListener(this);
		mEmojiView.setPreviewEnabled(false);
		mEmojiView.initEmoji(mView);
		mScrolltab = mView.findViewById(R.id.scrolltab);
		mEmojiScroll = mView.findViewById(R.id.emojiscroll);
		emojino=0;
		kv.setKeyboard(mCurKeyboard);
		mEmojiView.setKeyboard(mEmoji1Keyboard);
		return mView;
	}
	public void onInitializeInterface() {
		keyboard = new Keyboard(this, R.xml.english_us);
		mEmoji1Keyboard = new Keyboard(this, R.xml.emoji1);
		mEmoji2Keyboard = new Keyboard(this, R.xml.emoji2);
		mCurKeyboard = keyboard;
		
	}
	private boolean isEmojiKeyboard()
	{
		Keyboard kb = mCurKeyboard;
		if (kb == null)
			return false;
		if (kb == mEmoji1Keyboard ||
			kb == mEmoji2Keyboard
			
			){
			return true;
		}
		return false;
	}
	private void setEmojiKeyboard(int n)
	{
		switch(n){
			case 0: mCurKeyboard = (mEmoji1Keyboard);
				break;
			case 1: mCurKeyboard = (mEmoji2Keyboard);
				break;
			
		}
		if (mEmojiView != null)
			mEmojiView.setKeyboard(mCurKeyboard);
	}
	@Override
	public void onKey(int primaryCode, int[] keyCodes) {		
		InputConnection ic = getCurrentInputConnection();
		playClick(primaryCode);
		switch(primaryCode){
		case Keyboard.KEYCODE_DELETE :
			ic.deleteSurroundingText(1, 0);
			break;
		case Keyboard.KEYCODE_SHIFT:
			caps = !caps;
			keyboard.setShifted(caps);
			kv.invalidateAllKeys();
			break;
		case Keyboard.KEYCODE_DONE:
			ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
			break;
			case -15:
			case -16:
			
				emojino = (-primaryCode - 15);
				setEmojiKeyboard(emojino);
				break;
		case -7:
				if (!isEmojiKeyboard()){
					//mCurKeyboard = (mEmoji1Keyboard);
					setEmojiKeyboard(emojino);
					mEmojiView.setKeyboard(mCurKeyboard);

					mEmojiView.setVisibility(View.VISIBLE);
					mScrolltab.setVisibility(View.VISIBLE);
					mEmojiScroll.setVisibility(View.VISIBLE);

					kv.setVisibility(View.GONE);
					
				}
				else {
					mCurKeyboard = keyboard;
					kv.setKeyboard(mCurKeyboard);
					mEmojiView.setVisibility(View.GONE);
					mScrolltab.setVisibility(View.GONE);
					mEmojiScroll.setVisibility(View.GONE);

					kv.setVisibility(View.VISIBLE);
					
				}
				break;
		default:
			char code = (char)primaryCode;
				if (isEmojiKeyboard()){
					ic.commitText(String.valueOf(Character.toChars(primaryCode)), 1);
				} else{
			if(Character.isLetter(code) && caps){
				code = Character.toUpperCase(code);
			}
			ic.commitText(String.valueOf(code),1);
}			
		}
	}
	
	private void playClick(int keyCode){
		AudioManager am = (AudioManager)getSystemService(AUDIO_SERVICE);
		switch(keyCode){
		case 32: 
			am.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
			break;
		case Keyboard.KEYCODE_DONE:
		case 10: 
			am.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN);
			break;
		case Keyboard.KEYCODE_DELETE:
			am.playSoundEffect(AudioManager.FX_KEYPRESS_DELETE);
			break;				
		default: am.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
		}		
	}

	@Override
	public void onPress(int primaryCode) {
	}

	@Override
	public void onRelease(int primaryCode) { 			
	}

	@Override
	public void onText(CharSequence text) {		
	}

	@Override
	public void swipeDown() {	
	}

	@Override
	public void swipeLeft() {
	}

	@Override
	public void swipeRight() {
	}

	@Override
	public void swipeUp() {
	}
}
