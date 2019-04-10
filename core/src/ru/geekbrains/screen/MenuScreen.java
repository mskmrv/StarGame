package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseScreen;

public class MenuScreen extends BaseScreen {
    private Vector2 touch;
    private Vector2 pos;
    private Vector2 v;
    private Texture img;

    @Override
    public void show() {
        super.show();
        touch = new Vector2();
        pos = new Vector2();
        v = new Vector2(0.2f, 0.5f);
        img = new Texture("badlogic.jpg");
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        pos.add(v);
        batch.begin();
        batch.draw(img, pos.x, pos.y);
        batch.end();
        if (pos.y > Gdx.graphics.getHeight() - 256) {
            v.setZero();
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        img.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX, Gdx.graphics.getHeight() - screenY);
        System.out.println("touchDown touch.x = " + touch.x + " touch.y = " + touch.y);
        return false;
    }
}
