package org.rnd.jmagic.engine;

/**
 * Extend this class when you want to write the back face of a double-faced
 * card. (Use {@link FlipBottomHalf} when you want to write the bottom half of a
 * flip card.)
 */
public abstract class AlternateCard extends Card
{
	public AlternateCard(GameState state)
	{
		super(state);
	}
}
