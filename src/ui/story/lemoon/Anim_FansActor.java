package ui.story.lemoon;

import sun.java2d.pipe.TextRenderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateToAction;
import com.badlogic.gdx.utils.viewport.StretchViewport;

//！！！！！！ 非常重要 ！！！！！！！！！！
// 该Actor的坐标系统是和地图Map的系统完全一致的，是基于我们设定的单位Unit，这里设定一个地图瓦片就代表一个unit(32*32pix)， 完整地图的尺寸56*32units, 这也就是坐标系统。
// 但注意： Actor的坐标并不是默认就于map保持一致的，必须需要舞台Stage的坐标系统与map一致才行，所以要对Stage进行些处理， stage与map如何管理呢？
//		mCamera = new OrthographicCamera(WIDTH, HEIGHT);
//		mCamera.position.set(WIDTH/2f, HEIGHT/2f,0);  //设置相机居中
//		mCamera.zoom = 0.3f;
//		mCamera.update();	
//		
//		mMap = new TmxMapLoader().load("map/map.tmx");
//		mMapRenderer = new OrthogonalTiledMapRenderer(mMap, 1/32f);
//		
//		viewport = new StretchViewport(WIDTH,HEIGHT,mCamera);   // ！！！！！！！！！！！就是这儿的两句!!!!!！！！！！
//		mStage = new Stage(viewport);


///////// 做了以上设置之后，map的坐标系统、相机方可对Stage舞台上的内容 适应 /////////





public class Anim_FansActor extends Actor{
	float actorX, actorY;
	Texture texture;
	TextureRegion mTextureRegion;
	
	/// 整个地图的尺寸 
	int mMapWidth = 56;
	int mMapHeight = 32;
	
	int mUheight, mUWidth;
	int mUnitSize = 32;
	float xorg ,yorg;
	public Anim_FansActor(Texture t) {
		// TODO Auto-generated constructor stub
		super();
		
		// actorX/actorY为libGDX坐标系统上（原点左下角），风车所在的位置(actorX, actorY)，该点位风车图片的左下角位置。
		// 但是TileMapEditor中查看位置，其坐标是相对于 右上角的，如风车图片左下 角的坐标为(33, 18)
		// 地图=>libgdx，坐标转换的方式如下：
		actorX = 33f; 
		actorY = (mMapHeight-18-1);
		
		
		/// 风车的尺寸为 3*3units
		mUheight =  mUWidth = 3;
		
		///unit对应的像素数
		mUnitSize = 32;
		
		///旋转、缩放所基于的坐标！
		//// !!!!!! 非常重要， 其计算方式是，相对于其自身的，而非相对于整个坐标系统的，如此原点始终应为(0,0),
		/// 我们这里是要求其绕自身旋转的，即要绕‘中心点’旋转，所以该点坐标如下：
		xorg = (mUWidth-0)/2f;
		yorg = (mUheight-0)/2f;		
		

		//texture = new Texture(Gdx.files.internal("anims/fan.png"));
		texture = t;
		mTextureRegion = new TextureRegion(texture, mUWidth*mUnitSize, mUheight*mUnitSize);  //这儿必须是基于像素的，取所需图像的像素尺寸

		//关于设置边界这儿
//		int rw = mTextureRegion.getRegionWidth();
//		int rh = mTextureRegion.getRegionHeight();
//		Gdx.app.log("", "rw="+rw+", rh="+rh);
		
		//设置边界应该是也是具有 unit坐标系统的, 
		//下面两句不是我们必须的
		setBounds(actorX,actorY,mUWidth,mUheight); //这个很重要，集成Actor后，如果不设置边界，可能无法被touch或点击
		addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				// TODO Auto-generated method stub
				Gdx.app.log("", "mfan down x="+x+", y="+y);
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		
		//添加 旋转Action
		RotateToAction action = new RotateToAction();
		action.setRotation(360f);
		action.setDuration(5f);
		action.setReverse(true);
		
		//!!!!!!!!!! 循环执行， 没有这个默认执行一次就停止
		RepeatAction epeatAction = Actions.repeat(RepeatAction.FOREVER,  action);  
		addAction(epeatAction);
		
		//addAction(action);
	}
	
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		//batch.begin();
		batch.draw(mTextureRegion, 
				actorX, actorY,   //左下角坐标  
				xorg, yorg,   //旋转基点
                mUWidth, mUheight, //这儿特别注意，为风扇的 unit尺寸，虽然取出来是按像素的(目的是要取完整)， 但显示的大小是受这儿控制的
                getScaleX(), getScaleY(),  getRotation());  		
		//batch.end();
	}
	
	
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		super.clear();
		
		//texture.dispose();
	}
}
