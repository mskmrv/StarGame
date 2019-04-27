package ru.geekbrains.pool;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.base.SpritesPool;
import ru.geekbrains.sprite.Explosion;

public class ExplosionPool extends SpritesPool<Explosion> {
    private TextureAtlas atlas;

    public ExplosionPool(TextureAtlas atlas) {
        this.atlas = atlas;
    }

    @Override
    protected Explosion newObject() {
        return new Explosion(atlas);
    }
}
