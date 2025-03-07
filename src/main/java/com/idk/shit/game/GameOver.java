package com.idk.shit.game;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;

import com.idk.shit.ui.button;
import com.idk.shit.utils.Colours;
import com.idk.shit.utils.InputManager;
import com.idk.shit.graphics.Texture;
import com.idk.shit.Levels.*;

public class GameOver extends GameState{
    private button gameButton = new button(0.f, 0.f, 1f, 0.5f, "TRY AGAIN!", Colours.GREEN);

    public GameOver(long window, InputManager inputManager, StateManager stateManager, Texture blockTexture, Texture playerTexture) {
        super(window, inputManager, stateManager, blockTexture, playerTexture);
        initGameOver();
    }
    protected boolean spaced=false;
    private void initGameOver(){

    }
    @Override
    public void  update(){
        gameButton.update(window);
        if (gameButton.isClicked() || inputManager.isKeyPressed(GLFW_KEY_SPACE))  {
            inputManager.cleanup();
            stateManager.setState(new Level2(window, inputManager, stateManager, blockTexture, playerTexture));

        }
    }
    @Override
    public void render(){
        gameButton.draw();
    }
}