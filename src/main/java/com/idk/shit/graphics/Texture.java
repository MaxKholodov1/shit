package com.idk.shit.graphics;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;
import org.lwjgl.stb.STBImage;

public class Texture {
    private int textureID;
    
    public Texture(String path) {
        textureID = loadTexture(path);
    }

    public int loadTexture(String path) {
        // Буферы для ширины, высоты и количества каналов изображения
        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);
        
        // Флип по вертикали, так как OpenGL читает текстуры снизу вверх
        STBImage.stbi_set_flip_vertically_on_load(true);
        
        // Загружаем изображение в буфер
        ByteBuffer image = STBImage.stbi_load(path, width, height, channels, 4);
        if (image == null) {
            throw new RuntimeException("Не удалось загрузить текстуру: " + path);
        }

        // Создаем и настраиваем текстуру
        int id = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
        
        // Устанавливаем параметры текстуры (повторение и фильтрация)
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);

        // Загружаем данные изображения в текстуру
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width.get(), height.get(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, image);
        GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);

        // Освобождаем память
        STBImage.stbi_image_free(image);
        
        return id;
    }

    public void bind() {
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
    }

    public void unbind() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
    }

    public void cleanup() {
        GL11.glDeleteTextures(textureID);
    }
    public int getTextureID() {
        return textureID;
    }
    public void draw(float x,float  y, float width,float height ){
        // Включаем текстурирование и привязываем текстуру
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);

        // Включаем поддержку прозрачности
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.1f);

        // Устанавливаем белый цвет (чтобы текстура отрисовывалась правильно)
        GL11.glColor4f(1, 1, 1, 1);

        // Начинаем рисовать квадрат с текстурой
        glBegin(GL11.GL_QUADS);
        {
            GL11.glTexCoord2f(0, 0); glVertex2f(x - width / 2, y - height / 2);
            GL11.glTexCoord2f(1, 0); glVertex2f(x + width / 2, y - height / 2);
            GL11.glTexCoord2f(1, 1); glVertex2f(x + width / 2, y + height / 2);
            GL11.glTexCoord2f(0, 1); glVertex2f(x - width / 2, y + height / 2);
        }
        glEnd();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA_TEST);

    }
}