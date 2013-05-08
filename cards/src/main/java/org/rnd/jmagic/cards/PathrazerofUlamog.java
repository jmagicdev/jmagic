package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Pathrazer of Ulamog")
@Types({Type.CREATURE})
@SubTypes({SubType.ELDRAZI})
@ManaCost("(11)")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class PathrazerofUlamog extends Card
{
	public static final class CantBeBlockedExceptByThreeOrMore extends StaticAbility
	{
		public CantBeBlockedExceptByThreeOrMore(GameState state)
		{
			super(state, "Pathrazer of Ulamog can't be blocked except by three or more creatures.");

			SetGenerator countBlockingThis = Count.instance(Blocking.instance(This.instance()));
			SetGenerator blockingWithLessThanThree = Intersect.instance(Between.instance(1, 2), countBlockingThis);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(blockingWithLessThanThree));
			this.addEffectPart(part);
		}
	}

	public PathrazerofUlamog(GameState state)
	{
		super(state);

		this.setPower(9);
		this.setToughness(9);

		// Annihilator 3
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Annihilator.Final(state, 3));

		// Pathrazer of Ulamog can't be blocked except by three or more
		// creatures.
		this.addAbility(new CantBeBlockedExceptByThreeOrMore(state));
	}
}
