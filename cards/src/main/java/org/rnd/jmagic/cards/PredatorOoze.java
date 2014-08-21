package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Predator Ooze")
@Types({Type.CREATURE})
@SubTypes({SubType.OOZE})
@ManaCost("GGG")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class PredatorOoze extends Card
{
	public static final class PredatorOozeAbility1 extends EventTriggeredAbility
	{
		public PredatorOozeAbility1(GameState state)
		{
			super(state, "Whenever Predator Ooze attacks, put a +1/+1 counter on it.");
			this.addPattern(whenThisAttacks());
			this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, ABILITY_SOURCE_OF_THIS, "Put a +1/+1 counter on it."));
		}
	}

	public static final class PredatorOozeAbility2 extends EventTriggeredAbility
	{
		public PredatorOozeAbility2(GameState state)
		{
			super(state, "Whenever a creature dealt damage by Predator Ooze this turn dies, put a +1/+1 counter on Predator Ooze.");

			SetGenerator thisCreature = ABILITY_SOURCE_OF_THIS;
			SetGenerator damagedByThis = DealtDamageByThisTurn.instance(thisCreature);

			this.addPattern(whenXDies(damagedByThis));

			this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, ABILITY_SOURCE_OF_THIS, "Put a +1/+1 counter on Predator Ooze."));

			state.ensureTracker(new DealtDamageByThisTurn.DealtDamageByTracker());
		}
	}

	public PredatorOoze(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Indestructible
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Indestructible(state));

		// Whenever Predator Ooze attacks, put a +1/+1 counter on it.
		this.addAbility(new PredatorOozeAbility1(state));

		// Whenever a creature dealt damage by Predator Ooze this turn dies, put
		// a +1/+1 counter on Predator Ooze.
		this.addAbility(new PredatorOozeAbility2(state));
	}
}
