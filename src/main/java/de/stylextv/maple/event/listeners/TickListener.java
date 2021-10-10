package de.stylextv.maple.event.listeners;

import de.stylextv.maple.context.GameContext;
import de.stylextv.maple.event.EventListener;
import de.stylextv.maple.event.events.ClientTickEvent;
import de.stylextv.maple.event.events.RenderTickEvent;
import de.stylextv.maple.input.controller.BreakController;
import de.stylextv.maple.input.controller.InputController;
import de.stylextv.maple.input.controller.PlaceController;
import de.stylextv.maple.input.controller.ViewController;
import de.stylextv.maple.pathing.movement.MovementExecutor;

public class TickListener implements EventListener {
	
	@Override
	public void onClientTick(ClientTickEvent event) {
		if(!GameContext.isIngame()) return;
		
		BreakController.onTick();
		PlaceController.onTick();
		
		InputController.onTick();
	}
	
	@Override
	public void onRenderTick(RenderTickEvent event) {
		if(!GameContext.isIngame()) return;
		
		MovementExecutor.onRenderTick();
		
		ViewController.onRenderTick();
	}
	
}