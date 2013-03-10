package com.hiroyukinishimura.uryaoi;

import java.io.IOException;

import javax.microedition.khronos.opengles.GL10;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;

import android.view.KeyEvent;

public abstract class BaseScene extends Scene implements ButtonSprite.OnClickListener{
	
	private BaseActivity baseActivity;
	
	// ボタン効果音
	protected Sound soundButton;
	// ポーズ画面の背景
	private Rectangle overLay;	

	public BaseScene(BaseActivity baseActivity) {
		setTouchAreaBindingOnActionDownEnabled(true);
		this.baseActivity = baseActivity;
		prepareSoundAndMusic();
	}

	// イニシャライザ
	public abstract void init();

	public BaseActivity getBaseActivity() {
		return this.baseActivity;
	}
	
	@Override
	public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX,
			float pTouchAreaLocalY) {		
	}
	
	public void prepareSoundAndMusic() {
		SoundFactory.setAssetBasePath("mfx/");
		try {
			soundButton = SoundFactory.createSoundFromAsset(
					getBaseActivity().getSoundManager(), getBaseActivity(), "button.mp3");
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	// KeyEventのリスナー
	public abstract boolean dispatchKeyEvent(KeyEvent e);
	
	public void showOverLay() {
		overLay = new Rectangle(0, 0, getBaseActivity().getEngine().getCamera()
				.getWidth(), getBaseActivity().getEngine().getCamera()
				.getHeight(), getBaseActivity().getVertexBufferObjectManager());
		overLay.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		overLay.setColor(0, 0, 0);
		overLay.setAlpha(0.8f);
		attachChild(overLay);
	}
	
	public void hideOverLay() {
		detachChild(overLay);
	}
	
	public Sprite placeToCenter(Sprite sp) {
		sp.setPosition(baseActivity.getEngine().getCamera().getWidth() / 2.0f - sp.getWidth() / 2.0f, baseActivity.getEngine().getCamera().getHeight()
				/ 2.0f - sp.getHeight() / 2.0f);
		return sp;
	}

	public Sprite placeToCenterX(Sprite sp, float y) {
		sp.setPosition(baseActivity.getEngine().getCamera().getWidth() / 2.0f - sp.getWidth() / 2.0f, y);
		return sp;
	}

	public Sprite placeToCenterY(Sprite sp, float x) {
		sp.setPosition(x, baseActivity.getEngine().getCamera().getHeight() / 2.0f - sp.getHeight() / 2.0f);
		return sp;
	}
	
	public Text placeToCenterX(Text text, float y) {
		text.setPosition(baseActivity.getEngine().getCamera().getWidth() / 2.0f - text.getWidth() / 2.0f, y);
		return text;
	}
	
	public void createButton(ButtonSprite btn) {
		attachChild(btn);
		registerTouchArea(btn);
	}
	
	public void deleteButton(ButtonSprite btn) {
		detachChild(btn);
		unregisterTouchArea(btn);
	}
}
