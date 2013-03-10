package com.hiroyukinishimura.uryaoi;

import org.andengine.entity.scene.Scene;

import android.os.Bundle;

public class StageActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected Scene onCreateScene() {
		// InitialSceneをインスタンス化し、エンジンにセット
		StageScene scene = new StageScene(this);
		// 遷移管理用配列に追加
		getSceneArray().add(scene);
		return scene;
	}

	@Override
	public void onPause() {
		super.onPause();
		try {
			getResourceUtil().resetAllTexture();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void backToInitial() {
		// 遷移管理用配列をクリア
		getSceneArray().clear();
		// 新たにInitialSceneからスタート
		BaseScene scene = new InitialScene(this);
		getSceneArray().add(scene);
		getEngine().setScene(scene);
	}

	@Override
	public void refreshRunningScene(BaseScene scene) {

	}

	@Override
	protected int getLayoutID() {
		return R.layout.activity_initial;
	}

	@Override
	protected int getRenderSurfaceViewID() {
		// TODO Auto-generated method stub
		return R.id.renderview;
	}

	

}
