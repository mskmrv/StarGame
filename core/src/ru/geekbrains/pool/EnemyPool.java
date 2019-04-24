package ru.geekbrains.pool;

import com.badlogic.gdx.audio.Sound;

import ru.geekbrains.base.SpritesPool;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.Enemy;
import ru.geekbrains.sprite.MainShip;

public class EnemyPool extends SpritesPool<Enemy> {
    private Rect worldBounds;
    private BulletPool bulletPool;
    private Sound shootSound;
    private MainShip mainShip;

    public EnemyPool(BulletPool bulletPool, Sound shootSound, Rect worldBounds, MainShip mainShip) {
        this.bulletPool = bulletPool;
        this.shootSound = shootSound;
        this.worldBounds = worldBounds;
        this.mainShip = mainShip;
    }

    @Override
    protected Enemy newObjects() {
        return new Enemy(bulletPool, shootSound, worldBounds, mainShip);
    }
}
