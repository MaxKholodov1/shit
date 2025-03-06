package com.idk.shit.game;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;


import com.idk.shit.ui.button;
import com.idk.shit.utils.Colours;
import com.idk.shit.utils.InputManager;

public class GameOver extends GameState{
    private int score;
    private button redButton = new button(0.f, 0.f, 1f, 0.5f, "TRY AGAIN!", Colours.GREEN);

    public GameOver(long window, InputManager inputManager) {
        super(window, State._overgame_, inputManager);
        initGameOver();
    }
    protected boolean spaced=false;
    private void initGameOver(){

    }
    @Override
    public State update(){
        redButton.update(window);
        if (redButton.isClicked() || inputManager.isKeyPressed(GLFW_KEY_SPACE))  {
            this.curState = State._game_;
            inputManager.cleanup();
            return this.curState;
        }
        return this.curState;
    }
    @Override
    public void render(){
        redButton.draw();
    }
}