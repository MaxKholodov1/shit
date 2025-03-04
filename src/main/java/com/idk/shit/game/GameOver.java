package com.idk.shit.game;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.nanovg.NanoVGGL3;

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
    protected boolean spaced=false;
    private void initGameOver(){
        glfwSetKeyCallback(this.window, (wind, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(this.window, true); // Закрытие окна при нажатии ESC
            if (key == GLFW_KEY_SPACE){
                spaced=true;
            }
        });
        String text ="Score: " + score;
        scoreText =  new TextRenderer(0f, 0.8f, text, Colours.MAGENTA, vg, 0.7f, 0.7f );

    }
    @Override
    public void update(){
        redButton.update(window);
        if (redButton.isClicked() || spaced==true) {
            stateManager.setState(new Game(window, stateManager));
        }
    }
    @Override
    public void render(){
        redButton.draw();
        scoreText.drawText();
    }
}
