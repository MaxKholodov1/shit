package com.idk.shit.game.views.view.Implementations;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;

import com.idk.shit.ui.Button;
import com.idk.shit.utils.Colours;
import com.idk.shit.utils.InputManager;
import com.idk.shit.game.state.State;
import com.idk.shit.game.views.view.ApplicationView;
import com.idk.shit.ui.TextRenderer;
import com.idk.shit.utils.ScoreManager;

public class GameOverView extends ApplicationView{
    private Button gameButton;
    protected int score;
    protected int level;
    public GameOverView(State state,long window, InputManager inputManager, long vg, TextRenderer textRenderer, ScoreManager scoreManager, int score, int level) {
        super(state, window, inputManager, vg, textRenderer, scoreManager);
        this.score = score;
        this.level=level;
        gameButton = new Button(0.f, 0.f, 1f, 0.5f, "TRY AGAIN!", Colours.BROWN, vg, textRenderer);
        initGameOver();
    }
    private void initGameOver(){
        scoreManager.updateScore(level, score); // Обновит рекорд для уровня 1, если 600 больше текущего
        System.out.println(scoreManager.getHighScore(level));
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
        String scoreString = String.valueOf(score);
        textRenderer.drawText(0f, 0.9f, "your score:", Colours.BLACK, vg, 0.5f, 0.5f);
        textRenderer.drawText(-0.005f, 0.8f, scoreString, Colours.BLACK, vg, 0.4f, 0.4f);
    }
}