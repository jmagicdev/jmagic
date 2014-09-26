package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ivorytusk Fortress")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEPHANT})
@ManaCost("2WBG")
@ColorIdentity({Color.WHITE, Color.BLACK, Color.GREEN})
public final class IvorytuskFortress extends Card
{
	public static final class IvorytuskFortressAbility0 extends StaticAbility
	{
		public IvorytuskFortressAbility0(GameState state)
		{
			super(state, "Untap each creature you control with a +1/+1 counter on it during each other player's untap step.");

			SetGenerator yourPumpedCreatures = Intersect.instance(CREATURES_YOU_CONTROL, HasCounterOfType.instance(Counter.CounterType.PLUS_ONE_PLUS_ONE));
			EventFactory untap = untap(yourPumpedCreatures, "Untap all permanents you control.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ADD_UNTAP_EVENT);
			part.parameters.put(ContinuousEffectType.Parameter.EVENT, Identity.instance(untap));
			this.addEffectPart(part);

			this.canApply = Both.instance(this.canApply, RelativeComplement.instance(OwnerOf.instance(CurrentStep.instance()), You.instance()));
		}
	}

	public IvorytuskFortress(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(7);

		// Untap each creature you control with a +1/+1 counter on it during
		// each other player's untap step.
		this.addAbility(new IvorytuskFortressAbility0(state));
	}
}
