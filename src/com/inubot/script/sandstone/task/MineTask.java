package com.inubot.script.sandstone.task;

import com.inubot.script.sandstone.data.Rock;
import org.rspeer.commons.Pair;
import org.rspeer.game.adapter.component.inventory.Inventory;
import org.rspeer.game.adapter.scene.Player;
import org.rspeer.game.adapter.scene.SceneObject;
import org.rspeer.game.component.Item;
import org.rspeer.game.effect.Direction;
import org.rspeer.game.movement.Movement;
import org.rspeer.game.scene.Players;
import org.rspeer.game.scene.SceneObjects;
import org.rspeer.game.script.Task;
import org.rspeer.game.script.TaskDescriptor;

@TaskDescriptor(name = "Mining")
public class MineTask extends Task {

  @Override
  public boolean execute() {
    Player self = Players.self();
    if (self == null) {
      return false;
    }

    if (Inventory.backpack().isFull()) {
      return false;
    }

    Pair<Item, Item> manipulators = getTickManipulators();
    if (manipulators == null && (self.isMoving() || self.isAnimating())) {
      return false;
    }

    for (Rock rock : Rock.values()) {
      SceneObject obj = SceneObjects.query().on(rock.getPosition()).names("Sandstone rocks").results().first();
      if (obj != null) {
        boolean manipulated = false;
        if (manipulators != null) {
          Inventory.backpack().use(manipulators.getLeft(), manipulators.getRight());
          manipulated = true;
        }

        if (rock == Rock.FIRST && !self.getPosition().equals(rock.getPosition().translate(Direction.NORTH))) {
          Movement.walkTowards(rock.getPosition().translate(Direction.NORTH));
          return true;
        }

        if (manipulated) {
          sleep(3);
        }

        return obj.interact("Mine");
      }
    }

    return false;
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
