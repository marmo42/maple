package de.stylextv.maple.pathing.movement;

import de.stylextv.maple.context.GameContext;
import de.stylextv.maple.context.PlayerContext;
import de.stylextv.maple.input.InputAction;
import de.stylextv.maple.input.controller.InputController;
import de.stylextv.maple.pathing.calc.Path;
import de.stylextv.maple.pathing.calc.PathState;
import de.stylextv.maple.util.chat.ChatUtil;
import net.minecraft.util.math.BlockPos;

public class MovementExecutor {
	
	private static final int MAX_DISTANCE_TO_PATH = 9;
	
	private static Path path;
	
	public static void followPath(Path p) {
		stop();
		
		path = p;
	}
	
	public static void stop() {
		path = null;
	}
	
	public static void recalc() {
		path.clear();
	}
	
	public static void onRenderTick() {
		if(path == null) return;
		
		Movement m = path.getCurrentMovement();
		
		if(m == null) {
			
			if(path.isFinished()) {
				
				boolean atGoal = path.getState() == PathState.AT_GOAL;
				
				String s = atGoal ? "Destination reached." : "Can't get any closer to goal.";
				
				ChatUtil.send(s);
				
				stop();
			}
			
			return;
		}
		
		if(needsToRecalc()) {
			
			recalc();
			
			return;
		}
		
		m.updateHelpers();
		
		m.onRenderTick();
		
		double dt = GameContext.lastFrameDuration();
		
		m.tick(dt);
		
		if(PlayerContext.isInWater()) {
			
			InputController.setPressed(InputAction.JUMP, true);
			InputController.setPressed(InputAction.SNEAK, false);
		}
		
		MovementState state = m.getState();
		
		if(state == MovementState.FAILED) {
			
			recalc();
			
		} else if(state == MovementState.DONE) {
			
			path.next();
		}
	}
	
	private static boolean needsToRecalc() {
		Movement m = path.getCurrentMovement();
		
		if(m.ranOutOfTime()) return true;
		
		if(!PlayerContext.isFalling()) {
			
			BlockPos pos = PlayerContext.blockPosition();
			
			double dis = path.distanceSqr(pos);
			
			if(dis > MAX_DISTANCE_TO_PATH) return true;
		}
		
		return path.isImpossible();
	}
	
	public static boolean hasPath() {
		return path != null;
	}
	
	public static Path getPath() {
		return path;
	}
	
}