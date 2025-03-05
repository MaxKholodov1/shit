package com.idk.shit.game;

import java.lang.ref.WeakReference;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.nanovg.NanoVGGL3;

import com.idk.shit.graphics.TextureCache;
import com.idk.shit.ui.TextRenderer;
import com.idk.shit.ui.button;
import com.idk.shit.utils.Colours;

public class GameOver extends GameState{
    private long window;
    private StateManager stateManager;
    long vg = NanoVGGL3.nvgCreate(NanoVG.NVG_ALIGN_BASELINE);
    private int score;
    private button redButton = new button(0.f, 0.f, 1f, 0.5f, "TRY AGAIN!", Colours.GREEN, vg);
    private TextRenderer scoreText; 

    public GameOver(long window, StateManager stateManager, int score) {
        super(window, stateManager);
        this.window = window; // Сохраняем окно
        this.stateManager = stateManager;
        this.score=score;
        initGameOver();
    }
    public boolean spaced=false;
    private void initGameOver(){
        glfwSetKeyCallback(this.window, null); // Удаление старого обработчика
        WeakReference<GameOver> weakGameOver = new WeakReference<>(this);
        glfwSetKeyCallback(this.window, (wind, key, scancode, action, mods) -> {
            GameOver gameover = weakGameOver.get(); // Получаем объектgameover из WeakReference
            if (gameover != null) { // Проверяем, что объект еще существует
                if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE || key == GLFW_KEY_SPACE) {
                    glfwSetWindowShouldClose(gameover.window, true); // Закрытие окна при нажатии ESC
                    System.out.println("НАЖАТ ЕБАНЫЙ ПРОБЕЛ");
                }
                if (key == GLFW_KEY_SPACE) {
                    gameover.spaced = true; // Обработка нажатия пробела
                    System.out.println("НАЖАТ ЕБАНЫЙ ПРОБЕЛ");
                }
            }
        });
        String text ="Score: " + score;
        scoreText =  new TextRenderer(0f, 0.8f, text, Colours.MAGENTA, vg, 0.7f, 0.7f );

    }
    @Override
    public void update(){
        redButton.update(window);
        if (redButton.isClicked() || spaced) {
            stateManager.setState(new Game(window, stateManager));
            cleanup();
            return;
        }
        if (spaced) {
            stateManager.setState(new Game(window, stateManager));
            cleanup();
            return;
        }
    }
    @Override
    public void render(){
        redButton.draw();
        scoreText.drawText();
    }
    @Override
    public void cleanup() {
        glfwSetKeyCallback(window, null); // Удаляем обработчик
        TextureCache.cleanup();
        redButton = null;
        if (vg != 0) {
            NanoVGGL3.nvgDelete(vg);
            vg = 0; // Обнуляем ссылку на контекст
        }
    }   

}
