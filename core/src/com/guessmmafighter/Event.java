package com.guessmmafighter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector3;
import com.sun.javafx.font.directwrite.RECT;

/**
 * Created by Guseynov on 02.02.2016.
 */
public class Event {

    Vector3 touchPos;
    Logic logic;

    private static final int OFFSET_LEFT   = 15;
    private static final int OFFSET_BOTTOM = 150;
    private static final int RECT_WITDH    = 90;
    private static final int OFFSET_BOTTOM_ANSWER = 410;

    public Event(Logic _logic, Vector3 _touchPos){
        logic    = _logic;
        touchPos = _touchPos;
    }

    public void eventMenu(){
        if(touchPos.x > 120 && touchPos.x < 580){
            if(touchPos.y > 370 && touchPos.y < 530){
                logic.state = 1;
                logic.sound.playSound(GameSound.VIEW_NEXT);
                return;
            }else if(touchPos.y > 180 && touchPos.y < 335){
                if(logic.sound.sound){
                    logic.sound.playSound(GameSound.ADD_LETTER);
                    logic.sound.sound = false;
                }else{
                    logic.sound.sound = true;
                    logic.sound.playSound(GameSound.ADD_LETTER);
                }
                return;
            }
        }
    }

    public void eventGame(){
        if(touchPos.x < 95 && touchPos.y >  1175){
            logic.state = 0;
            logic.sound.playSound(GameSound.VIEW_NEXT);
            return;
        }

        if(touchPos.y > OFFSET_BOTTOM && touchPos.y < OFFSET_BOTTOM + RECT_WITDH + 100){
            if(logic.qtUsedLetters < logic.answerLength)
                lettersEvent();
            return;
        }

        if(touchPos.y > OFFSET_BOTTOM_ANSWER && touchPos.y < OFFSET_BOTTOM_ANSWER + logic.ANSWER_WIDTH){
            answerEvent();
            return;
        }

        if(touchPos.x > 600 && touchPos.x < 705){
            if(touchPos.y > 970 && touchPos.y < 1070 && logic.qtUsedLetters < logic.answerLength){
                if(logic.money >= 5){
                    logic.helpAddLetter();
                    logic.money -= 5;
                    logic.sound.playSound(GameSound.ADD_LETTER);
                    logic.prefs.putInteger("money", logic.money);
                    logic.prefs.flush();
                }
                return;
            }
            if(touchPos.y > 830 && touchPos.y < 930 && !logic.delHelp){
                if(logic.money >= 10){
                    logic.helpDeleteLetters();
                    logic.money -= 10;
                    logic.delHelp = true;
                    logic.sound.playSound(GameSound.CLEAN_LETTER);
                    logic.prefs.putInteger("money", logic.money);
                    logic.prefs.flush();
                }
                return;
            }
        }

        if(touchPos.y > 625 && touchPos.y < 695){
            if(touchPos.x > 20 && touchPos.x < 115) {
                if(logic.level > 1){
                    logic.setLevel(-1);
                }
                return;
            }
            if(touchPos.x > 605 && touchPos.x < 700) {
                if(logic.level < logic.currentMaxLevel){
                    logic.setLevel(+1);
                }
                return;
            }
        }
    }

    private void lettersEvent() {
        int dy = 0;
        int x1, x2;
        int y1, y2;

        for(int i = 0, j = 0; i < 14; i++, j++){
            if( j == 7){
                dy = 100;
                j = 0;
            }
            if(!logic.usedLetters[i]){
                x1 = OFFSET_LEFT + j*(RECT_WITDH + 10);
                x2 = OFFSET_LEFT + j*(RECT_WITDH + 10) + RECT_WITDH;
                if(touchPos.x >= x1 && touchPos.x <= x2) {
                    y1 = OFFSET_BOTTOM + dy;
                    y2 = OFFSET_BOTTOM + RECT_WITDH + dy;
                    if (touchPos.y >= y1 && touchPos.y <= y2) {
                        logic.usedLetters[i] = true;
                        logic.setLetter(i);
                        logic.sound.playSound(GameSound.ADD_LETTER);
                        break;
                    }
                }
            }
        }
    }

    public void answerEvent(){
       int x1, x2;
        for(int i = 0; i < logic.answerLength; i++){
            x1 = logic.OFFSET_LEFT_ANSWER + i*(logic.ANSWER_WIDTH + logic.OFFSET);
            x2 = logic.OFFSET_LEFT_ANSWER + i*(logic.ANSWER_WIDTH + logic.OFFSET) + logic.ANSWER_WIDTH;
            if(touchPos.x >=  x1 && touchPos.x <= x2 && logic.answer[i] != 0 && !logic.helpUsedLetters[i]){
                logic.delLetter(i);
                logic.sound.playSound(GameSound.CLEAN_LETTER);
                break;
            }
        }
    }

    public void eventBonus(){
        if(touchPos.y > 150 && touchPos.y < 350) {
            if (logic.currentMaxLevel + 1 != logic.MAX_LEVEL + 1) {
                if (logic.level == logic.currentMaxLevel) {
                    logic.money += 2;
                    logic.currentMaxLevel++;
                    logic.prefs.putInteger("lvl", logic.currentMaxLevel);
                    logic.prefs.putInteger("money", logic.money);
                    logic.prefs.flush();
                }
                logic.state = 1;
                logic.setLevel(+1);
            } else{
                if(!logic.game_and) {
                    logic.money += 2;
                    logic.prefs.putInteger("money", logic.money);
                    logic.prefs.flush();
                }
                logic.state = 0;
                logic.setLevel(0);
                logic.game_and = true;
            }
        }
    }
}
