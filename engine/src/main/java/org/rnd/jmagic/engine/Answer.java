package org.rnd.jmagic.engine;

/**
 * Represent possible responses the player may give when queried
 */
public enum Answer
{
	LAND, LOSE, NO, NONLAND, WIN, YES, ARTIFACT, WHITE, BLUE, BLACK, RED, GREEN;

	/**
	 * Get the possible values when choosing either artifact, or a color.
	 * 
	 * @return A set containing ARTIFACT and the colors.
	 */
	public static final java.util.Set<Answer> artifactOrAColorChoices()
	{
		return java.util.EnumSet.of(ARTIFACT, WHITE, BLUE, BLACK, RED, GREEN);
	}

	/**
	 * Get the possible values of a called coin flip.
	 * 
	 * @return A set containing WIN and LOSE
	 */
	public static final java.util.Set<Answer> calledCoinFlipChoices()
	{
		return java.util.EnumSet.of(WIN, LOSE);
	}

	/**
	 * Get the possible responses to a yes/no question
	 * 
	 * @return A set containing YES and NO
	 */
	public static final java.util.Set<Answer> mayChoices()
	{
		return java.util.EnumSet.of(YES, NO);
	}

	@Override
	public final String toString()
	{
		return org.rnd.util.CamelCase.enumValueToDisplay(this.name());
	}
}
