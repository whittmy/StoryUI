package ui.story.lemoon.scr;

import ui.story.lemoon.MyGame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class LoadingScr implements Screen {
	Game game;
	
	public LoadingScr(Game g){
		this.game = g;
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		AssetManager mgr = MyGame.getManager();
		mgr.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		mgr.load("map/map3.tmx", TiledMap.class);
		
		//mgr.setLoader(Texture.class, new TextureLoader(resolver));
		mgr.load("anims/fan.png", Texture.class);
		mgr.load("anims/ripple.png", Texture.class);
		mgr.load("anims/flow.png", Texture.class);
		
		mgr.load("anims/min.png", Texture.class);
		mgr.load("anims/hour.png", Texture.class);		
		mgr.load("anims/second.png", Texture.class);		
		
		mgr.load("font/arial-15.png", Texture.class);
		
		//mgr
		mgr.load("font/arial-15.fnt", BitmapFont.class);
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if(MyGame.getManager().update()){
			//加载完毕
			Gdx.app.log("", "load finished!!");
			game.setScreen(new Main1Src());
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
		MyGame.getManager().dispose();
	}

}
