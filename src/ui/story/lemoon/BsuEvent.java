package ui.story.lemoon;

import ui.story.lemoon.msg.MyMsg;

public abstract class BsuEvent {
	public abstract void notify(MyMsg msg);
}