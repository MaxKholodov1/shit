package com.idk.shit;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.nanovg.NanoVGGL3;

public class GameOver extends GameState{
    private long window;
    private StateManager stateManager;
    long vg = NanoVGGL3.nvgCreate(NanoVG.NVG_ALIGN_BASELINE);
    private button redButton = new button(-0.f, 0.f, 0.6f, 0.1f, "restart", new float[]{1.0f, 0.0f, 0.0f}, vg);


    public GameOver(long window, StateManager stateManager) {
        super(window, stateManager);
        this.window = window; // Сохраняем окно
        this.stateManager = stateManager;
        initGameOver();
    }
    private void initGameOver(){
        glfwSetKeyCallback(this.window, (wind, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(this.window, true); // Закрытие окна при нажатии ESC
            
        });
    }
    @Override
    public void update(){
        redButton.update(window);
        if (redButton.isClicked()) {
            stateManager.setState(new Game(window, stateManager));
        }
    }
    @Override
    public void render(){
        redButton.draw();
    }
}
