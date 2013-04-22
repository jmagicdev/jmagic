package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Leyline of Anticipation")
@Types({Type.ENCHANTMENT})
@ManaCost("2UU")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class LeylineofAnticipation extends Card
{
	// Copied from Vedalken Orrery
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

	public LeylineofAnticipation(GameState state)
	{
		super(state);

		// If Leyline of Anticipation is in your opening hand, you may begin the
		// game with it on the battlefield.
		this.addAbility(new org.rnd.jmagic.abilities.LeylineAbility(state, "Leyline of Anticipation"));

		// You may cast nonland cards as though they had flash. (You may cast
		// them any time you could cast an instant.)
		this.addAbility(new WhyDoesntThisJustGrantFlash(state));
	}
}
