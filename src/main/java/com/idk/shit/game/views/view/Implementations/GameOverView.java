package com.idk.shit.game.views.view.Implementations;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;

import com.idk.shit.ui.Button;
import com.idk.shit.utils.Colours;
import com.idk.shit.utils.InputManager;
import com.idk.shit.game.state.State;
import com.idk.shit.game.views.view.ApplicationView;
import com.idk.shit.ui.TextRenderer;

public class GameOverView extends ApplicationView{
    private Button gameButton;

    public GameOverView(State state,long window, InputManager inputManager, long vg, TextRenderer textRenderer) {
        super(state, window, inputManager, vg, textRenderer);
        gameButton = new Button(0.f, 0.f, 1f, 0.5f, "TRY AGAIN!", Colours.BROWN, vg, textRenderer);
        initGameOver();
    }
    protected boolean spaced=false;
    private void initGameOver(){

    }
    @Override
    public void  update() throws Exception{
        gameButton.update(window);
        if (gameButton.isClicked() || inputManager.isKeyPressed(GLFW_KEY_SPACE))  {
            inputManager.cleanup();
            state.Play(2);
        }
    }
    @Override
    public void render(){
        gameButton.draw();
    }
}