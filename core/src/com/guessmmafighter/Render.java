package com.guessmmafighter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * Created by Guseynov on 02.02.2016.
 */
public class Render {

    SpriteBatch batch;

    Logic logic;

    Texture bgMenu;
    Texture bgGame;
    Texture bNext;
    Texture Next;
    Texture rect;
    Texture sound_off;

    BitmapFont font;

    Letters letters;

    public Render(SpriteBatch _batch, Logic _logic){

        batch = _batch;
        logic = _logic;

        bgMenu = new Texture(Gdx.files.internal("textures/menu.png"));
        bgGame = new Texture(Gdx.files.internal("textures/game.png"));

        bNext = new Texture(Gdx.files.internal("textures/bNext.png"));
        Next  = new Texture(Gdx.files.internal("textures/next.png"));

        rect  = new Texture(Gdx.files.internal("textures/rect.png"));

        sound_off = new Texture(Gdx.files.internal("textures/sound_off.png"));

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/OpenSans-Bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
        param.size  = Gdx.graphics.getHeight() / 19;
        param.color = Color.WHITE;
        font = generator.generateFont(param);
        generator.dispose();

        letters = new Letters(batch, logic);

    }

    public void drawMenu(){
        batch.draw(bgMenu, 0, 0, 720, 1280);
        if(!logic.sound.sound){
            batch.draw(sound_off, 176, 250);
        }
    }

    public void drawGame(){
        batch.draw(bgGame,  0, 0, 720, 1280 );
        batch.draw(logic.levelImg, 160, 650, 400, 400);
        font.draw(batch, "LEVEL " + logic.level, 250, 1130);
        font.draw(batch, "$ " + logic.money, 550, 1247);
        letters.drawLetters();
        letters.drawAnswer();

        if(logic.level == 1) {
            batch.draw(rect, 20, 610);
        }
        if(logic.level == logic.currentMaxLevel){
            batch.draw(rect, 605, 610);
        }
    }

    public void drawBonus(){
        batch.draw(bgGame,  0, 0, 720, 1280 );
        font.draw(batch, "LEVEL " + logic.level, 250, 1130);
        font.draw(batch, "$ " + logic.money, 550, 1247);
        if(logic.level == 1) {
            batch.draw(rect, 20, 610);
        }
        if(logic.level == logic.currentMaxLevel){
            batch.draw(rect, 605, 610);
        }
        if(logic.level == logic.currentMaxLevel && !logic.game_and) {
            batch.draw(bNext, 0, 0, 720, 1280);
        }else{
            batch.draw(Next, 0, 0, 720, 1280);
        }
        batch.draw(logic.levelImg, 160, 650, 400, 400);
        letters.drawAnswer();
    }

    public void delete(){
        bgMenu.dispose();
        bgGame.dispose();
        bNext.dispose();
        Next.dispose();
        rect.dispose();
        font.dispose();
    }

}
