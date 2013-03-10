package com.hiroyukinishimura.uryaoi;

import java.util.ArrayList;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.Texture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.ui.activity.SimpleLayoutGameActivity;
import org.andengine.util.color.Color;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;

public abstract class BaseActivity extends SimpleLayoutGameActivity {
	
	// 画面のサイズ。
	private int CAMERA_WIDTH = 480;
	private int CAMERA_HEIGHT = 800;
	
	public MediaPlayer mediaPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onStart() {
		super.onStart();
	}
	
	public void onStop() {
		super.onStop();
	}
	
	public EngineOptions onCreateEngineOptions() {
		// サイズを指定し描画範囲をインスタンス化
		final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		// ゲームのエンジンを初期化。
		// 第1引数 タイトルバーを表示しないモード
		// 第2引数 画面は縦向き（幅480、高さ800）
		// 第3引数 解像度の縦横比を保ったまま最大まで拡大する
		// 第4引数 描画範囲
		EngineOptions eo = new EngineOptions(true,
				ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(
						CAMERA_WIDTH, CAMERA_HEIGHT), camera);
		// 効果音の使用を許可する
		eo.getAudioOptions().setNeedsSound(true);
		return eo;
	}
	
	// Font
	private Font mFont;
	private Texture mTexture;
	
	// ResourceUtilのインスタンス
	private ResourceUtil mResourceUtil;
	
	// 起動済みのSceneの配列
	private ArrayList<BaseScene> mSceneArray;

	@Override
	protected void onCreateResources() {
		mResourceUtil = ResourceUtil.getInstance(this);
		mSceneArray = new ArrayList<BaseScene>();
		initFont();
	}
	
	public ResourceUtil getResourceUtil() {
		return mResourceUtil;
	}

	public ArrayList<BaseScene> getSceneArray() {
		return mSceneArray;
	}

	// 起動済みのGeneraceAndEngineBaseSceneを格納する配列
	public void appendScene(BaseScene scene) {
		getSceneArray().add(scene);
	}
	
	// 最初のシーンに戻る為の関数
	public abstract void backToInitial();

	// シーンとシーン格納配列を更新する関数
	public abstract void refreshRunningScene(BaseScene scene);
	
	// どのシーンでもバックボタンが押された場合基本的には終了確認ダイアログを表示する
	public boolean dispatchKeyEvent(KeyEvent e) {
		if(e.getAction() == KeyEvent.ACTION_DOWN && e.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
			// アラートダイアログのタイトルを設定します
			alertDialogBuilder.setTitle(getString(R.string.finish_confirm_dialog_title));
			// アラートダイアログのメッセージを設定します
			alertDialogBuilder.setMessage(getString(R.string.finish_confirm_dialog_strings));
			// アラートダイアログの肯定ボタンがクリックされた時に呼び出されるコールバックリスナーを登録します
			alertDialogBuilder.setPositiveButton(getString(R.string.yes),
			new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					finish();
				}
			});
			// アラートダイアログの否定ボタンがクリックされた時に呼び出されるコールバックリスナーを登録します
			alertDialogBuilder.setNegativeButton(getString(R.string.no),
			new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
				}
			});
			// アラートダイアログのキャンセルが可能かどうかを設定します
			alertDialogBuilder.setCancelable(true);
			AlertDialog alertDialog = alertDialogBuilder.create();
			// アラートダイアログを表示します
			alertDialog.show();
	    }
		return false;
	}
	
	public int getCameraWidth() {
		return CAMERA_WIDTH;
	}

	public int getCameraHeight() {
		return CAMERA_HEIGHT;
	}
	
	public void initFont() {
		// フォントをイニシャライズ
		mTexture = new BitmapTextureAtlas(getTextureManager(), CAMERA_WIDTH, CAMERA_HEIGHT, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        // Font
        mFont = new Font(getFontManager(), mTexture, Typeface.DEFAULT_BOLD, 30, true, Color.WHITE);
        // EngineのTextureManagerにフォントTextureを読み込み
        getTextureManager().loadTexture(mTexture);
        // FontManagerにフォントを読み込み
        getFontManager().loadFont(mFont);
	}
	
	public Font getFont() {
		return mFont;
	}
	
	public Texture getTexture() {
		return mTexture;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	public void onPause() {
		super.onPause();
	}
}
