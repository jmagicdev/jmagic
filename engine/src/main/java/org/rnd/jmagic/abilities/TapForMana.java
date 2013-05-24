package org.rnd.jmagic.abilities;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

/**
 * Represents an ability of a permanent whose text is
 * "(T): Add [mana] to your mana pool."
 * 
 * This class is purposefully abstract despite it having no abstract methods
 * because it does not have a (GameState) constructor or an overridden create
 * method. To instantiate a TapForMana "directly", use an instance of
 * TapForMana.Final.
 */
public abstract class TapForMana extends ActivatedAbility
{
	/**
	 * Constructs an ability whose text (and name) is "(T): Add
	 * <code>mana</code> to your mana pool."
	 * 
	 * @param state The game state to construct the ability in.
	 * @param mana The mana to add. To represent "[color1] or [color2]" use a
	 * hybrid symbol; e.g., "(RG)".
	 */
	public TapForMana(GameState state, String mana)
	{
		super(state, "(T): Add " + translate(mana) + " to your mana pool.");
		this.costsTap = true;
		this.addEffect(addManaToYourManaPoolFromAbility(mana));
	}

	private static String translate(String from)
	{
		if(!from.startsWith("(") || !from.endsWith(")"))
			throw new UnsupportedOperationException("TapForMana's mana must be wrapped in ().");

		// multiple mana symbols
		if(from.contains(")("))
			return from;

		if(from.length() == 3)
			return from;

		if(from.length() == 4)
			return "(" + from.charAt(1) + ") or (" + from.charAt(2) + ")";

		if(from.equals("(WUBRG)"))
			return "one mana of any color";

		StringBuilder ret = new StringBuilder("(" + from.charAt(1) + "), ");
		for(int i = 2; i < from.length() - 2; i++)
			ret.append("(" + from.charAt(i) + "), ");
		ret.append("or (" + from.charAt(from.length() - 2) + ")");
		return ret.toString();
	}

	/**
	 * If TapForMana defined create, all its children would have to redefine it,
	 * so leave TapForMana abstract and use this class instead.
	 */
	public final static class Final extends org.rnd.jmagic.abilities.TapForMana
	{
		private String whichMana;

		public Final(GameState state, String mana)
		{
			super(state, mana);

			this.whichMana = mana;
		}

		@Override
		public Final create(Game game)
		{
			return new Final(game.physicalState, this.whichMana);
		}
	}

}
