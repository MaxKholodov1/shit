package com.idk.shit;

import org.lwjgl.Version;
import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

import com.idk.shit.Levels.Level1;
import com.idk.shit.game.views.ViewManager;
import com.idk.shit.utils.InputManager;
import com.idk.shit.graphics.Texture;
import com.idk.shit.graphics.TextureCache;


public class Main {
    private long window;
    private ViewManager stateManager;
    protected InputManager inputManager; // Создаем InputManager
    protected  int screen_width=650;
    protected  int screen_height=1000;
    protected float RATIO;
    Texture background, playerTexture, blockTexture;

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
        this.window = glfwCreateWindow(screen_width, screen_height, "hello, world!", NULL, NULL);
        this.inputManager = new InputManager();
        inputManager.registerCallbacks(window);
        if (window == NULL)
            throw new RuntimeException("Ошибка создания окна GLFW");

        // Устанавливаем текущий контекст OpenGL
        glfwMakeContextCurrent(window);
        // Включаем вертикальную синхронизацию (V-Sync)
        glfwSwapInterval(1);
        // Отображаем окно
        glfwShowWindow(window);
        GL.createCapabilities();
        RATIO=(float)screen_width/(float)screen_height;
        glMatrixMode(GL_PROJECTION);
        glOrtho(-RATIO, RATIO, -1, 1, -1, 1);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        // shader = new Shader("vertex_shader.glsl", "fragment_shader.glsl");
        background = TextureCache.getTexture("src\\main\\resources\\textures\\photo_2025-03-06_21-31-49.png");
        playerTexture = TextureCache.getTexture("src\\main\\resources\\textures\\pngegg.png");
        blockTexture = TextureCache.getTexture("src\\main\\resources\\textures\\photo_2025-03-03_11-41-26.jpg.png");
        stateManager = new ViewManager();
        stateManager.setState(new Level1 (window,inputManager, stateManager));

    }

    private void loop() {
        
        // Загружаем функции OpenGL
        GL.createCapabilities();

        // Устанавливаем цвет очистки экрана (белый фон)
        glClearColor(1.0f, 1.0f, 1.0f, 0.0f);

        // Основной цикл рендеринга
        while (!glfwWindowShouldClose(window)) {
            // Очищаем буфер цвета и глубины
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            background.draw(0f, 0f, 2*RATIO, 2);
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