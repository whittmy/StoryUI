package ui.story.lemoon.scr;

import sun.applet.Main;
import ui.story.lemoon.Anim_FansActor;
import ui.story.lemoon.Anim_MiscActor;
import ui.story.lemoon.MyGame;
import ui.story.lemoon.msg.MyMsg;
import ui.story.lemoon.nouse.nouse_Anim_RippleActor;
import ui.story.lemoon.nouse.nouse_FaceActor;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Main1Src implements Screen, GestureListener {
	MyGame game;
	int mCurPg = 0;

	private int MAP_WIDTH = 50;
	private int MAP_HEIGHT = 30;	

	private Stage mStage0, mStage1, mStage2;
	private OrthographicCamera mCamera;
	private MapRenderer mMapRenderer;
	TiledMap mMap;
	Viewport viewport;	
	
	InputMultiplexer multiplexer = new InputMultiplexer();
	
 
	class GuoXueActor extends Actor{
		public GuoXueActor(){
			setBounds(28, MAP_HEIGHT-20-1, 4, 2);
			addListener(new InputListener(){
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					// TODO Auto-generated method stub
					//Gdx.app.log("", "GuoXue down x="+x+", y="+y);
					MyMsg msg = new MyMsg();
					msg.what = MyMsg.ITEM_GUOXUE;
					game.notify(msg);
					//return super.touchDown(event, x, y, pointer, button);
					return true;
				}
			});
		}
	}
	
	class LifeActor extends Actor{
		public LifeActor(){
			setBounds(16, MAP_HEIGHT-20-1, 4, 4); //这个很重要，集成Actor后，如果不设置边界，可能无法被touch或点击
			addListener(new InputListener(){
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					// TODO Auto-generated method stub
					//Gdx.app.log("", "Life down x="+x+", y="+y);
					MyMsg msg = new MyMsg();
					msg.what = MyMsg.ITEM_LIFE;
					game.notify(msg);
					//return super.touchDown(event, x, y, pointer, button);
					return true;
				}
			});			
		}
	}
	
	class ScienceActor extends Actor{
		public ScienceActor(){
			setBounds(26, MAP_HEIGHT-18-1, 3, 4); //这个很重要，集成Actor后，如果不设置边界，可能无法被touch或点击
			addListener(new InputListener(){
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					// TODO Auto-generated method stub
					//Gdx.app.log("", "Science down x="+x+", y="+y);
					MyMsg msg = new MyMsg();
					msg.what = MyMsg.ITEM_SCIENCE;
					game.notify(msg);
					//return super.touchDown(event, x, y, pointer, button);
					return true;
				}
			});	
		}
	}
	
	class CartonActor extends Actor{
		public CartonActor(){
			setBounds(21, MAP_HEIGHT-16-1, 4, 3);  //这个很重要，集成Actor后，如果不设置边界，可能无法被touch或点击
			addListener(new InputListener(){
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					// TODO Auto-generated method stub
					//Gdx.app.log("", "Carton down x="+x+", y="+y);
					MyMsg msg = new MyMsg();
					msg.what = MyMsg.ITEM_CARTON;
					game.notify(msg);
					//return super.touchDown(event, x, y, pointer, button);
					return true;
				}
			});	
		}
	}
	
	
	//-------------
	class MusicActor extends Actor{
		public MusicActor(){
			setBounds(16, MAP_HEIGHT-20-1, 3, 4); //这个很重要，集成Actor后，如果不设置边界，可能无法被touch或点击
			addListener(new InputListener(){
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					// TODO Auto-generated method stub
					//Gdx.app.log("", "Music down x="+x+", y="+y);
					MyMsg msg = new MyMsg();
					msg.what = MyMsg.ITEM_MUSIC;
					game.notify(msg);
					//return super.touchDown(event, x, y, pointer, button);
					return true;
				}
			});	
		}
	}
	
	class PaintActor extends Actor{
		public PaintActor(){
			setBounds(21, MAP_HEIGHT-16-1, 4, 4); //这个很重要，集成Actor后，如果不设置边界，可能无法被touch或点击
			addListener(new InputListener(){
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					// TODO Auto-generated method stub
					//Gdx.app.log("", "Paint down x="+x+", y="+y);
					MyMsg msg = new MyMsg();
					msg.what = MyMsg.ITEM_PAINT;
					game.notify(msg);
					//return super.touchDown(event, x, y, pointer, button);
					return true;
				}
			});	
		}
	}
	
	class MathActor extends Actor{
		public MathActor(){
			setBounds(28, MAP_HEIGHT-16-1, 4, 2); //这个很重要，集成Actor后，如果不设置边界，可能无法被touch或点击
			addListener(new InputListener(){
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					// TODO Auto-generated method stub
					//Gdx.app.log("", "Math down x="+x+", y="+y);
					MyMsg msg = new MyMsg();
					msg.what = MyMsg.ITEM_MATHS;
					game.notify(msg);
					//return super.touchDown(event, x, y, pointer, button);
					return true;
				}
			});	
		}
	}
	
	class LanguageActor extends Actor{
		public LanguageActor(){
			setBounds(28, MAP_HEIGHT-21-1, 4, 4); //这个很重要，集成Actor后，如果不设置边界，可能无法被touch或点击
			addListener(new InputListener(){
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					// TODO Auto-generated method stub
					//Gdx.app.log("", "Language down x="+x+", y="+y);
					MyMsg msg = new MyMsg();
					msg.what = MyMsg.ITEM_LANGUAGE;
					game.notify(msg);
					//return super.touchDown(event, x, y, pointer, button);
					return true;
				}
			});	
		}
	}	
	
	//--------
	class LocalActor extends Actor{
		public LocalActor(){
			setBounds(27, MAP_HEIGHT-20-1, 5, 5); //这个很重要，集成Actor后，如果不设置边界，可能无法被touch或点击
			addListener(new InputListener(){
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					// TODO Auto-generated method stub
					//Gdx.app.log("", "Local down x="+x+", y="+y);
					MyMsg msg = new MyMsg();
					msg.what = MyMsg.ITEM_LOCAL;
					game.notify(msg);
					//return super.touchDown(event, x, y, pointer, button);
					return true;
				}
			});	
		}
	}
	
	class SettingActor extends Actor{
		public SettingActor(){
			setBounds(16, MAP_HEIGHT-20-1, 4, 2); //这个很重要，集成Actor后，如果不设置边界，可能无法被touch或点击
			addListener(new InputListener(){
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					// TODO Auto-generated method stub
					//Gdx.app.log("", "Setting down x="+x+", y="+y);
					MyMsg msg = new MyMsg();
					msg.what = MyMsg.ITEM_SETTING;
					game.notify(msg);
					//return super.touchDown(event, x, y, pointer, button);
					return true;
				}
			});	
		}
	}	
	
	
	
	public Main1Src(Game g){
		game = (MyGame)g;
	}
	
	
	GestureDetector mGesture;
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		mCamera = new OrthographicCamera(MAP_WIDTH, MAP_HEIGHT);
		mCamera.position.set((MAP_WIDTH) / 2f+1, MAP_HEIGHT / 2f-1, 0); 
		// mCamera.position.x += 250;
		mCamera.zoom = 1f;
		mCamera.update();
 
		
		AssetManager mgr = MyGame.getManager();
		
		
		//mMap = new TmxMapLoader().load("map/map2.tmx");
		if (mgr.isLoaded("map/map3.tmx")){
			Gdx.app.log("", "map loaded");
		}		
		
		mMap = mgr.get("map/map3.tmx", TiledMap.class);
		mMapRenderer = new OrthogonalTiledMapRenderer(mMap, 1 / 32f);

		viewport = new StretchViewport(MAP_WIDTH, MAP_HEIGHT, mCamera);
		mStage0 = new Stage(viewport);
		mStage1 = new Stage(viewport);
		mStage2 = new Stage(viewport);
 
		mStage0.addActor(new Anim_FansActor());
		mStage0.addActor(new Anim_MiscActor());
		mStage0.addActor(new GuoXueActor());
		mStage0.addActor(new LifeActor());
		mStage0.addActor(new ScienceActor());
		mStage0.addActor(new CartonActor());


		mStage1.addActor(new Anim_MiscActor());
		mStage1.addActor(new MusicActor());
		mStage1.addActor(new PaintActor());
		mStage1.addActor(new MathActor());
		mStage1.addActor(new LanguageActor());
		
		
		mStage2.addActor(new Anim_MiscActor());	
		mStage2.addActor(new LocalActor());
		mStage2.addActor(new SettingActor());
 
		
		mGesture = new GestureDetector(this);
		//multiplexer.addProcessor(mStage0);
		//multiplexer.addProcessor);
		//Gdx.input.setInputProcessor(multiplexer);
	}

 
	boolean mbSetStage = true;
	//重置舞台监听
	public void setStage() {
		if(!mbSetStage)
			return;
		Gdx.app.log("", "!!!!setStage");
		multiplexer.clear();
		multiplexer.addProcessor(mGesture);
		
		switch(mCurPg){
		case 0:
			multiplexer.addProcessor(mStage0);
			break;
		case 1:
			multiplexer.addProcessor(mStage1);
			break;
		case 2:
			multiplexer.addProcessor(mStage2);
			break;
		}
 
		Gdx.input.setInputProcessor(multiplexer);
		mbSetStage = false;
	}
	
	//重置舞台绘制
	public void stageRender() {
		switch(mCurPg){
		case 0:
			// 地图绘制控制
			mMapRenderer.render(mLayer_scr1);
			
			//舞台
			mStage0.act();
			mStage0.draw();
			
			mMapRenderer.render(mLayer_scr1_cover);		
			break;
		case 1:
			mMapRenderer.render(mLayer_scr2);
			mStage1.act();
			mStage1.draw();
			break;
		case 2:
			mMapRenderer.render(mLayer_scr3);
			mStage2.act();
			mStage2.draw();
			break;
		}
	}
	
	
	int[] mLayer_scr1 = new int[] { 0,1}; //include fan object
	int[] mLayer_scr1_cover = new int[]{2}; // for beautiful fan
	
	int[] mLayer_scr2 = new int[]{0, 3};
	int[] mLayer_scr3 = new int[]{0, 4};

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// 防止一帧消耗时间过长， 导致卡顿走动现象
		//float delta = Math.min(0.06f, Gdx.graphics.getDeltaTime());
		//Gdx.app.log("", "delta="+Gdx.graphics.getDeltaTime());
		

		anim_move();
		anim_toDefault();
		
		// 设置镜头跟随角色
		// mCamera.position.x = 700;
		// 镜头的更新与设置矩阵到SpriteBatch
		mCamera.update();
 
		// 设置摄像机
		mMapRenderer.setView(mCamera);
 
		this.stageRender();
		this.setStage();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		mStage0.dispose();
		mStage1.dispose();
		mStage2.dispose();
		
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
			
			mbSetStage = true;

			Gdx.app.log("", "change1 mCurpg="+mCurPg);
		}
		else if(velocityX < -1000){
			if(mCurPg+1 > 2){
				mCurPg = 0;
			}
			else{
				mCurPg++;
			}
			
			mbSetStage = true;

			Gdx.app.log("", "change2 mCurpg="+mCurPg);
		}
		
		return false;
	}

	float mXcnt = 0;
	float mYcnt = 0;
	
	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		// TODO Auto-generated method stub
		Gdx.app.log("", String.format("pan"));
		
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
		Gdx.app.log("", String.format("zoom arg1=%f,arg2=%f, rat=%f", initialDistance, distance, distance/initialDistance));
		
		if((distance/initialDistance-1.0f)>0 && (mCamera.zoom-0.46f)>0){
			mCamera.zoom -= 0.01;
			mCamera.update();
		}
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
		// TODO Auto-generated method stub
		Gdx.app.log("", String.format("pinch  " ));
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
	}
	
	private void resetView(){
		mCamera.position.set((MAP_WIDTH) / 2f+1, MAP_HEIGHT / 2f-1, 0); // 设置相机居中
		mCamera.zoom = 0.55f;
		//mCamera.translate(1, -1);
		mCamera.update();
	}
	
}	
	