package com.inubot.script.sandstone.task;

import org.rspeer.commons.math.Range;
import org.rspeer.game.movement.Movement;
import org.rspeer.game.script.Task;
import org.rspeer.game.script.TaskDescriptor;

@TaskDescriptor(name = "Toggling run")
public class ToggleRunTask extends Task {

  private static final Range TOGGLE_AT = Range.of(10, 20);

  private int tolerance = TOGGLE_AT.random();

  @Override
  public boolean execute() {
    if (Movement.isRunEnabled() || Movement.getRunEnergy() < tolerance) {
      return false;
    }

    Movement.toggleRun(true);
    tolerance = TOGGLE_AT.random();
    sleep(2);
    return true;
  }
}
