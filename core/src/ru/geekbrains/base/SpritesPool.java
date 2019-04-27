package ru.geekbrains.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public abstract class SpritesPool<T extends Sprite> {
    protected final List<T> activeOdjects = new ArrayList<T>();
    protected final List<T> freeOdjects = new ArrayList<T>();

    protected abstract T newObject();

    public T obtain() {
        T object;
        if (freeOdjects.isEmpty()) {
            object = newObject();
        } else {
            object = freeOdjects.remove(freeOdjects.size() - 1);
        }
        activeOdjects.add(object);
        System.out.println(this.getClass().getName() + " active/free: " + activeOdjects.size() + ":" + freeOdjects.size());
        return object;
    }

    public void updateActiveSprites(float delta) {
        for (Sprite sprite : activeOdjects) {
            if (!sprite.isDestroyed()) {
                sprite.update(delta);
            }
        }
    }

    public void drawActiveSprites(SpriteBatch spriteBatch) {
        for (int i = 0; i < activeOdjects.size(); i++) {
            Sprite sprite = activeOdjects.get(i);
            if (!sprite.isDestroyed()) {
                sprite.draw(spriteBatch);
            }
        }
    }

    public void freeAllActiveSprites() {
        freeOdjects.addAll(activeOdjects);
        activeOdjects.clear();
    }

    public void freeAllDestroyedActiveSprites() {
        for (int i = 0; i < activeOdjects.size(); i++) {
            T sprite = activeOdjects.get(i);
            if (sprite.isDestroyed()) {
                free(sprite);
                i--;
                sprite.flushDestroy();
            }
        }
    }

    public void free(T object) {
        if (activeOdjects.remove(object)) {
            freeOdjects.add(object);
            System.out.println(this.getClass().getName() + " active/free: " + activeOdjects.size() + ":" + freeOdjects.size());
        }
    }

    public void dispose() {
        freeOdjects.clear();
        activeOdjects.clear();
    }

    public List<T> getActiveOdjects() {
        return activeOdjects;
    }
}
