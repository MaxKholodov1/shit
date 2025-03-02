package com.idk.shit;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import org.lwjgl.nanovg.NanoVG;
import org.lwjgl.nanovg.NanoVGGL3;

public class Game extends GameState {
    private Deque<Object> blocks = new ArrayDeque<>();
    private Deque<Object> supposed_blocks = new ArrayDeque<>();
    private float block_height = 0.04f;
    private float block_width = 0.35f;
    long vg = NanoVGGL3.nvgCreate(NanoVG.NVG_ALIGN_BASELINE);


    private Player player = new Player(0.0f, 0.0f, 0.2f, 0.13f, 0.02f,Colours.BLACK );
    private Object block = new Object(0.0f, -0.5f, block_width, block_height, 0.0f, Colours.PURPLE);
    private button redButton = new button(-0.7f, 0.95f, 0.6f, 0.1f, "Menu", Colours.GREEN, vg);
    
    private  TextRenderer text_score = new TextRenderer(0.8f, 0.8f, "", Colours.BLACK, vg, 0.2f,0.4f );

    private boolean go_left = false;
    private boolean go_right = false;
    private float speed_player_x = 0.05f;
    private int screen_width = 500;
    private int screen_height = 800;

    private float speed_y = -0.0f;
    private float max_speed_y = 0.06f;
    private float accel_y = -0.003f;

    private int score = 0;

    private float max_height = -max_speed_y * max_speed_y / (2 * accel_y) - 0.05f;

    private long window;
    private StateManager stateManager;


    public Game(long window, StateManager stateManager) {
        super(window, stateManager); // Передаем window в родительский класс
        this.window = window; // Сохраняем окно
        this.stateManager = stateManager;
        initGame();
        String label = String.valueOf(score);
        this.text_score = new TextRenderer(0.8f, 0.8f, label, Colours.BLACK, vg, 0.2f, 0.4f);
    }

    private void initGame() {
        blocks.addLast(block);
        supposed_blocks.addLast(block);
        float x, y;
        float prev_height;
        for (int i = 0; i < 6; ++i) {
            prev_height = supposed_blocks.getLast().getTop();
            float a = prev_height + max_height / 5;
            float b = prev_height + max_height;
            float left = -1 + block_width / 2;
            float right = 1 - block_width / 2;
            rand randomizer = new rand();  // Создаём объект класса rand
            x = randomizer.rand_x(left, right); // Вызываем метод rand_x
            y = (float) (Math.random() * (b - a) + a);
            block = new Object(x, y, block_width, block_height, 0.0f, Colours.PURPLE);
            blocks.addLast(block);
            supposed_blocks.addLast(block);
        }

        // Устанавливаем обработчик клавиатуры
        glfwSetKeyCallback(this.window, (wind, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(this.window, true); // Закрытие окна при нажатии ESC
            if (key == GLFW_KEY_LEFT) {
                go_left = action != GLFW_RELEASE;
            }
            if (key == GLFW_KEY_RIGHT) {
                go_right = action != GLFW_RELEASE;
            }
        });
    }
    @Override
    public void update() {
        if (player.fall_down()==true){
            stateManager.setState(new GameOver(window, stateManager, score));
        }
        if (go_left) {
            player.update_object(-speed_player_x);
        } else if (go_right) {
            player.update_object(speed_player_x);
        } else {
            player.update_object(0);
        }

        for (Object block : supposed_blocks) {
            float block_speed_x = block.getSpeed_x();
            block.update_object(block_speed_x);
        }

        if (player.y > 0) {
            for (Object block : supposed_blocks) {
                block.y -= player.y;
            }
            player.y = 0;
        }

        for (Iterator<Object> iterator = blocks.iterator(); iterator.hasNext(); ) {
            Object block = iterator.next();
            if (block.y < -1) {
                score++;
                iterator.remove(); 
            }
        }

        int cnt = 0;
        for (Iterator<Object> iterator = supposed_blocks.iterator(); iterator.hasNext(); ) {
            Object block = iterator.next();
            if (block.y < -1) {
                cnt++;
                iterator.remove(); 
            }
        }

        float x;
        float y;
        float prev_height;
        for (int i = 0; i < cnt; ++i) {
            prev_height = supposed_blocks.getLast().getY();
            float a = prev_height + max_height / 3;
            float b = prev_height + max_height;
            float left = -1 + block_width / 2;
            float right = 1 - block_width / 2;
            rand randomizer = new rand();  
            x = randomizer.rand_x(left, right); 
            y = (float) (Math.random() * (b - a) + a);
            int res = randomizer.rand(new int[]{1, 2}, new int[]{score, 100});
            int speed_dir= randomizer.rand(new int[]{-1, 1}, new int[]{1, 1});
            if (res == 1) {
                block = new Object(x, y, block_width, block_height, 0.006f*speed_dir, Colours.CYAN);
            } else {
                block = new Object(x, y, block_width, block_height, 0.0f, Colours.PURPLE);
            }
            blocks.addLast(block);  
            supposed_blocks.addLast(block);
        }

        for (Object block : blocks) {
            block.collision(player);
        }

        redButton.update(window);
        if (redButton.isClicked()) {
            stateManager.setState(new Menu(window, stateManager));
        }
        String label = String.valueOf(score);
        this.text_score.update_text(label);
    }
    @Override
    public void render() {
        player.draw();
        for (Object block : blocks) {
            block.draw();
        }
        text_score.drawText();
        redButton.draw();
    }
}