package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Vedalken Orrery")
@Types({Type.ARTIFACT})
@ManaCost("4")
@Printings({@Printings.Printed(ex = Expansion.FIFTH_DAWN, r = Rarity.RARE)})
@ColorIdentity({})
public final class VedalkenOrrery extends Card
{
	public static final class WhyDoesntThisJustGrantFlash extends StaticAbility
	{
		public WhyDoesntThisJustGrantFlash(GameState state)
		{
			super(state, "You may cast nonland cards as though they had flash.");

			SetGenerator youHavePriority = Intersect.instance(You.instance(), PlayerWithPriority.instance());

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.MAY_CAST_TIMING);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, RelativeComplement.instance(Cards.instance(), HasType.instance(Type.LAND)));
			part.parameters.put(ContinuousEffectType.Parameter.PERMISSION, Identity.instance(new PlayPermission(youHavePriority)));
			this.addEffectPart(part);
		}
	}

	public VedalkenOrrery(GameState state)
	{
		super(state);

		// You may cast nonland cards as though they had flash.
		this.addAbility(new WhyDoesntThisJustGrantFlash(state));
	}
}
