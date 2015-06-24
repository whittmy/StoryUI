package ui.story.lemoon;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class nouse_MainUI extends ApplicationAdapter implements GestureListener {
	public BsuEvent bevent = null;		//用于通知desktop或android等其他平台执行某些操作
	
	
	nouse_FaceActor mFace;
	Anim_FansActor mFan;
	nouse_Anim_RippleActor mRipper;
	Anim_MiscActor mFlow;

	
	
	
	private int WIDTH = 56;
	private int HEIGHT = 32;
//	private int WIDTH = 111;
//	private int HEIGHT = 71;	

	private Stage mStage;
	private OrthographicCamera mCamera;
	private MapRenderer mMapRenderer;
	TiledMap mMap;
	Viewport viewport;

	public nouse_MainUI(BsuEvent be){
		bevent = be;
		//bevent.notify(this, "test");
	}
	
	
	@Override
	public void create() {
		mCamera = new OrthographicCamera(WIDTH, HEIGHT);
		mCamera.position.set((WIDTH) / 2f, HEIGHT / 2f, 0); // 设置相机居中
		// mCamera.position.x += 250;
		mCamera.zoom = 1f;
		mCamera.update();

		mMap = new TmxMapLoader().load("map/map2.tmx");
		mMapRenderer = new OrthogonalTiledMapRenderer(mMap, 1 / 32f);

		viewport = new StretchViewport(WIDTH, HEIGHT, mCamera);
		mStage = new Stage(viewport);

//		mFan = new Anim_FansActor();
//		mRipper = new Anim_RippleActor();
//		mFlow = new Anim_FlowActor();

		mStage.addActor(mFan);
		mStage.addActor(mRipper);
		mStage.addActor(mFlow);

		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(new GestureDetector(this));
		multiplexer.addProcessor(mStage);
		//multiplexer.addProcessor(this)
		Gdx.input.setInputProcessor(multiplexer);

		Gdx.input.setInputProcessor(multiplexer);
	}

	
	int[] mLayer0 = new int[] { 0 };
	int[] mLayer1 = new int[] { 1 };
	@Override
	public void render() {
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

		// 舞台更新
		mStage.act(Gdx.graphics.getDeltaTime());

		// 设置摄像机
		mMapRenderer.setView(mCamera);
		// 地图绘制
		//mMapRenderer.render(new int[] { 0 });
		mMapRenderer.render(mLayer0);

		// 舞台绘制
		mStage.draw();

		//mMapRenderer.render(new int[] { 1 });
		mMapRenderer.render(mLayer1);
 
	}
	
	
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		super.dispose();

		//mFan.dispose();
		mMap.dispose();
		//mFace.dispose();
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		Gdx.app.log("", "touchDown");
		return false;
	}

	int mMvStepsX=0;
	int mMvStepsY=0;
	
	//touchup
	@Override
	public boolean tap(float x, float y, int count, int button) {
		// TODO Auto-generated method stub
		Gdx.app.log("", String.format("tap x=%f,y=%f,count=%d",x,y,count));
		
		int w = Gdx.graphics.getWidth();
		int h = Gdx.graphics.getHeight();
		
		//x,y方向各个方向可供移动的unit单位。
		int canmvunitX = (int)(WIDTH - w/32)/2;
		int canmvunitY = (int)(HEIGHT-h/32)/2;
		int centerunitX = (int) WIDTH/2;
		int centerunitY = (int)HEIGHT/2;
		
		
		//获得点击处到中心点的像素偏移，可能正负
		float offx = x-w/2f;
		float offy = (h-y)-h/2f;	//点击坐标与libgdx的坐标系有点儿区别，y轴的区别。
		
		//设定x方向和y方向的 非响应区域，即不能偏移一个单元格也要取移动，这样会太灵敏而感觉不好控制
		float xrange = w/8f;  //中间 1/8的区域不触发
		float yrange = h/3f; // 中间 1/3的区域不触发
		
		//如果x有偏移
		if(Math.abs(offx) > xrange){
			mMvStepsX = (int)offx/32; //单位换算为unit
			if(mMvStepsX > 0){
				int mx =(int) (mCamera.position.x + mMvStepsX - centerunitX); //计算总偏移
				if(mx > canmvunitX){
					mMvStepsX -= (mx-canmvunitX); 
				}
			}
			else if(mMvStepsX < 0){
				int mx = (int)(centerunitX - mCamera.position.x - mMvStepsX);
				if(mx > canmvunitX){
					mMvStepsX += (mx - canmvunitX);
				}
			}
		}
		
		//如果y有偏移
		if(Math.abs(offy) > yrange){
			mMvStepsY = (int) offy/32; //单位换算为unit
			if(mMvStepsY > 0){
				int my = (int)(mCamera.position.y + mMvStepsY - centerunitY);
				if(my > canmvunitY){
					mMvStepsY -= (my - canmvunitY);
				}
			}
			else if(mMvStepsY < 0){
				int my = (int)(centerunitY - mCamera.position.y - mMvStepsY);
				if(my > canmvunitY){
					mMvStepsY += (my - canmvunitY);
				}
			}
		}
		
		
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
		return false;
	}

	float mXcnt = 0;
	float mYcnt = 0;
	
	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		// TODO Auto-generated method stub
		Gdx.app.log("", String.format("pan"));
		
		mXcnt += deltaX;
		mYcnt += deltaY;
		
		//Gdx.app.log("", String.format("pan x=%f,y=%f, deltax=%f, deltaY=%f",x,y, mXcnt, mYcnt));
		
		int w = Gdx.graphics.getWidth();
		int h = Gdx.graphics.getHeight();
		
		//x,y方向各个方向可供移动的unit单位。
		int canmvunitX = (int)(WIDTH - w/32)/2;
		int canmvunitY = (int)(HEIGHT-h/32)/2;
		int centerunitX = (int) WIDTH/2;
		int centerunitY = (int)HEIGHT/2;		
		
		mMvStepsY = mMvStepsX = 0;
		
		//////////// 边界判断 /////////////
		int offx = (int)mXcnt/32; //单位换算为unit
		if(Math.abs(offx) > 0){	
			mXcnt = 0;//清除累计
			if(offx > 0){ //向右移动，相机的x偏移要设为 负数	
				int mx = (int)(centerunitX - (mCamera.position.x - offx));
				
				if(mx >= canmvunitX){
					offx = 0;
				}			

			}
			else if(offx < 0){ //向左移动
				int mx =(int) (mCamera.position.x - offx - centerunitX); //计算总偏移
				
				if(mx >= canmvunitX){
					offx = 0;
				}
			}
		}

		int offy = (int)mYcnt/32;
		if(Math.abs(offy) > 0){
			mYcnt = 0;//清除累计
			if(offy > 0){
				int my = (int)(mCamera.position.y + offy - centerunitY);
				if(my > canmvunitY){
					offy = 0;
				}
			}
			else if(offy < 0){
				int my = (int)(centerunitY - mCamera.position.y - offy);
				if(my > canmvunitY){
					offy = 0;
				}
			}
		}		
		mCamera.translate(-offx, offy);
		mCamera.update();
		
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
			if((mCamera.zoom - 0.46f)< 0.1f){
				resetView();
				mbfirst = false;
			}	
			else{
				Gdx.app.log("", "anim_toDefault: zoom="+mCamera.zoom);
				mCamera.zoom -= 0.01f;
				mCamera.update();	
			}

			mToDftPeriod = 0f;
		}
	}
	
	private void resetView(){
		mCamera.position.set((WIDTH) / 2f+2, HEIGHT / 2f-1, 0); // 设置相机居中
		mCamera.zoom = 0.46f;
		//mCamera.translate(1, -1);
		mCamera.update();
	}
	
	
	
}
