package ui.story.lemoon.scr.v2;

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
	
	TextureAtlas textureAtlas;
	private SpriteBatch batch;
	Sprite  mHand, mUnlock;
	Animation boatAnim;
	
	OrthographicCamera camera, cameraUI;
	AtlasRegion mWave1, mWave2, mBg;
	Music music;
	
	
	float W,H;
	public SCRLoading(Game g){
		Gdx.app.log("", "------------SCRLoading.SCRLoading");
		this.game = g;
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		Gdx.app.log("", "------------SCRLoading.show");
//	    Gdx.graphics.setContinuousRendering(false);
//	    Gdx.graphics.requestRendering();
		
		
		W = Gdx.graphics.getWidth();
		H = Gdx.graphics.getHeight();
		
		AssetManager mgr = MyGame.getManager();
		mgr.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));

		mgr.load("v2/map/v2map.tmx", TiledMap.class);
		mgr.load("v2/datas/items.atlas", TextureAtlas.class);

		mgr.load("font/arial-15.png", Texture.class);
		mgr.load("font/arial-15.fnt", BitmapFont.class);
		
		
		
		//--------------
		music = Gdx.audio.newMusic(Gdx.files.internal("v2/lock_datas/lock_bg.ogg"));
		music.setLooping(true);
		music.play();
		
		
		batch = new SpriteBatch();
		textureAtlas = new TextureAtlas(Gdx.files.internal("v2/lock_datas/lock_item.atlas"));   // 用到 TextureAtlas， 纹理集
		mBg =  textureAtlas.findRegion("bg") ;
        //mbgSprite.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        
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
		//camera.zoom = 1f;
		
		Gdx.input.setCatchBackKey(true);
		Gdx.input.setCatchMenuKey(true);
		Gdx.input.setInputProcessor(this);   //必须
	}

	float mWave1OffsetX=0f, mWave2OffsetX=0f;
	void upateWave(){
		camera.position.x += 1f;	
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
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		float a = 1/255f;
		Gdx.gl.glClearColor(82*a, 173*a, 243*a, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if(mbPressed && Gdx.input.isTouched()){
			mboatPos += 5f;
			//Gdx.app.log("", "mbpressed="+false);

		}
		else{
			mbPressed =  false;
			mboatPos = 20;
		}
		if(mboatPos > 580){
			Gdx.app.log("", "boat pos="+mboatPos);
			//Gdx.graphics.setContinuousRendering(false);

			game.setScreen(new SCRMain(game));
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
			//Gdx.app.log("", "------------load finished!!,to SCRMain");
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
		Gdx.app.log("", "------------SCRLoading.pause");
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		Gdx.app.log("", "------------SCRLoading.resume");
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		Gdx.app.log("", "------------SCRLoading.hide");
		
		textureAtlas.dispose();
		music.stop();
		music.dispose();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		//MyGame.getManager().dispose();
		Gdx.app.log("", "------------SCRLoading.dispose");

	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		if(keycode == Input.Keys.BACK || keycode==Input.Keys.MENU){
			Gdx.app.log("", "========SCRMain keydown: back|menu");
			return true;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		if(keycode == Input.Keys.BACK || keycode==Input.Keys.MENU){
			Gdx.app.log("", "========SCRMain keyup: back|menu ");
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
		Gdx.app.log("", String.format("touchdown:(%d,%d)", x, y));
//		y>50<100
//		x>623 744
		
		if(x>623 && x<744
				&& y>50 && y<100)
			mbPressed = true;
		//615,36,
		Gdx.app.log("", "mbpressed="+mbPressed);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		Gdx.app.log("", String.format("touchup:(%d,%d)", screenX, screenY));
		//mbPressed = false;
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
