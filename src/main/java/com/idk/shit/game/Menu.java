package com.idk.shit.game;


import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.nanovg.NanoVGGL3;

import com.idk.shit.ui.button;
import com.idk.shit.utils.Colours;
import com.idk.shit.utils.InputManager;

public class Menu extends GameState {
    private long window;
    private StateManager stateManager;
    private InputManager inputManager;
    long vg = NanoVGGL3.nvgCreate(NanoVG.NVG_ALIGN_BASELINE);
    private button startButton = new button(0.f, 0.f, 1.5f, 1f, "start",Colours.GREEN, vg);



    public Menu(long window, StateManager stateManager, InputManager inputManager ) {
        super(window, stateManager);
        this.window = window; // Сохраняем окно
        this.stateManager = stateManager;
        this.inputManager=inputManager;
        initMenu();
    }
    private void initMenu(){
        inputManager.registerCallbacks(window); // Регистрируем обработчики ввода
        startButton.draw();
    }
    @Override
    public void update(){
        startButton.update(window);
        if (startButton.isClicked() ||inputManager.isKeyPressed(GLFW_KEY_SPACE) ) {
            inputManager.cleanup();
            stateManager.setState(new Game(window, stateManager, inputManager));
        }
        if (inputManager.isKeyPressed(GLFW_KEY_SPACE) ) {
            inputManager.cleanup();

            stateManager.setState(new Game(window, stateManager, inputManager));
        }
    }
    @Override
    public void render(){
        startButton.draw();
    }
}


