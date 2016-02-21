package com.guessmmafighter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by Guseynov on 08.02.2016.
 */
public class GameSound {

    boolean sound;

    Sound viewNext;
    Sound correctAnswer;
    Sound incorrectAnswer;
    Sound addLetter;
    Sound cleanLetter;
    Sound soundEvent;

    public final static int  VIEW_NEXT        = 1;
    public final static int  CORRECT_ANSWER   = 2;
    public final static int  INCORRECT_ANSWER = 3;
    public final static int  ADD_LETTER       = 4;
    public final static int  CLEAN_LETTER     = 5;
    public final static int  SOUND_EVENT      = 6;


    public GameSound(){
        sound = true;
        viewNext        = Gdx.audio.newSound(Gdx.files.internal("sounds/view_next_sound.wav"));
        correctAnswer   = Gdx.audio.newSound(Gdx.files.internal("sounds/correct_answer_sound.mp3"));
        incorrectAnswer = Gdx.audio.newSound(Gdx.files.internal("sounds/incorrect_answer_sound.mp3"));
        addLetter       = Gdx.audio.newSound(Gdx.files.internal("sounds/add_letter_sound.wav"));
        cleanLetter     = Gdx.audio.newSound(Gdx.files.internal("sounds/clean_letter_sound.wav"));
        soundEvent      = Gdx.audio.newSound(Gdx.files.internal("sounds/sound_event_sound.wav"));
    }

    public void soundOnOff(){
        sound = !sound;
    }

    public void playSound(int playsound){
        if(sound){
            switch (playsound){
                case VIEW_NEXT        : viewNext.play();        break;
                case CORRECT_ANSWER   : correctAnswer.play();   break;
                case INCORRECT_ANSWER : incorrectAnswer.play(); break;
                case ADD_LETTER       : addLetter.play();       break;
                case CLEAN_LETTER     : cleanLetter.play();     break;
                case SOUND_EVENT      : soundEvent.play();      break;
            }
        }
    }

    public  void delete(){
        viewNext.dispose();
        correctAnswer.dispose();
        incorrectAnswer.dispose();
        addLetter.dispose();
        cleanLetter.dispose();
        soundEvent.dispose();
    }
}
