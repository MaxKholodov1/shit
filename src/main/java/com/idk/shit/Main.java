package com.idk.shit;

import org.lwjgl.Version;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.nanovg.NanoVGGL3;
import org.lwjgl.opengl.GL;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.system.MemoryUtil.NULL;


public class Main {
    private long window;
    private StateManager stateManager;

    public void run() {
        System.out.println("Hello, LWJGL " + Version.getVersion() + "!");

        init();
        loop();

        // Освобождение ресурсов окна и завершение GLFW
        // glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init() {
        // Устанавливаем обработчик ошибок GLFW, ошибки будут выводиться в System.err
        GLFWErrorCallback.createPrint(System.err).set();

        // Инициализация GLFW
        if (!glfwInit())
            throw new IllegalStateException("Не удалось инициализировать GLFW");

        // Настройки GLFW
        glfwDefaultWindowHints(); // Устанавливаем параметры окна по умолчанию
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // Окно сначала будет скрыто
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // Разрешаем изменение размера окна

        // Создание окна
        this.window = glfwCreateWindow(500, 800, "hello, world!", NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Ошибка создания окна GLFW");

        // Устанавливаем текущий контекст OpenGL
        glfwMakeContextCurrent(window);
        // Включаем вертикальную синхронизацию (V-Sync)
        glfwSwapInterval(1);
        // Отображаем окно
        glfwShowWindow(window);
        GL.createCapabilities();

        // Инициализация игры
        stateManager = new StateManager();
        stateManager.setState(new Game(window, stateManager));
    }

    private void loop() {
        // Загружаем функции OpenGL
        GL.createCapabilities();

        // Устанавливаем цвет очистки экрана (белый фон)
        glClearColor(1.0f, 1.0f, 1.0f, 0.0f);

        // Основной цикл рендеринга
        long vg = NanoVGGL3.nvgCreate(NanoVG.NVG_ALIGN_BASELINE);
        while (!glfwWindowShouldClose(window)) {
            // Очищаем буфер цвета и глубины
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            // Обновляем и рендерим игру
            stateManager.update();
            stateManager.render();

            // Меняем буферы экрана (двойная буферизация)
            glfwSwapBuffers(window);

            // Обрабатываем события (например, нажатия клавиш)
            glfwPollEvents();
        }
    }

    public static void main(String[] args) {
        new Main().run();
    }
}