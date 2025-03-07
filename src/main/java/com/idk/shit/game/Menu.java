package com.idk.shit.game;


import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;

import com.idk.shit.ui.button;
import com.idk.shit.utils.Colours;
import com.idk.shit.utils.InputManager;
import com.idk.shit.graphics.Texture;
import com.idk.shit.Levels.*;


public class Menu extends GameState {
    private button startButton = new button(0.f, 0.f, 1.5f, 1f, "start",Colours.RED);

    public Menu(long window, InputManager inputManager, StateManager stateManager, Texture blockTexture, Texture playerTexture) {
        super(window, inputManager, stateManager, blockTexture, playerTexture);
        initMenu();
    }
    private void initMenu(){
        startButton.draw();
    }
    @Override
    public void update(){
        startButton.update(window);
        if (startButton.isClicked() || inputManager.isKeyPressed(GLFW_KEY_SPACE)) {
            stateManager.setState(new Level2(window, inputManager, stateManager, blockTexture, playerTexture));
        }
    }
    @Override
    public void render(){
        startButton.draw();
    }
}