package com.guessmmafighter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

import java.io.IOException;
import java.util.Random;
import java.util.Vector;

/**
 * Created by Guseynov on 02.02.2016.
 */
public class Logic {

    int state;

    char letters[];
    boolean answerLetters[];
    boolean usedLetters[];
    Vector<Texture> lettersTexture;
    Vector<Texture> answerTexture;

    int usedLettersInd[];
    int qtUsedLetters;

    char answer[];
    String levelAnswer;
    Texture levelImg;

    int answerLength;

    int level;
    int currentMaxLevel;
    public static final int GAME_MAX_LEVEL = 100;

    int money;

    boolean delHelp;
    boolean helpUsedLetters[];

    String alphabit;
    Random random;

    GameSound sound;
    boolean error_sound;

    int OFFSET_LEFT_ANSWER;
    int OFFSET;
    int ANSWER_WIDTH;

    public static Preferences prefs;

    public boolean game_and;

    public static final int MAX_LEVEL = 120;

    public Logic() {

        state = 0;

        alphabit = new String("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        random   = new Random();

        sound = new GameSound();

        prefs = Gdx.app.getPreferences("mma");

        if (!prefs.contains("lvl")) {
            prefs.putInteger("lvl", 1);
            prefs.flush();
            currentMaxLevel = 1;
        }else {
            currentMaxLevel = prefs.getInteger("lvl");
        }

        level = currentMaxLevel;

        if (!prefs.contains("money")) {
            prefs.putInteger("money", 15);
            prefs.flush();
            money = 15;
        }else {
            money = prefs.getInteger("money");
        }

        if(currentMaxLevel == MAX_LEVEL){
            game_and = true;
        }

        createLevel();
    }

    public void setLevel(int tmp){
            sound.playSound(GameSound.VIEW_NEXT);
            delete();
            level += tmp;
            createLevel();
    }

    private void createLevel(){
        try {

            Element root = new XmlReader().parse(Gdx.files.internal("levels.xml"));
            Element xml_parse = root.getChildByName("lvl_" + level);

            levelAnswer = xml_parse.getText();
            levelImg = new Texture(Gdx.files.internal("fighters1/" + level + ".jpg"));


            answerLength = levelAnswer.length();
            setOffsetAnswer();

            answer = new char[answerLength];
            answerTexture = new Vector<Texture>();

            letters = new char[14];
            lettersTexture = new Vector<Texture>();

            answerLetters = new boolean[14];
            usedLetters = new boolean[14];
            usedLettersInd = new int[14];
            qtUsedLetters = 0;

            delHelp = false;
            helpUsedLetters = new boolean[answerLength];

            error_sound = false;

            setAnswer();
            setLetters();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean checkAnswer(){
        if(qtUsedLetters == answerLength) {
            for (int i = 0; i < levelAnswer.length(); i++) {
                if (answer[i] != levelAnswer.charAt(i)) {
                    if(!error_sound) {
                        sound.playSound(GameSound.INCORRECT_ANSWER);
                        error_sound = true;
                    }
                    return false;
                }
            }
            return true;
        }
        else {
            error_sound = false;
            return false;
        }
    }

    public void setAnswer(){
        for(int i = 0; i < answerLength; i++){
            answerTexture.add(new Texture(Gdx.files.internal("letters/letter.png")));
        }
    }

    private void setLetters() {

        for (int i = 0; i < 14; i++) {
            if (i < answerLength) {
                letters[i] = levelAnswer.charAt(i);
                answerLetters[i] = true;
            } else {
                letters[i] = alphabit.charAt(random.nextInt(26));
            }
        }

        for (int i = 1; i < 14; i++) {
            int j = random.nextInt(i);

            char temp = letters[i];
            letters[i] = letters[j];
            letters[j] = temp;

            boolean tmp = answerLetters[i];
            answerLetters[i] = answerLetters[j];
            answerLetters[j] = tmp;
        }

        for(int i = 0; i < 14; i++){
            Texture img = new Texture(Gdx.files.internal("letters/letter_" + letters[i] + ".png"));
            lettersTexture.add(img);
        }
    }

    private void setOffsetAnswer(){
        if(answerLength <= 3) {
            OFFSET_LEFT_ANSWER = 200;
            OFFSET = 15;
            ANSWER_WIDTH = 100;
        }else if(answerLength <= 4) {
            OFFSET_LEFT_ANSWER = 138;
            OFFSET = 15;
            ANSWER_WIDTH = 100;
        }else if(answerLength <= 5){
            OFFSET_LEFT_ANSWER = 105;
            OFFSET = 10;
            ANSWER_WIDTH = 95;
        }else if(answerLength <= 6){
            OFFSET_LEFT_ANSWER = 52;
            OFFSET = 10;
            ANSWER_WIDTH = 95;
        }else if(answerLength <= 7){
            OFFSET_LEFT_ANSWER = 42;
            OFFSET = 5;
            ANSWER_WIDTH = 85;
        }else if(answerLength <= 8){
            OFFSET_LEFT_ANSWER = 23;
            OFFSET =  5;
            ANSWER_WIDTH = 80;
        }else if(answerLength <= 9){
            OFFSET_LEFT_ANSWER = 8;
            OFFSET = 5;
            ANSWER_WIDTH = 74;
        }else if(answerLength <= 10){
            OFFSET_LEFT_ANSWER = 7;
            OFFSET = 5;
            ANSWER_WIDTH = 66;
        }else if(answerLength <= 11){
            OFFSET_LEFT_ANSWER = 6;
            OFFSET = 5;
            ANSWER_WIDTH = 60;
        }else {
            OFFSET_LEFT_ANSWER = 3;
            OFFSET = 5;
            ANSWER_WIDTH = 55;
        }
    }


    public void setLetter(int i){
        for(int l = 0; l < answer.length; l++) {
            if (answer[l] == 0 ) {
                answer[l] = letters[i];
                usedLettersInd[l] = i;
                answerTexture.get(l).dispose();
                answerTexture.set(l, new Texture(Gdx.files.internal("letters/letter_" + answer[l] + ".png")));
                qtUsedLetters++;
                break;
            }
        }
    }

    public void delLetter(int i){
        answer[i] = 0;
        answerTexture.get(i).dispose();
        answerTexture.set(i, new Texture(Gdx.files.internal("letters/letter.png")));
        usedLetters[usedLettersInd[i]] = false;
        qtUsedLetters--;
    }


    public void helpDeleteLetters(){
        for(int i =0; i < answer.length; i++){
            if(answer[i] != 0 && !helpUsedLetters[i]) {
                answer[i] = 0;
                answerTexture.get(i).dispose();
                answerTexture.set(i, new Texture(Gdx.files.internal("letters/letter.png")));
                usedLetters[usedLettersInd[i]] = false;
                qtUsedLetters--;
            }
        }
        for(int i = 0; i < 14; i++){
            usedLetters[i] = !answerLetters[i];
        }
    }

    public void helpAddLetter(){
        for(int l = 0; l < answer.length; l++) {
            if (answer[l] != levelAnswer.charAt(l)) {
                if(answer[l] != 0){
                    for(int i =0; i < answer.length; i++){
                        if(answer[i] != 0 && !helpUsedLetters[i] && answer[i] != levelAnswer.charAt(i)) {
                            answer[i] = 0;
                            answerTexture.get(i).dispose();
                            answerTexture.set(i, new Texture(Gdx.files.internal("letters/letter.png")));
                            usedLetters[usedLettersInd[i]] = false;
                            qtUsedLetters--;
                        }
                    }
                }
                answer[l] = levelAnswer.charAt(l);
                answerTexture.get(l).dispose();
                answerTexture.set(l, new Texture(Gdx.files.internal("letters/help/letter_" + answer[l] + ".png")));
                qtUsedLetters++;
                
                for(int i = 0; i < 14; i++){
                    if(answer[l] == letters[i] && !usedLetters[i]){
                        usedLetters[i] = true;
                        answerLetters[i] = false;
                        helpUsedLetters[l] = true;
                        break;
                    }
                }
                break;
            }
        }
    }


    public void delete(){
        levelImg.dispose();
        for(Texture t: answerTexture)
            t.dispose();
        for(Texture t: lettersTexture)
            t.dispose();
    }
}