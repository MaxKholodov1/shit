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
    public GameState(State state, InputManager input){
        this.curState = state;
        this.inputManager = input;
    }
    public State update(){
        return this.curState;
    }
    public abstract void render();
    public void cleanup() {

    }

    
}
