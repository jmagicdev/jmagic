package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Garruk's Horde")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("5GG")
@ColorIdentity({Color.GREEN})
public final class GarruksHorde extends Card
{
	public static final class GarruksHordeAbility2 extends StaticAbility
	{
		public GarruksHordeAbility2(GameState state)
		{
			super(state, "You may cast the top card of your library if it's a creature card.");

			SetGenerator topCard = TopCards.instance(1, LibraryOf.instance(You.instance()));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.MAY_CAST_LOCATION);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, topCard);
			part.parameters.put(ContinuousEffectType.Parameter.PERMISSION, Identity.instance(new PlayPermission(You.instance())));
			this.addEffectPart(part);

			SetGenerator ifItsACreature = Intersect.instance(topCard, HasType.instance(Type.CREATURE));
			this.canApply = Both.instance(this.canApply, ifItsACreature);
		}
	}

	public GarruksHorde(GameState state)
	{
		super(state);

		this.setPower(7);
		this.setToughness(7);

		// Trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));

		// Play with the top card of your library revealed.
		this.addAbility(new org.rnd.jmagic.abilities.RevealTopOfLibrary(state));

		// You may cast the top card of your library if it's a creature card.
		// (Do this only any time you could cast that creature card. You still
		// pay the spell's costs.)
		this.addAbility(new GarruksHordeAbility2(state));
	}
}
