package com.idk.shit.game;


import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.nanovg.NanoVGGL3;

import com.idk.shit.utils.InputManager;

public class Menu extends GameState {
    long vg = NanoVGGL3.nvgCreate(NanoVG.NVG_ALIGN_BASELINE);
    // private button startButton = new button(0.f, 0.f, 1.5f, 1f, "start",Colours.GREEN, vg);



    public Menu(long window, InputManager inputManager) {
        super(window, State._overgame_, inputManager);
        this.window = window; // Сохраняем окно
        this.inputManager = inputManager;
        initMenu();
    }
    private void initMenu(){
        // startButton.draw();
    }
    @Override
    public State update(){
        // startButton.update(window);
        if ( inputManager.isKeyPressed(GLFW_KEY_SPACE)) {
            this.curState = State._game_;
            inputManager.cleanup();
            return this.curState;
        }
        return this.curState;
    }
    @Override
    public void render(){
        // startButton.draw();
    }
}


