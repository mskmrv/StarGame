package ru.geekbrains.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseScreen;
import sun.print.BackgroundLookupListener;

public class MenuScreen extends BaseScreen {

    private Texture bg;

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");

        batch.getProjectionMatrix().idt();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        batch.draw(bg, -0.5f, -0.5f, 1f, 1f);
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        return false;
    }
}
