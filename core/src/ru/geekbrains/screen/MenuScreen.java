package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseScreen;

public class MenuScreen extends BaseScreen {
    private static float V_LEN = 0.5f;
    private Vector2 touch;
    private Vector2 pos;
    private Vector2 v;
    private Vector2 buf;
    private Texture img;

    @Override
    public void show() {
        super.show();
        touch = new Vector2();
        pos = new Vector2();
        v = new Vector2(0.2f, 0.5f);
        buf = new Vector2();
        img = new Texture("badlogic.jpg");
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        buf.set(touch);
        // 2 touch.cpy().sub(pos).len() > 0.5f привдет к тому, что 60 раз в сек будет сооздаваться
        // новый объект (из-за вызова метода cpy()) == утечка памяти
        if (buf.sub(pos).len() > V_LEN) {
            pos.add(v);
        } else {
            pos.set(touch);
        }
        batch.begin();
        batch.draw(img, pos.x, pos.y);
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        img.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX, Gdx.graphics.getHeight() - screenY);
        /*
        1 Рассчитываем вектор скорости:
        находим вектор, который указывает из позиции картинки которая будет двигаться в ту точку,
        куда мы кликаем на экране - из вектора touch вычитаем векотр pos: touch.sub(pos)
        и сохраняем копию чтобы не менять вектор кнечной точки (touch): touch.cpy().sub(pos).
        Далее укорачиваем этот выктор, чтобы картинка нt сразу перескакивала из точки в точку, а
        плавно двигалась: setLength(0.5f)
         */
        v.set(touch.cpy().sub(pos)).setLength(V_LEN);
        System.out.println("touchDown touch.x = " + touch.x + " touch.y = " + touch.y);
        return false;
    }
}
