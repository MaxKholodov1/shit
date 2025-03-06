package com.idk.shit.game;


import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;

import com.idk.shit.ui.button;
import com.idk.shit.utils.Colours;
import com.idk.shit.utils.InputManager;

public class Menu extends GameState {
    private button startButton = new button(0.f, 0.f, 1.5f, 1f, "start",Colours.RED);



    public Menu(long window, InputManager inputManager) {
        super(window, State._menu_, inputManager);
        initMenu();
    }
    private void initMenu(){
        startButton.draw();
    }
    @Override
    public State update(){
        startButton.update(window);
        if (startButton.isClicked() || inputManager.isKeyPressed(GLFW_KEY_SPACE)) {
            this.curState = State._game_;
            inputManager.cleanup();
            return this.curState;
        }
        return this.curState;
    }
    @Override
    public void render(){
        startButton.draw();
    }
}