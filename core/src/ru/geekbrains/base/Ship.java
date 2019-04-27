package ru.geekbrains.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.sprite.Bullet;

public class Ship extends Sprite {
    protected Sound shootSound;
    protected Rect worldBounds;
    protected Vector2 v;
    protected Vector2 v0;

    protected BulletPool bulletPool;
    protected TextureRegion bulletRegion;
    protected float bulletHeight;
    protected Vector2 bulletV;
    protected int damage;
    protected int hp;

    protected float reloadInterval;
    protected float reloadTimer;

    protected float damgeAnimateInterval = 0.01f;
    protected float damgeAnimateTimer = damgeAnimateInterval;

    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
        this.v = new Vector2();
        this.v0 = new Vector2();
        this.bulletV = new Vector2();
    }

    public Ship() {
        this.v = new Vector2();
        this.v0 = new Vector2();
        this.bulletV = new Vector2();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(v, delta);
        damgeAnimateTimer += delta;
        if (damgeAnimateTimer >= damgeAnimateInterval) {
            frame = 0;
        }
    }

    public void shoot() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, pos, bulletV, bulletHeight, worldBounds, damage);
        shootSound.play();
    }

    public void damage(int damage) {
        frame = 1;
        damgeAnimateTimer = 0f;
        hp -= damage;
        if (hp <= 0) {
            destroy();
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        hp = 0;
    }
}
