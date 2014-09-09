package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Angel's Herald")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.CLERIC})
@ManaCost("W")
@ColorIdentity({Color.WHITE})
public final class AngelsHerald extends org.rnd.jmagic.cardTemplates.ShardsHerald
{
	public AngelsHerald(GameState state)
	{
		super(state, Color.GREEN, Color.WHITE, Color.BLUE, "Empyrial Archangel");

		this.setPower(1);
		this.setToughness(1);

		// (2)(W), (T), Sacrifice a green creature, a white creature, and a blue
		// creature: Search your library for a card named Empyrial Archangel and
		// put it onto the battlefield. Then shuffle your library.
	}
}
