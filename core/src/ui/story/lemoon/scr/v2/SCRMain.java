package ui.story.lemoon.scr.v2;

import java.util.HashMap;

import ui.story.lemoon.Configer;
import ui.story.lemoon.Configer.BgMusicListener;
import ui.story.lemoon.MyGame;
import ui.story.lemoon.actor.*;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

 

public class SCRMain implements Screen, GestureListener {
	int mCurPg = 0;
	
	private Stage mStage1; 
	private OrthographicCamera mCamera;
	private MapRenderer mMapRenderer;
	TiledMap mMap;
	Viewport viewport;	
	//
	InputMultiplexer multiplexer = new InputMultiplexer();
	GestureDetector mGesture;
	InputProcessor  mInput;
	HashMap<String, TiledMapTileLayer> mLayerMap = new HashMap<String, TiledMapTileLayer>();

	SpriteBatch batch;
	OrthographicCamera camera;
	AtlasRegion mbottomCloud, mLeftPt, mRightPt;
	float mcloudOffsetX,mcloudOffsetY, mCloudW,mCloud2W, mLeftPtW,mLeftPtH, mRightPtW, mRightPtH;
	
	Music night_music,day_music;
	
	public SCRMain(Game g){
		//Gdx.graphics.setContinuousRendering(false);
		Configer.mylog("", "------------SCRMain.SCRMain");
		//MyGame.mCfg = new Configer();
		MyGame.mCfg.game = (MyGame)g;
		
		MyGame.mCfg.setBgMusicListener(new BgMusicListener() {
			@Override
			public void toggleMusic(boolean on) {
				// TODO Auto-generated method stub
				if(on){
					if(MyGame.mCfg.mBDayMode){
						day_music.play();
					}
					else{
						night_music.play();
					}
					
				}
				else{
					if(MyGame.mCfg.mBDayMode){
						day_music.pause();
					}
					else{
						night_music.pause();
					}
					
				}
			}
		});
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		Configer.mylog("", "------------SCRMain.show");
		
		initRes();
		initStageItems();
		
		 
		initforcloud();
 
		initforPt();
		
		mGesture = new GestureDetector(this);
		mInput = new InputProcessor() {
			@Override
			public boolean touchUp(int screenX, int screenY, int pointer, int button) {
				// TODO Auto-generated method stub
				Configer.mylog("", "InputProcessor. touchup");
				return false;
			}
			
			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				// TODO Auto-generated method stub
				Configer.mylog("", "InputProcessor. touchDown");
				return false;
			}
			
			@Override
			public boolean scrolled(int amount) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean mouseMoved(int screenX, int screenY) {
				// TODO Auto-generated method stub
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
			public boolean keyDown(int keycode) {
				// TODO Auto-generated method stub
				if(keycode == Input.Keys.BACK || keycode==Input.Keys.MENU){
					Configer.mylog("", "========SCRMain keydown: back|menu");
					return true;
				}
				

				return false;
			}
		};
		
		
		
		setStage() ;
	}
	
	
	//对底部云层的绘制实现了 一张图片持续整屏幕滚动效果
	void initforcloud(){
		if(mbottomCloud!= null)
			return;
				
		mcloudOffsetX = 0f;		mcloudOffsetY=23f;
		
		batch = new SpriteBatch();
		  
        //移动的camera，该camera与屏幕尺寸相应，且仅仅变化x轴坐标。
		//所以其y轴固定，不会随着map的变换而变换，因为spritebatch是独立的。
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.zoom = 0.9f;	//这个是调试得到的，zoom设置必须要在上面设置大小之后调用才行，否则会出问题。

		mbottomCloud = MyGame.mCfg.getMainAtlas().findRegion("bottom_cloud");
		mCloudW = mbottomCloud.getRegionWidth();
		mCloud2W = 2*mCloudW;
	}

	void upateCloud(){
		camera.position.x += 2f;		
		if(camera.position.x - mcloudOffsetX > mbottomCloud.getRegionWidth()+600) {
			mcloudOffsetX += mbottomCloud.getRegionWidth();
		}
	}
	
	void DrawCloud(){
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();	 	
	    batch.draw(mbottomCloud, mcloudOffsetX, mcloudOffsetY);
		batch.draw(mbottomCloud, mcloudOffsetX + mCloudW, mcloudOffsetY);
		batch.draw(mbottomCloud, mcloudOffsetX + mCloud2W, mcloudOffsetY);
		batch.end();	 	
	}
	
	
	//mLeftPt, mRightPt
	void initforPt(){
		mLeftPt = MyGame.mCfg.getMainAtlas().findRegion("left_tap");
		mRightPt = MyGame.mCfg.getMainAtlas().findRegion("right_tap");
		
		
		mLeftPtW = mLeftPt.getRegionWidth()/32f;
		mLeftPtH = mLeftPt.getRegionHeight()/32f;
		mRightPtW = mRightPt.getRegionWidth()/32f;
		mRightPtH = mRightPt.getRegionHeight()/32f;
	}
	
	void drawPt(){
//		batch.setProjectionMatrix(mCamera.combined);
//		batch.begin();
//		batch.draw(mLeftPt, 15, 7, mLeftPtW, mLeftPtH);
//		batch.draw(mRightPt, 41, 7, mLeftPtW, mLeftPtH);
//		batch.end();
	}
 
	Group group;
	MeteorActor metor1, metor2;
	RainBowActor rainBow;
	LandActor land;
	ScienceActor science;
	MathActor math;
	LocalActor local;
	FanActor fan;
	GuoXueActor guoxue;
	LifeActor life;
	CartonActor carton;
	MusicActor music;
	PaintActor paint;
	LanguageActor language;
	SettingActor setting;
	SunActor sun;
	CloudActor1 cloud1;
	MoonActor moon;
	LogoActor logo;
	SpeakerActor speak;
	ExplorerActor explorer;
	
	void initStageItems(){
		if(mStage1 != null)
			return ;

		metor1 = new MeteorActor(MyGame.mCfg, 26f,19f, 30f, 0f);
		metor2 = new MeteorActor(MyGame.mCfg, 35f,22f, 30f, 2f);
		rainBow = new RainBowActor(MyGame.mCfg);
		land = new LandActor(MyGame.mCfg);
		science = new ScienceActor(MyGame.mCfg);
		math = new MathActor(MyGame.mCfg);
		local = new LocalActor(MyGame.mCfg);
		fan = new FanActor(MyGame.mCfg);
		guoxue = new GuoXueActor(MyGame.mCfg);
		life = new LifeActor(MyGame.mCfg);
		carton = new CartonActor(MyGame.mCfg);
		music = new MusicActor(MyGame.mCfg);
		paint = new PaintActor(MyGame.mCfg);
		language = new LanguageActor(MyGame.mCfg);
		setting = new SettingActor(MyGame.mCfg);	
		explorer = new ExplorerActor(MyGame.mCfg);
		
        sun = new SunActor(MyGame.mCfg);
        cloud1 = new CloudActor1(MyGame.mCfg); 		
        moon = new MoonActor(MyGame.mCfg);
		logo = new LogoActor(MyGame.mCfg);
        speak = new SpeakerActor(MyGame.mCfg);    

		mCamera = new OrthographicCamera(MyGame.mCfg.MAP_WIDTH, MyGame.mCfg.MAP_HEIGHT);
		mCamera.position.set((MyGame.mCfg.MAP_WIDTH) / 2f+1, MyGame.mCfg.MAP_HEIGHT / 2f-1, 0); 
		// mCamera.position.x += 250;
		mCamera.zoom = 1f;
		mCamera.update();

		viewport = new StretchViewport(MyGame.mCfg.MAP_WIDTH, MyGame.mCfg.MAP_HEIGHT, mCamera);
		mStage1 = new Stage(viewport);
		mStage1.addActor(metor1);
		mStage1.addActor(metor2);
		mStage1.addActor(rainBow);
		
		//注意添加顺序，有层级关系
		group = new Group();
		group.addActor(land);
		group.addActor(science);
		group.addActor(math);
		group.addActor(local);
		group.addActor(fan);
        group.addActor(guoxue);
        group.addActor(life);        
        group.addActor(carton);
        group.addActor(music);
        group.addActor(paint);        
        group.addActor(language);
        group.addActor(setting);     
        group.addActor(explorer); 
        
        //岛上下摆动
//        SequenceAction ac_float = Actions.sequence(Actions.moveBy(0, -1, 1.5f), Actions.moveBy(0, 1, 1.5f));
//        group.addAction(Actions.forever(ac_float));
        
        //太阳
        mStage1.addActor(sun);
        mStage1.addActor(cloud1);

        //moon
        mStage1.addActor(moon);
		mStage1.addActor(group);  
        mStage1.addActor(logo);
        mStage1.addActor(speak);
		
		
		mStage1.setActionsRequestRendering(false);
	}
 
	
	//重置舞台监听
	public void setStage() {
		Configer.mylog("", "------------SCRMain.setStage");
		multiplexer.clear();
		multiplexer.addProcessor(mInput);
		multiplexer.addProcessor(mGesture);
		multiplexer.addProcessor(mStage1);

		Gdx.input.setCatchBackKey(true);
		Gdx.input.setCatchMenuKey(true);
		Gdx.input.setInputProcessor(multiplexer);
	}	
	
	void initRes(){
		Configer.mylog("","------------SCRMain initRes");
		if(mMap != null)
			return;
		
		float r = 0.0039215686274509803921568627451f;
		BitmapFont font = MyGame.mCfg.getFont();
		font.setColor(238*r, 136*r, 4*r, 1);
		font.getData().setScale(0.04f, 0.04f);
		font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);			
		night_music = MyGame.mCfg.getNightMusic();
		day_music = MyGame.mCfg.getDayMusic();
		night_music.setLooping(true);
		day_music.setLooping(true);

		mMap = MyGame.mCfg.getMap();
		mMapRenderer = new OrthogonalTiledMapRenderer(mMap, 1 / 32f);
		
		parserLayer();
	}
 
 
	//图层处理
    private void parserLayer(){
        MapLayers layers = mMap.getLayers();
        for(MapLayer layer : layers){//遍历图层
        	String name = layer.getName();
             if(name.equals("night_bg") || name.equals("day_bg")){//找到障碍层
                   mLayerMap.put(name, (TiledMapTileLayer) layer);
             }
        }
    }
 
	private void handleBg(){
		if(!MyGame.mCfg.mBhadsettime){
			return;
		}
		
		TiledMapTileLayer layer = mLayerMap.get("night_bg");

		if(MyGame.mCfg.mHour > 5 && MyGame.mCfg.mHour<18){
			//day
			if(MyGame.mCfg.mHour==17&&MyGame.mCfg.mMin==59){
				if(MyGame.mCfg.mSec > 54){
					Configer.mylog("", String.format("hour=%d,min=%s,se=%d", MyGame.mCfg.mHour,MyGame.mCfg.mMin,MyGame.mCfg.mSec));
					layer.setOpacity((MyGame.mCfg.mSec-54)* (1f/5f));
					return;
				}
			}
		 
			layer.setOpacity(0f);
 			return;
		}
		else{
			layer.setOpacity(1f);
 		}
	}
	
	
	//重置舞台绘制
	public void stageRender() {
		handleBg();
		
		mMapRenderer.render();
		//mMapRenderer.render(mLayer_scr1);
		
		// ！！数据共享！！
		MyGame.mCfg.mCurPage = mCurPg;
		
		mStage1.act(/*Gdx.graphics.getDeltaTime()*/);
		mStage1.draw();
	}
	
	//音乐播放状态相关
	boolean mLastModeisDay = true;
	boolean mbmusicfirst = true;
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
  
		//Configer.mylog("", "---------render------ ------"+delta);
		//优化音乐播放状态检测与切换
		if(MyGame.mCfg.mBgMusicOn){
			if(mLastModeisDay != MyGame.mCfg.mBDayMode || mbmusicfirst){
				if(MyGame.mCfg.mBDayMode){
					night_music.stop();
					day_music.play();
				}
				else{
					day_music.stop();
					night_music.play();
				}
				mLastModeisDay = MyGame.mCfg.mBDayMode;
				
				mbmusicfirst = false;
			}
		}
		
//		if(MyGame.mCfg.mBDayMode){
//			if(night_music.isPlaying()){
//				night_music.stop();
//			}
//			if(!day_music.isPlaying() && MyGame.mCfg.mBgMusicOn){
//				day_music.play();
//			}
//			
//		}
//		else{
//			if(day_music.isPlaying()){
//				day_music.stop();
//			}
//			if(!night_music.isPlaying() && MyGame.mCfg.mBgMusicOn){
//				night_music.play();
//			}
//		}
 

		//anim_move();
		anim_toDefault();
		//resetView();
		
		// 设置镜头跟随角色
		// mCamera.position.x = 700;
		// 镜头的更新与设置矩阵到SpriteBatch
		mCamera.update();
 
		// 设置摄像机
		mMapRenderer.setView(mCamera);
 
		this.stageRender();
		
		
		upateCloud();
		DrawCloud();
		
		
		drawPt();
 	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		Configer.mylog("", "------------SCRMain.resize");
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		Configer.mylog("", "------------SCRMain.pause");
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		Configer.mylog("", "------------SCRMain.resume");
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		Configer.mylog("", "------------SCRMain.hide");
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		Configer.mylog("", "------------SCRMain.dispose");
		mStage1.dispose();
		MyGame.getManager().dispose();
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		Configer.mylog("", "gesture..touchDown");
		return false;
	}

	int mMvStepsX=0;
	int mMvStepsY=0;
	
	//touchup
	@Override
	public boolean tap(float x, float y, int count, int button) {
		// TODO Auto-generated method stub
		Configer.mylog("", String.format("gesture..tap x=%f,y=%f,count=%d",x,y,count));
		
		if(x>0&&x<100&&y>350&&y<480){
			prevPage();
		}
		else if(x>700&&x<800&&y>350&&y<480){
			nextPage();
		}
		
		
		
		return false;
	}
	

	@Override
	public boolean longPress(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}

	
	private void prevPage(){
		if(mCurPg-1 < 0){
			mCurPg = 2;
		}
		else{
			mCurPg--;
		}
		MyGame.mCfg.mLastSwitchSrc_time = System.currentTimeMillis();
	}
	
	private void nextPage(){
		if(mCurPg+1 > 2){
			mCurPg = 0;
		}
		else{
			mCurPg++;
		}
		MyGame.mCfg.mLastSwitchSrc_time = System.currentTimeMillis();
	}
 
	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		// TODO Auto-generated method stub
		Configer.mylog("", String.format("gesture..fling x=%f,y=%f",velocityX,velocityY));
		
		if(velocityX>1000){
			prevPage();
		}
		else if(velocityX < -1000){
			nextPage();
		}
		return false;
	}

	float mXcnt = 0;
	float mYcnt = 0;
	
	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		// TODO Auto-generated method stub
		//Configer.mylog("", String.format("gesture..pan"));
		MyGame.mCfg.mLastSwitchSrc_time = System.currentTimeMillis();
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		Configer.mylog("", String.format("gesture..panstop x=%f,y=%f, pointer=%d",x,y, pointer));
		
		//清除累计
		mXcnt =   mYcnt = 0;
		
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		// TODO Auto-generated method stub
		//Configer.mylog("", String.format("zoom arg1=%f,arg2=%f, rat=%f", initialDistance, distance, distance/initialDistance));
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
		// TODO Auto-generated method stub
		Configer.mylog("", String.format("pinch  " ));
		return false;
	}
	
	float mMovePeriod = 0f;	// 0.1f = 100ms
	private void anim_move(){
		mMovePeriod += 	Gdx.graphics.getDeltaTime();	
		if(mMovePeriod >= 0.1f){
			if(mMvStepsX > 0){
				mCamera.position.x ++;
				mMvStepsX --;
			}
			else if(mMvStepsX < 0){ 
				mCamera.position.x --;
				mMvStepsX ++;
			}
			
			if(mMvStepsY > 0){
				mCamera.position.y ++;
				mMvStepsY --;
			}
			else if(mMvStepsY < 0){
				mCamera.position.y --;
				mMvStepsY ++;
			}			
			
			mMovePeriod = 0;
		}
	}

	private boolean mbfirst = true;
	float mToDftPeriod = 0f;	// 0.1f = 100ms
 	private void anim_toDefault(){
		if(!mbfirst)
			return;

		mToDftPeriod  += Gdx.graphics.getDeltaTime();	
		if(mToDftPeriod >= 0.02f){
			if((mCamera.zoom - 0.55f) < 0.0000000000000001f){
				resetView();
				mbfirst = false;
			}	
			else{
				//Configer.mylog("", "anim_toDefault: zoom="+mCamera.zoom);
				mCamera.zoom -= 0.01f;
				mCamera.update();	
			}
			mToDftPeriod = 0f;
		}
		MyGame.mCfg.mCurCameraZoom = mCamera.zoom;
	}
	
	private void resetView(){
		mCamera.position.set((MyGame.mCfg.MAP_WIDTH) / 2f+1, MyGame.mCfg.MAP_HEIGHT / 2f-1, 0); // 设置相机居中
		mCamera.zoom = 0.5f;
		MyGame.mCfg.mCurCameraZoom = mCamera.zoom;
		//mCamera.translate(1, -1);
		mCamera.update();
		
	    Gdx.graphics.setContinuousRendering(false);
        Gdx.graphics.requestRendering();
	}
	
}	
	