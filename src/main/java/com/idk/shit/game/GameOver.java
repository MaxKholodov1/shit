package com.idk.shit.game;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.nanovg.NanoVGGL3;

import com.idk.shit.ui.TextRenderer;
import com.idk.shit.ui.button;
import com.idk.shit.utils.Colours;
import com.idk.shit.utils.InputManager;

public class GameOver extends GameState{
    long vg = NanoVGGL3.nvgCreate(NanoVG.NVG_ALIGN_BASELINE);
    private int score;
    private button redButton = new button(0.f, 0.f, 1f, 0.5f, "TRY AGAIN!", Colours.GREEN, vg);
    private TextRenderer scoreText; 

    public GameOver(long window, InputManager inputManager) {
        super(window, State._overgame_, inputManager);
        this.window = window; 
        this.inputManager = inputManager;
        initGameOver();
    }
    protected boolean spaced=false;
    private void initGameOver(){
        String text ="Score: " + score;
        scoreText =  new TextRenderer(0f, 0.8f, text, Colours.MAGENTA, vg, 0.7f, 0.7f );

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
        redButton.draw();
        scoreText.drawText();
    }
}
