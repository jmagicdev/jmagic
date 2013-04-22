package org.rnd.jmagic.abilities.keywords;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

import static org.rnd.jmagic.Convenience.*;

/**
 * 702.99. Extort
 * 
 * 702.99a Extort is a triggered ability. "Extort" means "Whenever you cast a
 * spell, you may pay {W/B}. If you do, each opponent loses 1 life and you gain
 * life equal to the total life lost this way."
 * 
 * 702.99b If a permanent has multiple instances if extort, each triggers
 * separately.
 */
public class Extort extends Keyword
{
	public Extort(GameState state)
	{
		super(state, "Extort");
	}

	@Override
	public java.util.List<NonStaticAbility> createNonStaticAbilities()
	{
		java.util.List<NonStaticAbility> ret = new java.util.LinkedList<NonStaticAbility>();
		ret.add(new ExtortAbility(this.state));
		return ret;
	}

	public static final class ExtortAbility extends EventTriggeredAbility
	{
		public ExtortAbility(GameState state)
		{
			super(state, "Whenever you cast a spell, you may pay (WB). If you do, each opponent loses 1 life and you gain life equal to the total life lost this way.");

			this.addPattern(whenYouCastASpell());

			// Each opponent loses 3 life.
			EventFactory loseLife = loseLife(OpponentsOf.instance(You.instance()), 1, "Each opponent loses 1 life");

			// You gain life equal to the life lost this way.
			SetGenerator amount = EffectResult.instance(loseLife);
			EventFactory gainLife = gainLife(You.instance(), amount, "and you gain life equal to the life lost this way.");

			this.addEffect(ifThen(youMayPay("(WB)"), sequence(loseLife, gainLife), "You may pay (WB). If you do, each opponent loses 1 life and you gain life equal to the total life lost this way."));
		}
	}
}
