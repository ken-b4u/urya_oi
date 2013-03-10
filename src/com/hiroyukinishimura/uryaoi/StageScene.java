package com.hiroyukinishimura.uryaoi;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.opengl.font.Font;
import org.andengine.util.HorizontalAlign;

import android.content.Intent;
import android.view.KeyEvent;

public class StageScene extends BaseScene implements ButtonSprite.OnClickListener {
	
	private static final int TAG_BUTTON_URYA = 1;
	private static final int TAG_BUTTON_OI = 2;
	private static final int TAG_SPRITE_URYA = 3;
	private static final int TAG_SPRITE_OI = 4;
	private Sprite spriteBase;
	private ButtonSprite btnUrya;
	private ButtonSprite btnOi;
	private Sprite spriteRed;
	private Sprite spriteYellow;
	private Sprite spritePink;
	private Sprite spritePurple;
	private Sprite spriteGreen;
	
	private Text textMaxScore;
	private Text textScore;
	
	private int combo;
	private int count;
	
	private int[] urya;
	private int[] oi;
	private int uryaIndex;
	private int oiIndex;
	
	public StageScene(BaseActivity context) {
		super(context);
		init();
	}
	
	@Override
	public void init() {
		
		combo = 0;
		count = 0;
		
		urya =  getBaseActivity().getResources().getIntArray(R.array.urya);
		oi =  getBaseActivity().getResources().getIntArray(R.array.oi);
		
		// MAXスコア
		Font textFont = getBaseActivity().getFont();
		String maxScore = getBaseActivity().getMaxScorePrefs() + " combo";
		textMaxScore = new Text(0, getBaseActivity().getCameraWidth(), textFont, maxScore, (maxScore.length() + 1) * 2, new TextOptions(HorizontalAlign.CENTER), getBaseActivity().getVertexBufferObjectManager());
		textMaxScore.setPosition(10,10);
		attachChild(textMaxScore);
		
		spriteBase = getBaseActivity().getResourceUtil().getSprite("base.png");
		spriteBase.setPosition(100, 100);
		attachChild(spriteBase);
		
		btnUrya = getBaseActivity().getResourceUtil()
				.getButtonSprite("button_urya.png",
						"button_urya.png");
		btnUrya.setPosition(100,600);
		btnUrya.setTag(TAG_BUTTON_URYA);
		btnUrya.setOnClickListener(this);
		attachChild(btnUrya);
		registerTouchArea(btnUrya);
		
		btnOi = getBaseActivity().getResourceUtil()
				.getButtonSprite("button_urya.png",
						"button_urya.png");
		btnOi.setPosition(300,600);
		btnOi.setTag(TAG_BUTTON_OI);
		btnOi.setOnClickListener(this);
		attachChild(btnOi);
		registerTouchArea(btnOi);
		
		registerUpdateHandler(updateHandler);
	}

	@Override
	public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX,
			float pTouchAreaLocalY) {
		super.onClick(pButtonSprite, pTouchAreaLocalX, pTouchAreaLocalY);
		
		if (pButtonSprite.getTag() == TAG_BUTTON_URYA) {
			for (int i = 0; i < getChildCount(); i++) {
				if (getChildByIndex(i).getTag() == TAG_SPRITE_URYA) {
					float x = getChildByIndex(i).getX();
					System.out.println("Urya x=" + x);
					if (x >= 80 && x <= 120) {
						++combo;
						getChildByIndex(i).detachSelf();
					}
				}
			}
		} else if (pButtonSprite.getTag() == TAG_BUTTON_OI) {
		
		}
		
	}
	
	public TimerHandler updateHandler = new TimerHandler(1f / 30f, true, new ITimerCallback() {
		
		@Override
		public void onTimePassed(TimerHandler pTimerHandler) {
			if (urya.length > uryaIndex) {
				if (urya[uryaIndex] == count) {
					Sprite spriteUrya = getBaseActivity().getResourceUtil().getSprite("urya.png");
					spriteUrya.setTag(TAG_SPRITE_URYA);
					spriteUrya.setPosition(500, 100);
					attachChild(spriteUrya);
					++uryaIndex;
				}
			}
			if (oi.length > oiIndex) {
				if (oi[oiIndex] == count) {
					Sprite spriteOi = getBaseActivity().getResourceUtil().getSprite("oi.png");
					spriteOi.setTag(TAG_SPRITE_OI);
					spriteOi.setPosition(500, 100);
					attachChild(spriteOi);
					++oiIndex;
				}
			}
			
			for (int i = 0; i < getChildCount(); i++) {
				if (getChildByIndex(i).getTag() == TAG_SPRITE_URYA
						|| (getChildByIndex(i).getTag() == TAG_SPRITE_OI)) {
					float x = getChildByIndex(i).getX() - 5;
					getChildByIndex(i).setPosition(x, 100);
					if (x < -100) {
						getChildByIndex(i).detachSelf();
					}
				}
			}
				
			++count;
		}
	});
	
	@Override
	public boolean dispatchKeyEvent(KeyEvent e) {
		return false;
	}
	
}
