package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.base.Sprite;

public class GameOver extends Sprite {
    private static final float HEIGHT = 0.05f;
    private static final float BOTTOM_MARGINE = 0.009f;

    public GameOver(TextureAtlas atlas) {
        super(atlas.findRegion("message_game_over"));
        setHeightProportion(HEIGHT);
        setBottom(BOTTOM_MARGINE);
    }
}
