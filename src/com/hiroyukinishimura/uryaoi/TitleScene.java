package com.hiroyukinishimura.uryaoi;

import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.opengl.font.Font;
import org.andengine.util.HorizontalAlign;

import android.content.Intent;
import android.view.KeyEvent;

public class TitleScene extends BaseScene implements ButtonSprite.OnClickListener {
	
	private static final int TAG_START = 1;	
	private Text textMaxScore;

	public TitleScene(BaseActivity context) {
		super(context);
		init();
	}
	
	@Override
	public void init() {
		
		ButtonSprite btnStart = getBaseActivity().getResourceUtil()
				.getButtonSprite("button_start.png",
						"button_start.png");
		placeToCenterX(btnStart, 640);
		btnStart.setTag(TAG_START);
		btnStart.setOnClickListener(this);
		attachChild(btnStart);
		registerTouchArea(btnStart);
		
		// MAXスコア
		Font textFont = getBaseActivity().getFont();
		String maxScore = getBaseActivity().getMaxScorePrefs() + " combo";
		textMaxScore = new Text(0, getBaseActivity().getCameraWidth(), textFont, maxScore, (maxScore.length() + 1) * 2, new TextOptions(HorizontalAlign.CENTER), getBaseActivity().getVertexBufferObjectManager());
		textMaxScore.setPosition(10,10);
		attachChild(textMaxScore);
	}

	@Override
	public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX,
			float pTouchAreaLocalY) {
		super.onClick(pButtonSprite, pTouchAreaLocalX, pTouchAreaLocalY);
		
		System.out.println("Urya tag=" + pButtonSprite.getTag());
		
		if (pButtonSprite.getTag() == TAG_START) {
			getBaseActivity().finish();
			getBaseActivity().startActivity(new Intent(getBaseActivity(), StageActivity.class));
		}
		
	}
	
	@Override
	public boolean dispatchKeyEvent(KeyEvent e) {
		return false;
	}
	
}
