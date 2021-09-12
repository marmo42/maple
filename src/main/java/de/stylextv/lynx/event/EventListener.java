package de.stylextv.lynx.event;

import de.stylextv.lynx.event.events.ClientChatEvent;
import de.stylextv.lynx.event.events.ClientTickEvent;
import de.stylextv.lynx.event.events.RenderTickEvent;
import de.stylextv.lynx.event.events.RenderWorldEvent;

public interface EventListener {
	
	public default void onClientTick(ClientTickEvent event) {}
	
	public default void onRenderTick(RenderTickEvent event) {}
	
	public default void onWorldRender(RenderWorldEvent event) {}
	
	public default void onClientChat(ClientChatEvent event) {}
	
}
