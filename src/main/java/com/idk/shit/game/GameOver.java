package com.idk.shit.game;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;

import com.idk.shit.utils.InputManager;

public class GameOver extends GameState{
    // long vg = NanoVGGL3.nvgCreate(NanoVG.NVG_ALIGN_BASELINE);
    private int score;

    public GameOver(InputManager inputManager) {
        super(State._overgame_, inputManager);
        this.inputManager = inputManager;
        initGameOver();
    }
    protected boolean spaced=false;
    private void initGameOver(){
        String text ="Score: " + score;

    }
    @Override
    public State update(){
        if ( inputManager.isKeyPressed(GLFW_KEY_SPACE))  {
            this.curState = State._game_;
            inputManager.cleanup();
            return this.curState;
        }
        return this.curState;
    }
    @Override
    public void render(){
    }
}
