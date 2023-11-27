package com.inubot.script.sandstone.data;

import org.rspeer.game.position.Position;

public enum Rock {

  FIRST(3166, 2913),
  SECOND(3167, 2913),
  THIRD(3164, 2914),
  FOURTH(3164, 2915);

  private final Position position;

  Rock(Position position) {
    this.position = position;
  }

  Rock(int x, int y) {
    this(new Position(x, y));
  }

  public Position getPosition() {
    return position;
  }
}
