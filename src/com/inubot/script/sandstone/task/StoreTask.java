package com.inubot.script.sandstone.task;

import org.rspeer.commons.Pair;
import org.rspeer.game.adapter.component.inventory.Inventory;
import org.rspeer.game.adapter.scene.Player;
import org.rspeer.game.adapter.scene.SceneObject;
import org.rspeer.game.component.Item;
import org.rspeer.game.scene.Players;
import org.rspeer.game.scene.SceneObjects;
import org.rspeer.game.script.Task;
import org.rspeer.game.script.TaskDescriptor;

@TaskDescriptor(name = "Storing")
public class StoreTask extends Task {

  @Override
  public boolean execute() {
    Player self = Players.self();
    if (self == null) {
      return false;
    }

    if (!Inventory.backpack().isFull()) {
      return false;
    }

    SceneObject grinder = SceneObjects.query().names("Grinder").results().nearest();
    return grinder != null && grinder.interact("Deposit");
  }

  private Pair<Item, Item> getTickManipulators() {
    Inventory inv = Inventory.backpack();
    if (inv.contains(x -> x.nameContains("Pestle").results())) {
      Item first = inv.query().names("Guam leaf", "Marrentill").results().first();
      Item second = inv.query().nameContains("Swamp tar").results().first();
      if (first != null && second != null) {
        return new Pair<>(first, second);
      }
    }

    Item first = inv.query().names("Knife").results().first();
    Item second = inv.query().names("Teak logs").results().first();
    if (first != null && second != null) {
      return new Pair<>(first, second);
    }

    return null;
  }
}
