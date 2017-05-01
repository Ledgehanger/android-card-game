
package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game.ExtraGameScreens;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;

import java.util.List;

import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Game;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.graphics.IGraphics2D;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.input.Input;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.input.TouchEvent;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.AssetStore;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.ElapsedTime;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game.MainGameScreens.MainMenu;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.scaleScreenReso;
import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.world.GameScreen;


/**
 * Created by ariftahir on 24/04/2017.
 */


public class OptionsState extends GameScreen{

    private Rect backgroundRect, titleRect, soundBtnRect, volumeBtnRect, plusBtnRect, minusBtnRect, backBtnRect, boundBoolean;
    private Settings settings;
    AssetStore aStore = mGame.getAssetManager();
    MainMenu menu;

    public OptionsState(String name, Game newGame) {
        super("Options state", newGame);
        aStore.loadAndAddBitmap("backGround", "img/OptionsScreenImages/mmbg.jpg");
        aStore.loadAndAddBitmap("title", "img/OptionsScreenImages/Title.PNG");
        aStore.loadAndAddBitmap("sound1", "img/OptionsScreenImages/sound1.png");
        aStore.loadAndAddBitmap("volume", "img/OptionsScreenImages/volume.png");
        aStore.loadAndAddBitmap("plus", "img/OptionsScreenImages/plus.png");
        aStore.loadAndAddBitmap("minus", "img/OptionsScreenImages/minus.png");
        aStore.loadAndAddBitmap("back", "img/OptionsScreenImages/back.png");

        aStore.loadAndAddBitmap("on", "img/OptionsScreenImages/on.png");
        aStore.loadAndAddBitmap("off", "img/OptionsScreenImages/off.png");

        aStore.loadAndAddMusic("BGM", "music/DISC5_02.mp3");
    }

    @Override
    public void update(ElapsedTime elapsedTime) {
        Input input = mGame.getInput();
        List<TouchEvent> touchEvents = input.getTouchEvents();
        if (touchEvents.size() > 0) {
            TouchEvent touchEvent = touchEvents.get(0);

            if (soundBtnRect.contains((int) touchEvent.x, (int) touchEvent.y) && touchEvent.type == 0) {
               aStore.getMusic("BGM").stop();
                settings.soundEnabled = false;
            }
            if (plusBtnRect.contains((int) touchEvent.x, (int) touchEvent.y) && touchEvent.type == 0) {
                aStore.getMusic("BGM").setVolume(+1);
            }
            if (minusBtnRect.contains((int) touchEvent.x, (int) touchEvent.y) && touchEvent.type == 0) {
                aStore.getMusic("BGM").setVolume(-1);
            }

            if (backBtnRect.contains((int) touchEvent.x, (int) touchEvent.y) && touchEvent.type == 0) {
                aStore.getMusic("BGM").dispose();
                mGame.getScreenManager().removeScreen(this.getName());
                MainMenu menu = new MainMenu("", mGame);
                mGame.getScreenManager().addScreen(menu);
            }

        }
    }

    @Override
    public void draw(ElapsedTime elapsedTime, IGraphics2D graphics2D) {
            try{
                scaleScreenReso scale = new scaleScreenReso(graphics2D);

                Bitmap aBackground = aStore.getBitmap("backGround");
                Bitmap aTitle = aStore.getBitmap("title");
                Bitmap aSound = aStore.getBitmap("sound1");
                Bitmap aVolume = aStore.getBitmap("volume");
                Bitmap aPlus = aStore.getBitmap("plus");
                Bitmap aMinus = aStore.getBitmap("minus");
                Bitmap aBack = aStore.getBitmap("back");

                Bitmap aOn = aStore.getBitmap("on");
                Bitmap aOff = aStore.getBitmap("off");

                if(backgroundRect == null || titleRect == null || soundBtnRect == null || volumeBtnRect == null ||
                        plusBtnRect == null || minusBtnRect == null || backBtnRect == null || boundBoolean == null){

                    int bgLeft = 0;
                    int bgRight = graphics2D.getSurfaceWidth();
                    int bgTop = 0;
                    int bgBot = graphics2D.getSurfaceHeight();

                    backgroundRect = new Rect(bgLeft, bgTop, bgRight, bgBot);

                    int titleLeft = 200;
                    int titleRight = titleLeft + aTitle.getWidth();
                    int titletop = 60;
                    int titlebottom = 139;

                    titleRect = scale.scalarect(titleLeft, titletop, titleRight, titlebottom);

                    int soundLeft = 275;
                    int soundRight = soundLeft + aSound.getWidth();
                    int soundTop = 280;
                    int soundBottom = soundTop + aSound.getHeight();

                    soundBtnRect = scale.scalarect(soundLeft,soundTop,soundRight,soundBottom);

                    int volLeft = 275;
                    int volRight = volLeft + aVolume.getWidth();
                    int volTop = 415;
                    int volBottom= volTop + aVolume.getHeight();

                    volumeBtnRect = scale.scalarect(volLeft,volTop,volRight,volBottom);

                    int plusLeft = volRight + 40;
                    int plusRight = plusLeft + aPlus.getWidth();
                    int plusTop = volTop;
                    int plusBottom = plusTop + aPlus.getHeight();

                    plusBtnRect = scale.scalarect(plusLeft, plusTop, plusRight, plusBottom);

                    int minusLeft = plusRight + 30;
                    int minusRight = minusLeft + aMinus.getWidth();
                    int minusTop = volTop;
                    int minusBottom = minusTop + aMinus.getHeight();

                    minusBtnRect = scale.scalarect(minusLeft, minusTop, minusRight, minusBottom);

                    int backLeft = 10;
                    int backRight = backLeft + aBack.getWidth();
                    int backTop = 630;
                    int backBottom = backTop + aBack.getHeight();

                    backBtnRect = scale.scalarect(backLeft, backTop, backRight, backBottom);

                    int boolLeft = soundRight + 40;
                    int boolRight = boolLeft + aOn.getWidth();
                    int boolTop = soundTop;
                    int boolBottom = boolTop + aOn.getHeight();

                    boundBoolean = scale.scalarect(boolLeft, boolTop, boolRight, boolBottom);
                }

             if(settings.soundEnabled = true) {
                    aStore.getMusic("BGM").play();
                    aStore.getMusic("BGM").setVolume(1);
                    aStore.getMusic("BGM").setLopping(true);
                }

                graphics2D.clear(Color.rgb(255, 255, 255));
                graphics2D.drawBitmap(aBackground, null, backgroundRect, null);
                graphics2D.drawBitmap(aTitle, null, titleRect, null);
                graphics2D.drawBitmap(aSound, null, soundBtnRect, null);
                graphics2D.drawBitmap(aVolume, null, volumeBtnRect, null);
                graphics2D.drawBitmap(aPlus, null, plusBtnRect, null);
                graphics2D.drawBitmap(aMinus, null, minusBtnRect, null);
                graphics2D.drawBitmap(aBack, null, backBtnRect, null);

                if(settings.isSoundEnabled()){
                    graphics2D.drawBitmap(aOn, null, boundBoolean, null);
                } else{
                    graphics2D.drawBitmap(aOff, null, boundBoolean, null);
                }

        }catch(Exception e){
                e.printStackTrace();
            }

    }

}