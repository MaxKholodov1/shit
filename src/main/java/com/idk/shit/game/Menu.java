package com.idk.shit.game;


import java.lang.ref.WeakReference;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.nanovg.NanoVGGL3;

import com.idk.shit.ui.button;
import com.idk.shit.utils.Colours;

public class Menu extends GameState {
    private long window;
    private StateManager stateManager;
    long vg = NanoVGGL3.nvgCreate(NanoVG.NVG_ALIGN_BASELINE);
    private button startButton = new button(0.f, 0.f, 1.5f, 1f, "start",Colours.GREEN, vg);



    public Menu(long window, StateManager stateManager) {
        super(window, stateManager);
        this.window = window; // Сохраняем окно
        this.stateManager = stateManager;
        initMenu();
    }
    private void initMenu(){
        WeakReference<Menu> weakMenu = new WeakReference<>(this);

        glfwSetKeyCallback(this.window, (wind, key, scancode, action, mods) -> {
            Menu menu = weakMenu.get(); // Получаем объект Game из WeakReference
            if (menu != null) { // Проверяем, что объект еще существует
                if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                    glfwSetWindowShouldClose(menu.window, true); // Закрытие окна при нажатии ESC
                }
                // if (key == GLFW_KEY_SPACE) {
                //     menu.spaced = true; // Обработка нажатия пробела
                // }
            }
        });
        startButton.draw();
    }
    @Override
    public void update(){
        startButton.update(window);
        if (startButton.isClicked()) {
            stateManager.setState(new Game(window, stateManager));
        }
    }
    @Override
    public void render(){
        startButton.draw();
    }
}


