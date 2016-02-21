package com.guessmmafighter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Guseynov on 02.02.2016.
 */
public class Letters {

    SpriteBatch batch;
    Logic logic;

    private static final int OFFSET_LEFT   = 15;
    private static final int OFFSET_BOTTOM = 150;
    private static final int RECT_WITDH    = 90;
    private static final int OFFSET_BOTTOM_ANSWER = 410;

    public Letters(SpriteBatch _batch, Logic _logic){
        batch = _batch;
        logic = _logic;
    }

    public void drawLetters(){

        int j = 0;
        int x, y, dy = 0;

        for(int i = 0; i < 14; i++, j++){
            if( j == 7){
                dy = 100;
                j = 0;
            }
            if(!logic.usedLetters[i]) {
                x = OFFSET_LEFT + j * (RECT_WITDH + 10);
                y = OFFSET_BOTTOM + dy;
                batch.draw(logic.lettersTexture.elementAt(i), x, y, RECT_WITDH, RECT_WITDH);
            }
        }
    }

    public void drawAnswer(){
        int x, y;
        for(int i = 0; i < logic.answerLength; i++){
            x = logic.OFFSET_LEFT_ANSWER + i*(logic.ANSWER_WIDTH + logic.OFFSET);
            y = OFFSET_BOTTOM_ANSWER + 5;
            batch.draw(logic.answerTexture.elementAt(i), x, y, logic.ANSWER_WIDTH, logic.ANSWER_WIDTH);
        }
    }
}
