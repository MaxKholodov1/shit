package com.idk.shit;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_RED;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL11.glVertex2f;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import org.lwjgl.stb.STBTTBakedChar;
import org.lwjgl.stb.STBTTFontinfo;
import org.lwjgl.stb.STBTruetype;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

public class TextRenderer {
    private int textureID;
    private int textureWidth, textureHeight;
    private float scale;

    public TextRenderer(String fontPath, String text, float width, float height) {
        // Загрузка шрифта
        ByteBuffer fontBuffer = loadFont(fontPath);

        // Вычисление масштаба для текста
        scale = calculateScale(fontBuffer, text, width, height);

        // Создание растрового изображения текста
        ByteBuffer bitmap = createBitmap(fontBuffer, text, scale);

        // Создание текстуры
        textureID = createTexture(bitmap, textureWidth, textureHeight);

        // Освобождаем память, выделенную для растрового изображения
        MemoryUtil.memFree(bitmap);
    }

    private ByteBuffer loadFont(String fontPath) {
        try {
            // Чтение файла шрифта в массив байт
            byte[] fontData = Files.readAllBytes(Paths.get(fontPath));
            // Выделение памяти и копирование данных в ByteBuffer
            ByteBuffer fontBuffer = MemoryUtil.memAlloc(fontData.length);
            fontBuffer.put(fontData).flip();
            return fontBuffer;
        } catch (IOException e) {
            throw new RuntimeException("Не удалось загрузить шрифт: " + fontPath, e);
        }
    }

    private float calculateScale(ByteBuffer fontBuffer, String text, float width, float height) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            // Инициализация шрифта
            STBTTFontinfo fontInfo = STBTTFontinfo.create();
            STBTruetype.stbtt_InitFont(fontInfo, fontBuffer);

            // Получаем метрики шрифта
            FloatBuffer scale = stack.floats(STBTruetype.stbtt_ScaleForPixelHeight(fontInfo, height));

            // Вычисляем ширину текста
            float textWidth = 0;
            IntBuffer advanceWidth = stack.mallocInt(1);
            IntBuffer leftSideBearing = stack.mallocInt(1);

            for (char c : text.toCharArray()) {
                STBTruetype.stbtt_GetCodepointHMetrics(fontInfo, c, advanceWidth, leftSideBearing);
                textWidth += advanceWidth.get(0) * scale.get(0);
            }

            // Масштабируем текст, чтобы он поместился в ширину
            return Math.min(scale.get(0), width / textWidth);
        }
    }

    private ByteBuffer createBitmap(ByteBuffer fontBuffer, String text, float scale) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            // Инициализация шрифта
            STBTTFontinfo fontInfo = STBTTFontinfo.create();
            STBTruetype.stbtt_InitFont(fontInfo, fontBuffer);

            // Вычисление размера растрового изображения
            IntBuffer ascent = stack.mallocInt(1);
            IntBuffer descent = stack.mallocInt(1);
            IntBuffer lineGap = stack.mallocInt(1);
            STBTruetype.stbtt_GetFontVMetrics(fontInfo, ascent, descent, lineGap);

            // Получаем значения из буферов
            int ascentValue = ascent.get(0);
            int descentValue = descent.get(0);
            int lineGapValue = lineGap.get(0);

            // Вычисляем высоту текстуры
            textureHeight = (int) ((ascentValue - descentValue + lineGapValue) * scale);
            textureWidth = 0;

            // Вычисление ширины текста
            IntBuffer advanceWidth = stack.mallocInt(1);
            IntBuffer leftSideBearing = stack.mallocInt(1);

            for (char c : text.toCharArray()) {
                STBTruetype.stbtt_GetCodepointHMetrics(fontInfo, c, advanceWidth, leftSideBearing);
                textureWidth += advanceWidth.get(0) * scale;
            }

            // Создание растрового изображения
            ByteBuffer bitmap = MemoryUtil.memAlloc(textureWidth * textureHeight);

            // Создание буфера для хранения информации о символах
            STBTTBakedChar.Buffer charData = STBTTBakedChar.malloc(text.length());

            // Вызов stbtt_BakeFontBitmap
            STBTruetype.stbtt_BakeFontBitmap(fontBuffer, scale, bitmap, textureWidth, textureHeight, 0, charData);

            return bitmap;
        }
    }

    private int createTexture(ByteBuffer bitmap, int width, int height) {
        int textureID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureID);

        // Загрузка растрового изображения в текстуру
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RED, width, height, 0, GL_RED, GL_UNSIGNED_BYTE, bitmap);
        glGenerateMipmap(GL_TEXTURE_2D);

        // Настройка параметров текстуры
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        return textureID;
    }

    public void render(float x, float y) {
        glEnable(GL_TEXTURE_2D);
        glBindTexture(GL_TEXTURE_2D, textureID);

        // Вычисление координат для центрирования текста
        float drawX = x - textureWidth / 2.0f;
        float drawY = y - textureHeight / 2.0f;

        // Отрисовка текстуры
        glBegin(GL_QUADS);
        glTexCoord2f(0, 0); glVertex2f(drawX, drawY);
        glTexCoord2f(1, 0); glVertex2f(drawX + textureWidth, drawY);
        glTexCoord2f(1, 1); glVertex2f(drawX + textureWidth, drawY + textureHeight);
        glTexCoord2f(0, 1); glVertex2f(drawX, drawY + textureHeight);
        glEnd();

        glDisable(GL_TEXTURE_2D);
    }

    public void cleanup() {
        glDeleteTextures(textureID);
    }
}