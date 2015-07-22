package ui.story.lemoon.scr.v2;

import java.util.HashMap;

import ui.story.lemoon.Configer;
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
	
	private Stage mStage1;//, mStage2, mStage3;
	private OrthographicCamera mCamera;
	private MapRenderer mMapRenderer;
	TiledMap mMap;
	Viewport viewport;	
	Configer mCfg;
	InputMultiplexer multiplexer = new InputMultiplexer();
	GestureDetector mGesture;
	InputProcessor  mInput;
	HashMap<String, TiledMapTileLayer> mLayerMap = new HashMap<String, TiledMapTileLayer>();

	SpriteBatch batch;
	OrthographicCamera camera;
	AtlasRegion mbottomCloud;
	float mcloudOffsetX,mcloudOffsetY, mCloudW,mCloud2W;
	
	Music night_music;
	
	public SCRMain(Game g){
		Gdx.app.log("", "------------SCRMain.SCRMain");
		mCfg = new Configer();
		mCfg.game = (MyGame)g;
	}
	
	
	
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		Gdx.app.log("", "------------SCRMain.show");
		initRes();
		initStageItems();
		
		 
		initforcloud();
		
		mGesture = new GestureDetector(this);
		mInput = new InputProcessor() {
			@Override
			public boolean touchUp(int screenX, int screenY, int pointer, int button) {
				// TODO Auto-generated method stub
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
			public boolean keyDown(int keycode) {
				// TODO Auto-generated method stub
				if(keycode == Input.Keys.BACK || keycode==Input.Keys.MENU){
					Gdx.app.log("", "========SCRMain keydown: back|menu");
					return true;
				}
				return false;
			}
		};
		
		
		
		setStage() ;
	}
	
	
	//对底部云层的绘制实现了 一张图片持续整屏幕滚动效果
	void initforcloud(){
		mcloudOffsetX = 0f;		mcloudOffsetY=23f;
		
		batch = new SpriteBatch();
		 
        //移动的camera，该camera与屏幕尺寸相应，且仅仅变化x轴坐标。
		//所以其y轴固定，不会随着map的变换而变换，因为spritebatch是独立的。
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.zoom = 0.9f;	//这个是调试得到的，zoom设置必须要在上面设置大小之后调用才行，否则会出问题。

		mbottomCloud = mCfg.mTextureAltas.findRegion("bottom_cloud");
		mCloudW = mbottomCloud.getRegionWidth();
		mCloud2W = 2*mCloudW;
	}

	void upateCloud(){
		camera.position.x += 1.2f;		
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
	
	void initStageItems(){
		mCamera = new OrthographicCamera(mCfg.MAP_WIDTH, mCfg.MAP_HEIGHT);
		mCamera.position.set((mCfg.MAP_WIDTH) / 2f+1, mCfg.MAP_HEIGHT / 2f-1, 0); 
		// mCamera.position.x += 250;
		mCamera.zoom = 1f;
		mCamera.update();

		viewport = new StretchViewport(mCfg.MAP_WIDTH, mCfg.MAP_HEIGHT, mCamera);
		mStage1 = new Stage(viewport);
		
		
		mStage1.addActor(new RainBowActor(mCfg));
		
		//注意添加顺序，有层级关系
		Group group = new Group();
		group.addActor(new LandActor(mCfg));
		group.addActor(new ScienceActor(mCfg));
		group.addActor(new MathActor(mCfg));
		group.addActor(new LocalActor(mCfg));
		group.addActor(new FanActor(mCfg));
        group.addActor(new GuoXueActor(mCfg));
        group.addActor(new LifeActor(mCfg));        
        group.addActor(new CartonActor(mCfg));
        group.addActor(new MusicActor(mCfg));
        group.addActor(new PaintActor(mCfg));        
        group.addActor(new LanguageActor(mCfg));
        group.addActor(new SettingActor(mCfg));        
        
        //岛上下摆动
//        SequenceAction ac_float = Actions.sequence(Actions.moveBy(0, -1, 1.5f), Actions.moveBy(0, 1, 1.5f));
//        group.addAction(Actions.forever(ac_float));
        
        //太阳
        mStage1.addActor(new SunActor(mCfg));
        
        mStage1.addActor(new CloudActor1(mCfg));
        

        
        //moon
        mStage1.addActor(new MoonActor(mCfg));

        
		mStage1.addActor(group);  
		
		
        mStage1.addActor(new LogoActor(mCfg));

	}
 
	
	
	//重置舞台监听
	public void setStage() {
		Gdx.app.log("", "------------SCRMain.setStage");
		multiplexer.clear();
		multiplexer.addProcessor(mInput);
		multiplexer.addProcessor(mGesture);
		multiplexer.addProcessor(mStage1);

		Gdx.input.setCatchBackKey(true);
		Gdx.input.setCatchMenuKey(true);
		Gdx.input.setInputProcessor(multiplexer);
	}	
	
	void initRes(){
		Gdx.app.log("","------------SCRMain initRes");
		AssetManager mgr = MyGame.getManager();
		if(mgr.isLoaded("v2/datas/items.atlas")){
			//Gdx.app.log("","------------SCRMain initRes.atlas loaded!");
		}
		mCfg.mTextureAltas = mgr.get("v2/datas/items.atlas",TextureAtlas.class);
		
		if (mgr.isLoaded("font/arial-15.fnt")){
			mCfg.font = mgr.get("font/arial-15.fnt", BitmapFont.class);
			
			float r = 0.0039215686274509803921568627451f;
			mCfg.font.setColor(238*r, 136*r, 4*r, 1);
			mCfg.font.getData().setScale(0.04f, 0.04f);
			mCfg.font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
		
		night_music = Gdx.audio.newMusic(Gdx.files.internal("v2/datas/night.ogg"));
		night_music.setLooping(true);
		//night_music.play();
		
		if (mgr.isLoaded("v2/map/v2map.tmx")){
			//Gdx.app.log("", "------------ map loaded");
		}		
		mMap = mgr.get("v2/map/v2map.tmx", TiledMap.class);		
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
		if(!mCfg.mBhadsettime){
			return;
		}
		
		TiledMapTileLayer layer = mLayerMap.get("night_bg");

		if(mCfg.mHour > 5 && mCfg.mHour<18){
			//day
			if(mCfg.mHour==17&&mCfg.mMin==59){
				if(mCfg.mSec > 54){
					Gdx.app.log("", String.format("hour=%d,min=%s,se=%d", mCfg.mHour,mCfg.mMin,mCfg.mSec));
					layer.setOpacity((mCfg.mSec-54)* (1f/5f));
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
		mCfg.mCurPage = mCurPg;
		
		mStage1.act();
		mStage1.draw();
	}
	
	
	
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
  
		if(mCfg.mBDayMode){
			if(night_music.isPlaying()){
				night_music.stop();
			}
		}
		else{
			if(!night_music.isPlaying()){
				night_music.play();
			}
		}
		
		
		
		
		// 防止一帧消耗时间过长， 导致卡顿走动现象
		//float delta = Math.min(0.06f, Gdx.graphics.getDeltaTime());
		//Gdx.app.log("", "delta="+Gdx.graphics.getDeltaTime());
		

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
 	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		Gdx.app.log("", "------------SCRMain.resize");
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		Gdx.app.log("", "------------SCRMain.pause");
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		Gdx.app.log("", "------------SCRMain.resume");
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		Gdx.app.log("", "------------SCRMain.hide");
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		Gdx.app.log("", "------------SCRMain.dispose");
		mStage1.dispose();
//		mStage2.dispose();
//		mStage3.dispose();
		
		MyGame.getManager().dispose();
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		//Gdx.app.log("", "touchDown");
		return false;
	}

	int mMvStepsX=0;
	int mMvStepsY=0;
	
	//touchup
	@Override
	public boolean tap(float x, float y, int count, int button) {
		// TODO Auto-generated method stub
		//Gdx.app.log("", String.format("tap x=%f,y=%f,count=%d",x,y,count));
		
//		int w = Gdx.graphics.getWidth();
//		int h = Gdx.graphics.getHeight();
//		
//		//x,y方向各个方向可供移动的unit单位。
//		int canmvunitX = (int)(MAP_WIDTH - w/32)/2;
//		int canmvunitY = (int)(MAP_HEIGHT-h/32)/2;
//		int centerunitX = (int) MAP_WIDTH/2;
//		int centerunitY = (int)MAP_HEIGHT/2;
//		
//		
//		//获得点击处到中心点的像素偏移，可能正负
//		float offx = x-w/2f;
//		float offy = (h-y)-h/2f;	//点击坐标与libgdx的坐标系有点儿区别，y轴的区别。
//		
//		//设定x方向和y方向的 非响应区域，即不能偏移一个单元格也要取移动，这样会太灵敏而感觉不好控制
//		float xrange = w/8f;  //中间 1/8的区域不触发
//		float yrange = h/3f; // 中间 1/3的区域不触发
//		
//		//如果x有偏移
//		if(Math.abs(offx) > xrange){
//			mMvStepsX = (int)offx/32; //单位换算为unit
//			if(mMvStepsX > 0){
//				int mx =(int) (mCamera.position.x + mMvStepsX - centerunitX); //计算总偏移
//				if(mx > canmvunitX){
//					mMvStepsX -= (mx-canmvunitX); 
//				}
//			}
//			else if(mMvStepsX < 0){
//				int mx = (int)(centerunitX - mCamera.position.x - mMvStepsX);
//				if(mx > canmvunitX){
//					mMvStepsX += (mx - canmvunitX);
//				}
//			}
//		}
//		
//		//如果y有偏移
//		if(Math.abs(offy) > yrange){
//			mMvStepsY = (int) offy/32; //单位换算为unit
//			if(mMvStepsY > 0){
//				int my = (int)(mCamera.position.y + mMvStepsY - centerunitY);
//				if(my > canmvunitY){
//					mMvStepsY -= (my - canmvunitY);
//				}
//			}
//			else if(mMvStepsY < 0){
//				int my = (int)(centerunitY - mCamera.position.y - mMvStepsY);
//				if(my > canmvunitY){
//					mMvStepsY += (my - canmvunitY);
//				}
//			}
//		}
		
		
		return false;
	}
	

	@Override
	public boolean longPress(float x, float y) {
		// TODO Auto-generated method stub
		Gdx.app.log("", String.format("longPress x=%f,y=%f",x,y));
		
		resetView();
		
		return true;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		// TODO Auto-generated method stub
		Gdx.app.log("", String.format("fling x=%f,y=%f",velocityX,velocityY));
		
		if(velocityX>1000){
			if(mCurPg-1 < 0){
				mCurPg = 2;
			}
			else{
				mCurPg--;
			}
		}
		else if(velocityX < -1000){
			if(mCurPg+1 > 2){
				mCurPg = 0;
			}
			else{
				mCurPg++;
			}
		}
		return false;
	}

	float mXcnt = 0;
	float mYcnt = 0;
	
	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		// TODO Auto-generated method stub
//		Gdx.app.log("", String.format("pan"));
		
//		mXcnt += deltaX;
//		mYcnt += deltaY;
//		
//		//Gdx.app.log("", String.format("pan x=%f,y=%f, deltax=%f, deltaY=%f",x,y, mXcnt, mYcnt));
//		
//		int w = Gdx.graphics.getWidth();
//		int h = Gdx.graphics.getHeight();
//		
//		//x,y方向各个方向可供移动的unit单位。
//		int canmvunitX = (int)(MAP_WIDTH - w/32)/2;
//		int canmvunitY = (int)(MAP_HEIGHT-h/32)/2;
//		int centerunitX = (int) MAP_WIDTH/2;
//		int centerunitY = (int)MAP_HEIGHT/2;		
//		
//		mMvStepsY = mMvStepsX = 0;
//		
//		//////////// 边界判断 /////////////
//		int offx = (int)mXcnt/32; //单位换算为unit
//		if(Math.abs(offx) > 0){	
//			mXcnt = 0;//清除累计
//			if(offx > 0){ //向右移动，相机的x偏移要设为 负数	
//				int mx = (int)(centerunitX - (mCamera.position.x - offx));
//				
//				if(mx >= canmvunitX){
//					offx = 0;
//				}			
//
//			}
//			else if(offx < 0){ //向左移动
//				int mx =(int) (mCamera.position.x - offx - centerunitX); //计算总偏移
//				
//				if(mx >= canmvunitX){
//					offx = 0;
//				}
//			}
//		}
//
//		int offy = (int)mYcnt/32;
//		if(Math.abs(offy) > 0){
//			mYcnt = 0;//清除累计
//			if(offy > 0){
//				int my = (int)(mCamera.position.y + offy - centerunitY);
//				if(my > canmvunitY){
//					offy = 0;
//				}
//			}
//			else if(offy < 0){
//				int my = (int)(centerunitY - mCamera.position.y - offy);
//				if(my > canmvunitY){
//					offy = 0;
//				}
//			}
//		}		
//		mCamera.translate(-offx, offy);
//		mCamera.update();
		
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		Gdx.app.log("", String.format("panstop x=%f,y=%f, pointer=%d",x,y, pointer));
		
		//清除累计
		mXcnt =   mYcnt = 0;
		
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		// TODO Auto-generated method stub
		//Gdx.app.log("", String.format("zoom arg1=%f,arg2=%f, rat=%f", initialDistance, distance, distance/initialDistance));
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
		// TODO Auto-generated method stub
		//Gdx.app.log("", String.format("pinch  " ));
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
				//Gdx.app.log("", "anim_toDefault: zoom="+mCamera.zoom);
				mCamera.zoom -= 0.01f;
				mCamera.update();	
			}
			mToDftPeriod = 0f;
		}
		mCfg.mCurCameraZoom = mCamera.zoom;

	}
	
	private void resetView(){
		mCamera.position.set((mCfg.MAP_WIDTH) / 2f+1, mCfg.MAP_HEIGHT / 2f-1, 0); // 设置相机居中
		mCamera.zoom = 0.5f;
		mCfg.mCurCameraZoom = mCamera.zoom;
		//mCamera.translate(1, -1);
		mCamera.update();
	}
	
}	
	