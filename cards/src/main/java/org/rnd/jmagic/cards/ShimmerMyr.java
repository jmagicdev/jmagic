package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Shimmer Myr")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.MYR})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.RARE)})
@ColorIdentity({})
public final class ShimmerMyr extends Card
{
	public static final class ShimmerMyrAbility1 extends StaticAbility
	{
		public ShimmerMyrAbility1(GameState state)
		{
			super(state, "You may cast artifact cards as though they had flash.");

			SetGenerator youHavePriority = Intersect.instance(You.instance(), PlayerWithPriority.instance());

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.MAY_CAST_TIMING);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, Intersect.instance(Cards.instance(), HasType.instance(Type.ARTIFACT)));
			part.parameters.put(ContinuousEffectType.Parameter.PERMISSION, Identity.instance(new PlayPermission(youHavePriority)));
			this.addEffectPart(part);
		}
	}

	public ShimmerMyr(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Flash
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flash(state));

		// You may cast artifact cards as though they had flash.
		this.addAbility(new ShimmerMyrAbility1(state));
	}
}
