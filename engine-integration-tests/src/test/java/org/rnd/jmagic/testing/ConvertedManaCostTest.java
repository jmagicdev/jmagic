package org.rnd.jmagic.testing;

import org.junit.*;

import static org.junit.Assert.*;

import org.rnd.jmagic.cards.*;
import org.rnd.jmagic.gameTypes.*;

public class ConvertedManaCostTest extends JUnitTest
{
	@Test
	public void monocoloredHybrid()
	{
		this.addDeck(BeseechtheQueen.class);
		this.addDeck();
		startGame(new Open());

		assertEquals(6, getLibrary(0).objects.get(0).getConvertedManaCost()[0]);
	}
}
