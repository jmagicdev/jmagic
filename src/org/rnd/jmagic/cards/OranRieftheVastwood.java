package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Oran-Rief, the Vastwood")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.ZENDIKAR, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class OranRieftheVastwood extends Card
{
	public static final class PumpNewCreatures extends ActivatedAbility
	{
		public PumpNewCreatures(GameState state)
		{
			super(state, "(T): Put a +1/+1 counter on each green creature that entered the battlefield this turn.");
			this.costsTap = true;

			state.ensureTracker(new PutOntoTheBattlefieldThisTurn.BirthTracker());
			SetGenerator greenCreatures = Intersect.instance(HasColor.instance(Color.GREEN), HasType.instance(Type.CREATURE));
			SetGenerator newGreenCreatures = Intersect.instance(PutOntoTheBattlefieldThisTurn.instance(), greenCreatures);

			this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, newGreenCreatures, "Put a +1/+1 counter on each green creature that entered the battlefield this turn."));
		}
	}

	public OranRieftheVastwood(GameState state)
	{
		super(state);

		// Oran-Rief, the Vastwood enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		// (T): Add (G) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForG(state));

		// (T): Put a +1/+1 counter on each green creature that entered the
		// battlefield this turn.
		this.addAbility(new PumpNewCreatures(state));
	}
}
