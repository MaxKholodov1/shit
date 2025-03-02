package com.idk.shit;

import org.lwjgl.nanovg.NVGColor;
import org.lwjgl.nanovg.NanoVG;
import static org.lwjgl.nanovg.NanoVG.nvgBeginFrame;
import static org.lwjgl.nanovg.NanoVG.nvgEndFrame;
import static org.lwjgl.nanovg.NanoVG.nvgFillColor;
import static org.lwjgl.nanovg.NanoVG.nvgFontFace;
import static org.lwjgl.nanovg.NanoVG.nvgFontSize;
import static org.lwjgl.nanovg.NanoVG.nvgText;
public class TextRenderer {
    protected float screen_height=800;
    protected float screen_width=500;
    private float x;
    private float y;
    private String label;
    private float[] color; 
    private long vg; 


    public TextRenderer(float x, float y, String label,float[] color,  long vg ) {
        this.x = x;
        this.y = y;
        this.label = label;
        this.color = color;
        this.vg=vg;
    }

    public void drawText(String label, float x, float y) {
        nvgBeginFrame(vg, screen_width, screen_height, 1); // Начинаем фрейм
        nvgFontSize(vg, 24.0f); 
        String fontPath = "C:/Users/maksh/Desktop/python/shit/src/main/java/com/idk/shit/Roboto_Condensed-MediumItalic.ttf";
        int font = NanoVG.nvgCreateFont(vg, "roboto", fontPath);
        if (font == -1) {
            throw new RuntimeException("Не удалось загрузить шрифт: " + fontPath);
        }
        nvgFontFace(vg, "roboto");    

        NVGColor color1 = NVGColor.create();
        color1.r(0.0f);  // Красный
        color1.g(0.0f);  // Зеленый
        color1.b(0.0f);  // Синий
        color1.a(1.0f);  // Альфа (непрозрачность)
        nvgFillColor(vg, color1);
    
        nvgText(vg, x, y, label);
    
        nvgEndFrame(vg);
    }
}