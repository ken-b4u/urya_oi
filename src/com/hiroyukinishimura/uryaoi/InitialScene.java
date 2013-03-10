package com.hiroyukinishimura.uryaoi;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.opengl.font.Font;
import org.andengine.util.HorizontalAlign;

import android.content.Intent;
import android.view.KeyEvent;

public class InitialScene extends BaseScene {
	
	private Text text;
	private int count;
	
	public InitialScene(BaseActivity context) {
		super(context);
		init();
	}
	
	@Override
	public void init() {
		Font textFont = getBaseActivity().getFont();
		String name = getBaseActivity().getResources().getString(R.string.name);
		text = new Text(0, getBaseActivity().getCameraWidth(), textFont, name, (name.length() + 1) * 2, new TextOptions(HorizontalAlign.CENTER), getBaseActivity().getVertexBufferObjectManager());
		text.setPosition((getBaseActivity().getCameraWidth()/2 - text.getWidth()/2),
				getBaseActivity().getCameraHeight()/2);
		attachChild(text);
		
		count = 0;
		
		registerUpdateHandler(updateHandler);
	}
	
	
	public TimerHandler updateHandler = new TimerHandler(1f / 10f, true, new ITimerCallback() {
		
		@Override
		public void onTimePassed(TimerHandler pTimerHandler) {
			++count;
			if (count == 10) {
				getBaseActivity().finish();
				getBaseActivity().startActivity(new Intent(getBaseActivity(), TitleActivity.class));
			}
		}
	});

	@Override
	public boolean dispatchKeyEvent(KeyEvent e) {
		return false;
	}
	
}
