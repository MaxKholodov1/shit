package com.idk.shit;

import java.nio.IntBuffer;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

import org.lwjgl.Version;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowPos;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import org.lwjgl.system.MemoryStack;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Main {
    Deque<Object> blocks = new ArrayDeque<>();
	Deque<Object> supposed_blocks = new ArrayDeque<>();
	protected float block_height=0.04f;
	protected float block_width=0.35f;

    private Player player = new Player(0.0f, 0.0f, 0.2f, 0.13f, 0.02f);
	private Object block =new Object (0.0f,-0.5f, block_width, block_height,0.0f  );
	button redButton = new button(-0.5f, 0.5f, 0.6f, 0.1f, "Red", new float[]{1.0f, 0.0f, 0.0f});

	protected boolean go_left=false;
	protected boolean go_right=false;
	protected float speed_player_x=0.05f;
	protected int screen_width=500;
	protected int screen_height=800;

	protected float speed_y=-0.0f;
    protected float max_speed_y=0.06f;
    protected float accel_y=-0.003f;

	protected int score=0;

	protected float max_height = -max_speed_y*max_speed_y/(2*accel_y)-0.05f;
	// Дескриптор окна
	public long window;

	public void run() {
		System.out.println("Hello, LWJGL " + Version.getVersion() + "!");
		System.out.println(max_height);

		init(); // Инициализация окна
		loop(); // Основной цикл отрисовки

		// Освобождение ресурсов окна и завершение GLFW
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}

	private void init() {
		blocks.addLast(block);  
		supposed_blocks.addLast(block);
		float x, y;
		float prev_height;
		for (int i=0;i<6;++i){
			prev_height=supposed_blocks.getLast().getTop();
			float a=prev_height +max_height/3;
			float b= prev_height+max_height;
			float left=-1+block_width/2;
			float right=1-block_width/2;
			rand randomizer = new rand();  // Создаём объект класса rand
        	x = randomizer.rand_x(left, right); // Вызываем метод rand_x
			y=(float) (Math.random() * (b-a) + a);
			block =new Object (x,y, block_width, block_height,0.0f  );
			blocks.addLast(block);  
			supposed_blocks.addLast(block);
		}
		


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
		if (window == NULL)
			throw new RuntimeException("Ошибка создания окна GLFW");

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

		// Центрируем окно на экране
		try (MemoryStack stack = stackPush()) {
			IntBuffer pWidth = stack.mallocInt(1);
			IntBuffer pHeight = stack.mallocInt(1);

			// Получаем размер окна
			glfwGetWindowSize(window, pWidth, pHeight);

			// Получаем разрешение главного монитора
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			// Устанавливаем позицию окна в центр экрана
			glfwSetWindowPos(
				this.window,
				(vidmode.width() - pWidth.get(0)) / 2,
				(vidmode.height() - pHeight.get(0)) / 2
			);
		} // Стек автоматически освобождается

		// Устанавливаем текущий контекст OpenGL
		glfwMakeContextCurrent(window);
		// Включаем вертикальную синхронизацию (V-Sync)
		glfwSwapInterval(1);
		// Отображаем окно
		glfwShowWindow(window);
	}
    private void update(){
		if (go_left==true){
			player.update_object(-speed_player_x);
		}else
		if (go_right==true){
			// go_right=false;
			player.update_object(speed_player_x);
		}else{
			player.update_object(0);

		}
		for (Object block : supposed_blocks) {
			float block_speed_x=block.getSpeed_x();
			block.update_object(block_speed_x);
		}
		if (player.y>0){
			for (Object block : supposed_blocks) {
				block.y-=player.y;
			}
			player.y=0;
		}
		for (Iterator<Object> iterator = blocks.iterator(); iterator.hasNext(); ) {
			Object block = iterator.next();
			if (block.y < -1) {
				score++;
				iterator.remove(); // Удаляем объект из коллекции
			}
		}
		int cnt=0;
		for (Iterator<Object> iterator = supposed_blocks.iterator(); iterator.hasNext(); ) {
			Object block = iterator.next();
			if (block.y < -1) {
				cnt++;
				iterator.remove(); // Удаляем объект из коллекции
			}
		}
		float x;
		float y;
		float prev_height;
		for (int i=0;i<cnt;++i){
			prev_height=supposed_blocks.getLast().getY();
			float a=prev_height +max_height/3;
			float b= prev_height+max_height;
			float left=-1+block_width/2;
			float right=1-block_width/2;
			rand randomizer = new rand();  // Создаём объект класса rand
        	x = randomizer.rand_x(left, right); // Вызываем метод rand_x
			y=(float) (Math.random() * (b-a) + a);
			int res= randomizer.rand(new int[]{1,2},new int[]{score, 100});
			if(res==1){
				block =new Object (x,y, block_width, block_height,0.004f  );

			}else{
				block =new Object (x,y, block_width, block_height,0.0f  );
			}
			blocks.addLast(block);  // где block — это уже объект типа Object
			supposed_blocks.addLast(block);
		}
		// block.collision(player);
		for (Object block : blocks) {
			block.collision(player);
		}
		redButton.update(window);
		if (redButton.isClicked()==true){
			System.out.println("clicked");
		}
		glfwPollEvents();
    }
    private void render(){
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // Очищаем буфер цвета и глубины

        player.draw();
		redButton.draw();
		for (Object block : blocks) {
			block.draw();
		}		
		
		glfwSwapBuffers(window); // Меняем буферы экрана (двойная буферизация)
    }
	private void loop() {
		// Загружаем функции OpenGL
		GL.createCapabilities();

		// Устанавливаем цвет очистки экрана (красный фон)
		glClearColor(1.0f, 1.0f, 1.0f, 0.0f);

		// Основной цикл рендеринга
		while (!glfwWindowShouldClose(window)) {


			// Обрабатываем события (например, нажатия клавиш)
            render();
            update();
		}
	}

	public static void main(String[] args) {
		new Main().run();
	}
}
