package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sphinx of Jwar Isle")
@Types({Type.CREATURE})
@SubTypes({SubType.SPHINX})
@ManaCost("4UU")
@ColorIdentity({Color.BLUE})
public final class SphinxofJwarIsle extends Card
{
	public static final class ShesALooker extends StaticAbility
	{
		public ShesALooker(GameState state)
		{
			super(state, "You may look at the top card of your library.");

			SetGenerator library = LibraryOf.instance(You.instance());
			SetGenerator topCardOfLibrary = TopCards.instance(1, library);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.LOOK);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, topCardOfLibrary);
			part.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
			this.addEffectPart(part);
		}
	}

	public SphinxofJwarIsle(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Flying, shroud
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Shroud(state));

		// You may look at the top card of your library. (You may do this at any
		// time.)
		this.addAbility(new ShesALooker(state));
	}
}
