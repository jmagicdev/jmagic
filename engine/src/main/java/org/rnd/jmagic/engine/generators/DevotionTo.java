package org.rnd.jmagic.engine.generators;

import org.rnd.jmagic.engine.*;

/**
 * Evaluates to a single number representing [your/the given player's] devotion
 * to the specified colors. This will only evaluate the devotion for one player!
 *
 * A player's devotion to [color] is the number of mana symbols that are (at
 * least) [color] among the mana costs of permanents they control.
 *
 * A player's devotion to [color X] or [color Y] is the total number of mana
 * symbols that are (at least) [color X] or [color Y] among those mana costs; in
 * other words, we don't double-count hybrid symbols.
 *
 * The rules don't (yet?) say how to determine devotion to three or more colors,
 * but we can logically extend this pattern. This generator just looks at each
 * mana symbol, and if it's one of the given colors, counts it.
 */
public class DevotionTo extends SetGenerator
{
	// These first two methods exist instead of a (Color... colors) method
	// because (1) I'm avoiding a warning about confusion between
	// Identity(Object...) and Identity(Collection) and (2) it's probably faster
	// than introducing another varargs layer.

	/**
	 * "Your" devotion to [color]
	 */
	public static DevotionTo instance(Color color)
	{
		return new DevotionTo(Identity.instance(color), You.instance());
	}

	/**
	 * "Your" devotion to [x] and [y]
	 */
	public static DevotionTo instance(Color x, Color y)
	{
		return new DevotionTo(Identity.instance(x, y), You.instance());
	}

	/**
	 * "Your" devotion to [colors]
	 */
	public static DevotionTo instance(SetGenerator colors)
	{
		return new DevotionTo(colors, You.instance());
	}

	/**
	 * [player]'s devotion to [colors]
	 */
	public static DevotionTo instance(SetGenerator colors, SetGenerator player)
	{
		return new DevotionTo(colors, player);
	}

	private final SetGenerator colors;
	private final SetGenerator player;

	private DevotionTo(SetGenerator colors, SetGenerator player)
	{
		this.colors = colors;
		this.player = player;
	}

	@Override
	public Set evaluate(GameState state, Identified thisObject)
	{
		int devotion = 0;
		Player player = this.player.evaluate(state, thisObject).getOne(Player.class);
		java.util.Set<Color> colors = java.util.EnumSet.copyOf(this.colors.evaluate(state, thisObject).getAll(Color.class));
		for(GameObject permanent: state.battlefield().objects)
		{
			if(!permanent.getController(state).equals(player))
				continue;

			for(ManaPool pool: permanent.getManaCost())
				if(pool != null)
					for(ManaSymbol mana: pool)
						if(!java.util.Collections.disjoint(colors, mana.colors))
							devotion++;
		}
		return new Set(devotion);
	}
}
