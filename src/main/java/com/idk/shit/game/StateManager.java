package com.idk.shit.game;
import com.idk.shit.graphics.Texture;

import com.idk.shit.utils.InputManager;

public class StateManager {
    private long window;
    private GameState.State state;
    public GameState screen;
    private InputManager inputManager;
    private Texture playerTexture;
    private Texture blockTexture;

    public StateManager(long window, InputManager inputManager,  Texture blockTexture, Texture playerTexture) {
        this.inputManager = inputManager;
        this.state = GameState.State._game_;
        this.blockTexture=blockTexture;
        this.playerTexture=playerTexture;
        this.screen = new Game(window, inputManager, blockTexture, playerTexture);
        this.window =window;
    }
    public void setState(GameState.State newState){
        if (this.state != newState) {
            if (newState == GameState.State._game_) {
                this.screen = new Game(window, inputManager, this.blockTexture, this.playerTexture);
            } else if (newState == GameState.State._menu_) {
                this.screen = new Menu(window, inputManager);
            } else if (newState == GameState.State._overgame_) {
                this.screen = new GameOver(window, inputManager);
            }
            System.gc();
            this.state = newState;
        }
    }

    public void update(){
        if(screen!=null){
            setState(screen.update());
        }
    }
    public void render(){
        if(screen!=null){
            screen.render();
        }
    }
}