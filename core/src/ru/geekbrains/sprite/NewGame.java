package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.base.ScaledTouchUpButton;
import ru.geekbrains.screen.GameScreen;

public class NewGame extends ScaledTouchUpButton {
    private static final float HEIGHT = 0.05f;
    private static final float TOP = -0.012f;
    private GameScreen screen;

    public NewGame(TextureAtlas atlas, GameScreen screen) {
        super(atlas.findRegion("button_new_game"));
        this.screen = screen;
        setHeightProportion(HEIGHT);
        setTop(TOP);
    }

    @Override
    protected void action() {
        screen.reset();
    }
}
