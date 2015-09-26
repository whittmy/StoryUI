package ui.story.lemoon.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

import ui.story.lemoon.BsuEvent;
import ui.story.lemoon.nouse.nouse_MainUI;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(480, 320);
        }

        @Override
        public ApplicationListener getApplicationListener () {
                return new nouse_MainUI(new BsuEvent(){
        			@Override
        			public void notify(Object obj, String msg) {
        				//Toast.makeText(AndroidLauncher.this, "libgdx消息通知", Toast.LENGTH_SHORT).show();
        			}
                });
        }
}