package com.idk.shit.game;

import com.idk.shit.utils.InputManager;

public abstract  class GameState {
    public enum State{
        _menu_,
        _game_,
        _overgame_
    }
    protected State curState;
    protected InputManager inputManager;
    public GameState(long window, State state, InputManager input){
        this.window=window;
        this.curState = state;
        this.inputManager = input;
        this.window=window;
    }
    public State update(){
        return this.curState;
    }
    public abstract void render();
    public void cleanup() {

    }

    
}
