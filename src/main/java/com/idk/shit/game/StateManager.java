package com.idk.shit.game;

import com.idk.shit.utils.InputManager;

public class StateManager {
    public GameState currState;
    private InputManager inputManager;
    public StateManager(InputManager inputManager) {
        this.inputManager = inputManager;
    }
    public void setState(GameState newState){
        if(currState!=null){
            currState.cleanup(); // Освобождаем ресурсы предыдущего состояния
            currState = null;
            currState=newState;
            System.gc();
        }
        else{
            currState=newState;
            System.gc();
        }
    }

    public void update(){
        if(currState!=null){
            currState.update();
        }
    }
    public void render(){
        if(currState!=null){
            currState.render();
        }
    }
}
