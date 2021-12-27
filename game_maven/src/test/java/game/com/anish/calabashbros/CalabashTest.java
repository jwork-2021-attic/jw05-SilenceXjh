package game.com.anish.calabashbros;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;

import org.junit.Test;

public class CalabashTest {

	private Calabash bro = new Calabash(new Color(204, 0, 0), new World());

	@Test
	public void testAddScore() {
		assert(bro.getScore() == 0);
		bro.addScore(20);
		assert(bro.getScore() == 20);
	}

	@Test
	public void testGetHurt() {

	}

	@Test
	public void testGetLevel() {
		assert(bro.getLevel() == 0);
	}

	@Test
	public void testIsDead() {
		assertFalse(bro.isDead());
		bro.getHurt();
		bro.getHurt();
		bro.getHurt();
		assertTrue(bro.isDead());
	}

}
