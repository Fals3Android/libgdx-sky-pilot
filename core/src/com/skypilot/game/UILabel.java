package com.skypilot.game;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class UILabel {
    static BitmapFont font	= new BitmapFont();

    public static Label createLabel(String text, float xPosition, float yPosition, float scale) {
        Label label;
        Label.LabelStyle chooseLabelStyle;
        chooseLabelStyle = new Label.LabelStyle();
        chooseLabelStyle.font = font;
        label = new Label(text  , chooseLabelStyle);
        label.setScale(scale);
        label.setPosition(xPosition, yPosition);
        label.setFontScale(scale, scale);
        return label;
    }
}
