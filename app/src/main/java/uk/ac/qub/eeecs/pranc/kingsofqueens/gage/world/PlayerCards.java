package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game;

import uk.ac.qub.eeecs.gage.ai.SteeringBehaviours;
import uk.ac.qub.eeecs.gage.engine.ElapsedTime;
import uk.ac.qub.eeecs.gage.engine.input.Input;
import uk.ac.qub.eeecs.gage.util.Vector2;
import uk.ac.qub.eeecs.gage.world.GameScreen;
import uk.ac.qub.eeecs.gage.world.Sprite;

public class PlayerCards extends Sprite {

	private Vector2 screenCentre = new Vector2();
	private Vector2 playerTouchAcceleration = new Vector2();

	public PlayerCards(float startX, float startY, GameScreen gameScreen) {
		super(startX, startY, 50.0f, 50.0f, gameScreen.getGame()
				.getAssetManager().getBitmap("//CARD"), gameScreen);

		screenCentre.x = gameScreen.getGame().getScreenWidth() / 2;
		screenCentre.y = gameScreen.getGame().getScreenHeight() / 2;

		maxAcceleration = 300.0f;
		maxVelocity = 100.0f;
		maxAngularVelocity = 1440.0f;
		maxAngularAcceleration = 1440.0f;
	}

	public void update(ElapsedTime elapsedTime) {

		Input input = mGameScreen.getGame().getInput();

		if (input.existsTouch(0)) {
			playerTouchAcceleration.x = (input.getTouchX(0) - screenCentre.x)
					/ screenCentre.x;
			playerTouchAcceleration.y = (screenCentre.y - input.getTouchY(0))
					/ screenCentre.y;

			acceleration.x = playerTouchAcceleration.x * maxAcceleration;
			acceleration.y = playerTouchAcceleration.y * maxAcceleration;
		}

		angularAcceleration *= 0.95f;
		angularVelocity *= 0.75f;
		acceleration.multiply(0.75f);
		velocity.multiply(0.95f);

		super.update(elapsedTime);
	}
}
