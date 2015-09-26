package ui.story.lemoon.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import ui.story.lemoon.MyGame;
import ui.story.lemoon.comunicate.BsuEvent;
import ui.story.lemoon.comunicate.MyMsg;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 800;
		config.height = 480;
//		config.width = 1000;
//		config.height = 600;
//		new LwjglApplication(new MainUI(new BsuEvent(){
//			@Override
//			public void notify(Object obj, String msg) {
//				//Toast.makeText(AndroidLauncher.this, "libgdx消息通知", Toast.LENGTH_SHORT).show();
//			}
//        }), config);
		
		
		new LwjglApplication(new MyGame(new BsuEvent() {
			@Override
			public void notify(MyMsg msg) {
				// TODO Auto-generated method stub
				switch(msg.what){
				case MyMsg.ITEM_GUOXUE:
					System.out.println("GUOXUE");
					break;
				case MyMsg.ITEM_CARTON:
					System.out.println("CARTON");
					break;
				case MyMsg.ITEM_LANGUAGE:
					System.out.println("LANGUAGE");
					break;
				case MyMsg.ITEM_LIFE:
					System.out.println("LIFE");
					break;
				case MyMsg.ITEM_LOCAL:
					System.out.println("LOCAL");
					break;
				case MyMsg.ITEM_MATHS:
					System.out.println("MATHS");
					break;
				case MyMsg.ITEM_MUSIC:
					System.out.println("MUSIC");
					break;
				case MyMsg.ITEM_PAINT:
					System.out.println("PAINT");
					break;
				case MyMsg.ITEM_SCIENCE:
					System.out.println("SCIENCE");
					break;
				case MyMsg.ITEM_SETTING:
					System.out.println("SETTING");
					break;
				}
			}
		}), config);
	}
}
