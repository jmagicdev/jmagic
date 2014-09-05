package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Bloodcrazed Goblin")
@Types({Type.CREATURE})
@SubTypes({SubType.BERSERKER, SubType.GOBLIN})
@ManaCost("R")
@Printings({@Printings.Printed(ex = Magic2011.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class BloodcrazedGoblin extends Card
{
	public static final class Bloodcrazed extends StaticAbility
	{
		public Bloodcrazed(GameState state)
		{
			super(state, "Bloodcrazed Goblin can't attack unless an opponent has been dealt damage this turn.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Intersect.instance(This.instance(), Attacking.instance())));
			this.addEffectPart(part);

			state.ensureTracker(new DamageDealtToThisTurn.Tracker());
			SetGenerator damageToOpponents = DamageDealtToThisTurn.instance(OpponentsOf.instance(You.instance()));
			SetGenerator noOpponentWasDealtDamage = Intersect.instance(numberGenerator(0), damageToOpponents);
			this.canApply = Both.instance(this.canApply, noOpponentWasDealtDamage);
		}
	}

	public BloodcrazedGoblin(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Bloodcrazed Goblin can't attack unless an opponent has been dealt
		// damage this turn.
		this.addAbility(new Bloodcrazed(state));
	}
}
