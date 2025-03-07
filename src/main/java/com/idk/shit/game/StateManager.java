package com.idk.shit.game;
import com.idk.shit.graphics.Texture;

import com.idk.shit.utils.InputManager;

public class StateManager {
    private long window;
    private GameState.State state;
    public GameState currState;
    private InputManager inputManager;
    private Texture playerTexture;
    private Texture blockTexture;

    public StateManager(long window, InputManager inputManager,  Texture blockTexture, Texture playerTexture) {
        this.inputManager = inputManager;
        this.state = GameState.State._game_;
        this.blockTexture=blockTexture;
        this.playerTexture=playerTexture;
        this.currState = new Menu(window, inputManager);
        this.window =window;
    }
    public void setState(GameState.State newState){
        if (this.state != newState) {
            if (newState == GameState.State._game_) {
                this.currState = new Game(window, inputManager, this.blockTexture, this.playerTexture);
            } else if (newState == GameState.State._menu_) {
                this.currState = new Menu(window, inputManager);
            } else if (newState == GameState.State._overgame_) {
                this.currState = new GameOver(window, inputManager);
            }
            System.gc();
            this.state = newState;
        }
    }

    public void update(){
        if(currState!=null){
            setState(currState.update());
        }
    }
    public void render(){
        if(currState!=null){
            currState.render();
        }
    }
}