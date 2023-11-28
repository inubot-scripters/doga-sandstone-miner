package com.inubot.script.sandstone.task;

import org.rspeer.game.adapter.component.inventory.Inventory;
import org.rspeer.game.component.tdi.Magic;
import org.rspeer.game.component.tdi.Spell;
import org.rspeer.game.query.results.ItemQueryResults;
import org.rspeer.game.script.Task;
import org.rspeer.game.script.TaskDescriptor;

@TaskDescriptor(name = "Casting humidify")
public class HumidifyTask extends Task {

  @Override
  public boolean execute() {
    if (Magic.getBook() != Magic.Book.LUNAR || !Magic.canCast(Spell.Lunar.HUMIDIFY)) {
      return false;
    }

    ItemQueryResults waterskins = Inventory.backpack().query()
        .nameContains("Waterskin(")
        .filter(x -> !x.getName().contains("(0)"))
        .results();
    if (!waterskins.isEmpty()) {
      return false;
    }

    Magic.cast(Spell.Lunar.HUMIDIFY);
    sleep(2);
    return true;
  }
}
