package ui.story.lemoon.scr.v2;

import ui.story.lemoon.Configer;
import ui.story.lemoon.MyGame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Matrix4;

public class SCRLoading implements Screen , InputProcessor {
	Game game;
	
	//TextureAtlas textureAtlas;
	private SpriteBatch batch;
	Sprite  mHand, mUnlock;
	Animation boatAnim;
	
	OrthographicCamera camera, cameraUI;
	AtlasRegion mWave1, mWave2, mBg;
	//Music music;
	
	
	float W,H;
	public SCRLoading(Game g){
		Configer.mylog("", "------------SCRLoading.SCRLoading");
		this.game = g;
	}
	
	SCRMain srcmain;
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		Configer.mylog("", "------------SCRLoading.show");
		
		W = Gdx.graphics.getWidth();
		H = Gdx.graphics.getHeight();

		MyGame.mCfg.loadRes();

		Music music = MyGame.mCfg.getLockMusic();
		music.setLooping(true);
		music.play();
		
		
		srcmain = new SCRMain(game);

		
		TextureAtlas textureAtlas = MyGame.mCfg.getLockAtlas();
		batch = new SpriteBatch();
		mBg =  textureAtlas.findRegion("bg") ;        
        AtlasRegion region = textureAtlas.findRegion("hand");
        mHand = new Sprite(region);
        mHand.setBounds(715,0, region.getRegionWidth(), region.getRegionHeight());
        
        region = textureAtlas.findRegion("unlock");
        mUnlock = new Sprite(region);
        mUnlock.setBounds(615,36, region.getRegionWidth(), region.getRegionHeight()) ;
       
        
        mWave1 = textureAtlas.findRegion("wave1");
        mWave2 = textureAtlas.findRegion("wave2");
        
        boatAnim = new Animation(1/3f, textureAtlas.findRegion("boat1"), textureAtlas.findRegion("boat2"));
        
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.zoom = 1f; 
		
		
		cameraUI = new OrthographicCamera();
		cameraUI.setToOrtho(false, W, H);
		
		Gdx.input.setCatchBackKey(true);
		Gdx.input.setCatchMenuKey(true);
		Gdx.input.setInputProcessor(this);   //必须
	}

	float mWave1OffsetX=0f, mWave2OffsetX=0f;
	void upateWave(){
		camera.position.x += 5f;	
 		if(camera.position.x - mWave1OffsetX > mWave1.getRegionWidth()+394.9f) {
			mWave1OffsetX += mWave1.getRegionWidth();
		}
		if(camera.position.x - mWave2OffsetX> mWave2.getRegionWidth()+400f){
			mWave2OffsetX += mWave2.getRegionWidth();
		}
	}
	
	int mHandStep=0;
 
	void updateHand(){
 
		if(mHandStep < 30){
			mHand.scale(0.005f);
 			mHandStep ++;
		}
		else if(mHandStep >=30 && mHandStep <60){
 
			mHand.scale(-0.005f);
			mHandStep++;
		}
		else{
			mHandStep = 0;
		}
	}
	
	int mUnlockStep = 0;
	float mUnlockAlpha = 1f;
	void updateUnlock(){
		if(mUnlockStep < 80){
			mUnlockAlpha -= 1/160f;
			mUnlockStep ++;
		}
		else if(mUnlockStep >= 80 && mUnlockStep<160){
			mUnlockAlpha += 1/160f;
			mUnlockStep++;
		}
		else{
			mUnlockAlpha = 1f;
			mUnlockStep = 0;
		}
	}
	
	boolean mbPressed = false;
	float mboatPos = 20;
	float elapsedTime = 0f;
	
	long mLastUpTm = 0;  //上一次
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		float a = 1/255f;
		Gdx.gl.glClearColor(82*a, 173*a, 243*a, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		if(mbPressed){
			mboatPos += 5f;  
		}
		else{
			//mboatPos = 20;
			
			if(System.currentTimeMillis() - mLastUpTm > 1500){
				//Configer.mylog("", "============= origin========");
				mboatPos = 20;
			}
			else{
				//mbPressed = true;
//				if(mbPressed)
//					mboatPos += 3f; 
				//Configer.mylog("", "=============  pause ========");
			}
		}
		
		if(mboatPos > 580 && MyGame.mCfg.isLoadFinish()){
			Configer.mylog("", "boat pos="+mboatPos);
			//Gdx.graphics.setContinuousRendering(false);

		    Gdx.graphics.setContinuousRendering(true);
	        Gdx.graphics.requestRendering();
	        
			game.setScreen(srcmain);
		}
		
		upateWave();
		updateHand();
		updateUnlock();
		
		camera.update();
		
		batch.begin();
		batch.draw(mBg,0,0);
		batch.end();
		
 		batch.setProjectionMatrix(camera.combined);
		
		elapsedTime += Gdx.graphics.getDeltaTime();
        
		batch.begin();
		//batch.draw(mBg, camera.position.x - mBg.getRegionWidth() / 2, 0);
	    batch.draw(mWave2, mWave2OffsetX, 30);
		batch.draw(mWave2, mWave2OffsetX + mWave2.getRegionWidth(), 30);
		batch.draw(mWave2, mWave2OffsetX + mWave2.getRegionWidth()*2, 30);
		batch.draw(mWave2, mWave2OffsetX + mWave2.getRegionWidth()*3, 30);
		batch.draw(mWave2, mWave2OffsetX + mWave2.getRegionWidth()*4, 30);	

		batch.end();
		
		//============
		batch.setProjectionMatrix(cameraUI.combined);
		batch.begin();	
		TextureRegion tr  = boatAnim.getKeyFrame(elapsedTime, true);
        batch.draw(tr, mboatPos, 45);
		batch.end();
		
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
	    batch.draw(mWave1, mWave1OffsetX, 0);
		batch.draw(mWave1, mWave1OffsetX + mWave1.getRegionWidth(), 0);
		batch.draw(mWave1, mWave1OffsetX + mWave1.getRegionWidth()*2, 0);
		batch.draw(mWave1, mWave1OffsetX + mWave1.getRegionWidth()*3, 0);
		batch.draw(mWave1, mWave1OffsetX + mWave1.getRegionWidth()*4, 0);
		

		batch.end();
		
		
		batch.setProjectionMatrix(cameraUI.combined);
		batch.begin();	
        mUnlock.draw(batch, mUnlockAlpha);
        mHand.draw(batch);
        batch.end();
		
		
		
		if(MyGame.getManager().update()){
			//加载完毕
			//Configer.mylog("", "------------load finished!!,to SCRMain");
			//game.setScreen(new SCRMain(game));
		}
		else{
			
			
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		Configer.mylog("", "------------SCRLoading.pause");
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		Configer.mylog("", "------------SCRLoading.resume");
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		Configer.mylog("", "------------SCRLoading.hide");
		
//		textureAtlas.dispose();
//		music.stop();
//		music.dispose();
		
		MyGame.mCfg.disposeLockAtlas();
		MyGame.mCfg.disposeLockMusic();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		//MyGame.getManager().dispose();
		Configer.mylog("", "------------SCRLoading.dispose");

	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		if(keycode == Input.Keys.BACK || keycode==Input.Keys.MENU){
			Configer.mylog("", "========SCRMain keydown: back|menu");
			return true;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		if(keycode == Input.Keys.BACK || keycode==Input.Keys.MENU){
			Configer.mylog("", "========SCRMain keyup: back|menu ");
			return true;
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		int x = screenX;
		int y = (int)(H-screenY-1);
		Configer.mylog("", String.format("touchdown:(%d,%d)", x, y));
//		y>50<100
//		x>623 744
		
		if(x>623 && x<744
				&& y>50 && y<100){
			mLastUpTm = System.currentTimeMillis();
			mbPressed = true;
		}
		//615,36,
		Configer.mylog("", "mbpressed="+mbPressed);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		Configer.mylog("", String.format("touchup:(%d,%d)", screenX, screenY));
		mbPressed = false;
		mLastUpTm = System.currentTimeMillis();
		return true;
		//return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
