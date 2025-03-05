package com.idk.shit.game;

import com.idk.shit.utils.InputManager;

public class StateManager {
    long window;
    private GameState.State state;
    public GameState screen;
    private InputManager inputManager;
    public StateManager(long window, InputManager inputManager) {
        this.inputManager = inputManager;
        this.state = GameState.State._game_;
        this.screen = new Game(window, inputManager);
        this.window =window;
    }
    public void setState(GameState.State newState){
        if (this.state != newState) {
            if (newState == GameState.State._game_) {
                this.screen = new Game(window, inputManager);
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
