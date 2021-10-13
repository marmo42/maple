package de.stylextv.maple.task.tasks;

import de.stylextv.maple.pathing.PathingCommand;
import de.stylextv.maple.pathing.PathingCommandType;
import de.stylextv.maple.pathing.PathingStatus;
import de.stylextv.maple.pathing.calc.goal.Goal;
import de.stylextv.maple.task.Task;
import de.stylextv.maple.util.chat.ChatUtil;

public class CustomGoalTask extends Task {
	
	private Goal goal;
	
	public CustomGoalTask(Goal goal) {
		this.goal = goal;
	}
	
	@Override
	public PathingCommand onTick(PathingStatus status) {
		Goal g = status.getGoal();
		
		if(!goal.equals(g)) {
			
			return new PathingCommand(PathingCommandType.PATH_TO_GOAL, goal);
		}
		
		if(status.isPathing()) return PathingCommand.DEFER;
		
		boolean atGoal = status.isAtGoal();
		
		String s = atGoal ? "Destination reached." : "Can't get any closer to goal.";
		
		ChatUtil.send(s);
		
		return super.onTick(status);
	}
	
	public Goal getGoal() {
		return goal;
	}
	
}
