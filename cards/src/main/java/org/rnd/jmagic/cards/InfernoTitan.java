package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Inferno Titan")
@Types({Type.CREATURE})
@SubTypes({SubType.GIANT})
@ManaCost("4RR")
@ColorIdentity({Color.RED})
public final class InfernoTitan extends Card
{
	public static final class InfernoTitanAbility1 extends EventTriggeredAbility
	{
		public InfernoTitanAbility1(GameState state)
		{
			super(state, "Whenever Inferno Titan enters the battlefield or attacks, it deals 3 damage divided as you choose among one, two, or three target creatures and/or players.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addPattern(whenThisAttacks());

			Target target = this.addTarget(CREATURES_AND_PLAYERS, "one, two, or three target creatures and/or players");
			target.setNumber(1, 3);
			this.setDivision(Union.instance(numberGenerator(3), Identity.instance("damage")));

			EventFactory damage = new EventFactory(EventType.DISTRIBUTE_DAMAGE, "It deals 3 damage divided as you choose among one, two, or three target creatures and/or players.");
			damage.parameters.put(EventType.Parameter.SOURCE, ABILITY_SOURCE_OF_THIS);
			damage.parameters.put(EventType.Parameter.TAKER, ChosenTargetsFor.instance(Identity.instance(target), This.instance()));
			this.addEffect(damage);
		}
	}

	public InfernoTitan(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(6);

		// (R): Inferno Titan gets +1/+0 until end of turn.
		this.addAbility(new org.rnd.jmagic.abilities.Firebreathing(state, "Inferno Titan"));

		// Whenever Inferno Titan enters the battlefield or attacks, it deals 3
		// damage divided as you choose among one, two, or three target
		// creatures and/or players.
		this.addAbility(new InfernoTitanAbility1(state));
	}
}
