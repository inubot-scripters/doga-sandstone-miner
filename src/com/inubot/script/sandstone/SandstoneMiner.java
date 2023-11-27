package com.inubot.script.sandstone;

import com.inubot.script.sandstone.task.*;
import org.rspeer.commons.ArrayUtils;
import org.rspeer.commons.StopWatch;
import org.rspeer.event.Subscribe;
import org.rspeer.game.component.tdi.Skill;
import org.rspeer.game.event.ChatMessageEvent;
import org.rspeer.game.script.Task;
import org.rspeer.game.script.TaskScript;
import org.rspeer.game.script.meta.ScriptMeta;
import org.rspeer.game.script.meta.paint.PaintBinding;
import org.rspeer.game.script.meta.paint.PaintScheme;

@ScriptMeta(
    name = "Sandstone Miner",
    regions = -3,
    paint = PaintScheme.class,
    desc = "Mines sandstone, good for ironmen",
    developer = "Doga",
    version = 1.01
)
public class SandstoneMiner extends TaskScript {

  @PaintBinding("Runtime")
  private final StopWatch runtime = StopWatch.start();

  @PaintBinding("XP")
  private final Skill skill = Skill.MINING;

  @PaintBinding(value = "Mined", rate = true)
  private int mined = 0;

  @Override
  public Class<? extends Task>[] tasks() {
    return ArrayUtils.getTypeSafeArray(
        ToggleRunTask.class,
        MineTask.class,
        StoreTask.class
    );
  }

  @Subscribe
  public void notify(ChatMessageEvent event) {
    if (event.getContents().contains("some sandstone")) {
      mined++;
    }
  }
}
