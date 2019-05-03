package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.EnemyPool;
import ru.geekbrains.pool.ExplosionPool;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.Bullet;
import ru.geekbrains.sprite.Enemy;
import ru.geekbrains.sprite.GameOver;
import ru.geekbrains.sprite.MainShip;
import ru.geekbrains.sprite.Star;
import ru.geekbrains.utils.EnemyGenerator;

public class GameScreen extends BaseScreen {
    private enum State {
        PLAYING,
        PAUSE,
        GAME_OVER
    }

    private State state;

    private Texture bg;
    private Background background;
    private TextureAtlas atlas;
    private Star starList[];

    private MainShip mainShip;

    private BulletPool bulletPool;
    private EnemyPool enemyPool;
    private ExplosionPool explosionPool;

    private Music music;
    private Sound laserSound;
    private Sound bulletSound;
    private Sound explosionSound;

    private EnemyGenerator enemyGenerator;

    private GameOver gameOver;

    @Override
    public void show() {
        super.show();
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.setLooping(true);
        music.play();
        laserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
        bg = new Texture("textures/bg.png");
        background = new Background(new TextureRegion(bg));
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        starList = new Star[64];
        for (int i = 0; i < starList.length; i++) {
            starList[i] = new Star(atlas);
        }
        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlas, explosionSound);
        mainShip = new MainShip(atlas, bulletPool, explosionPool, laserSound);
        enemyPool = new EnemyPool(bulletPool, explosionPool, bulletSound, wordBounds, mainShip);
        enemyGenerator = new EnemyGenerator(atlas, enemyPool, wordBounds);
        gameOver = new GameOver(atlas);
        state = State.PLAYING;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollisions();
        freeAllDestroyedSprite();
        draw();
    }

    private void update(float delta) {
        for (Star star : starList) {
            star.update(delta);
        }
        explosionPool.updateActiveSprites(delta);
        if (state == State.PLAYING) {
            mainShip.update(delta);
            enemyGenerator.generate(delta);
            bulletPool.updateActiveSprites(delta);
            enemyPool.updateActiveSprites(delta);
        }
    }

    private void checkCollisions() {
        if (state != State.PLAYING) {
            return;
        }
        List<Enemy> enemyList = enemyPool.getActiveOdjects();
        for (Enemy enemy : enemyList) {
            if (enemy.isDestroyed()) {
                continue;
            }
            float minDist = enemy.getHalfWidth() + mainShip.getHalfWidth();
            if (enemy.pos.dst(mainShip.pos) < minDist) {
                enemy.destroy();
                mainShip.destroy();
                state = State.GAME_OVER;
                return;
            }
        }

        List<Bullet> bulletList = bulletPool.getActiveOdjects();

        for (Bullet bullet : bulletList) {
            if (bullet.isDestroyed()) {
                continue;
            }
            if (bullet.getOwner() == mainShip) {
                for (Enemy enemy : enemyList) {
                    if (enemy.isDestroyed()) {
                        continue;
                    }
                    if (enemy.isBulletCollision(bullet)) {
                        enemy.damage(bullet.getDamage());
                        bullet.destroy();
                        return;
                    }
                }
            } else {
                if (mainShip.isBulletCollision(bullet)) {
                    mainShip.damage(bullet.getDamage());
                    if (mainShip.isDestroyed()) {
                        state = State.GAME_OVER;
                    }
                    bullet.destroy();
                    return;
                }
            }
        }
    }

    private void freeAllDestroyedSprite() {
        bulletPool.freeAllDestroyedActiveSprites();
        enemyPool.freeAllDestroyedActiveSprites();
        explosionPool.freeAllDestroyedActiveSprites();
    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        for (Star star : starList) {
            star.draw(batch);
        }
        if (state == State.PLAYING) {
            mainShip.draw(batch);
            bulletPool.drawActiveSprites(batch);
            enemyPool.drawActiveSprites(batch);
        } else if (state == State.GAME_OVER) {
            gameOver.draw(batch);
        }
        explosionPool.drawActiveSprites(batch);
        batch.end();
    }

    @Override
    public void resize(Rect wordBounds) {
        super.resize(wordBounds);
        background.resize(wordBounds);
        for (Star star : starList) {
            star.resize(wordBounds);
        }
        if (state == State.PLAYING) {
            mainShip.resize(wordBounds);
        }
    }

    @Override
    public void pause() {
        super.pause();
        if (state == State.PLAYING) {
            state = State.PAUSE;
        }
    }

    @Override
    public void resume() {
        super.resume();
        if (state == State.PAUSE) {
            state = State.PLAYING;
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        enemyPool.dispose();
        explosionPool.dispose();
        music.dispose();
        laserSound.dispose();
        bulletSound.dispose();
        explosionSound.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (state == State.PLAYING) {
            mainShip.keyDown(keycode);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (state == State.PLAYING) {
            mainShip.keyUp(keycode);
        }
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (state == State.PLAYING) {
            mainShip.touchDown(touch, pointer);
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if (state == State.PLAYING) {
            mainShip.touchUp(touch, pointer);
        }
        return false;
    }
}
