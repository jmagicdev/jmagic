package org.rnd.jmagic.testing;

import org.junit.*;
import static org.junit.Assert.*;
import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.engine.*;

public class MulliganTest extends JUnitTest
{
	@Test
	public void basics()
	{
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		addDeck(Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class, Plains.class);
		startGame(GameTypes.OPEN);

		respondWith(getPlayer(0));

		assertEquals(7, getHand(0).objects.size());
		assertEquals(7, getHand(1).objects.size());

		mulligan();

		assertEquals(7, getHand(0).objects.size());
		assertEquals(7, getHand(1).objects.size());

		mulligan();

		assertEquals(6, getHand(0).objects.size());
		assertEquals(6, getHand(1).objects.size());

		keep();

		assertEquals(6, getHand(0).objects.size());
		assertEquals(6, getHand(1).objects.size());

		mulligan();

		assertEquals(6, getHand(0).objects.size());
		assertEquals(5, getHand(1).objects.size());

		mulligan();

		assertEquals(6, getHand(0).objects.size());
		assertEquals(4, getHand(1).objects.size());

		mulligan();

		assertEquals(6, getHand(0).objects.size());
		assertEquals(3, getHand(1).objects.size());

		keep();

		// These passes make sure that we're in normal actions
		pass();
		pass();
	}
}
