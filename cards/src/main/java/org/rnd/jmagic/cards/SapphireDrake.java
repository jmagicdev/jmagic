package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sapphire Drake")
@Types({Type.CREATURE})
@SubTypes({SubType.DRAKE})
@ManaCost("5U")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class SapphireDrake extends Card
{
	public static final class SapphireDrakeAbility1 extends StaticAbility
	{
		public SapphireDrakeAbility1(GameState state)
		{
			super(state, "Each creature you control with a +1/+1 counter on it has flying.");
			SetGenerator creatures = Intersect.instance(CREATURES_YOU_CONTROL, HasCounterOfType.instance(Counter.CounterType.PLUS_ONE_PLUS_ONE));
			this.addEffectPart(addAbilityToObject(creatures, org.rnd.jmagic.abilities.keywords.Flying.class));
		}
	}

	public SapphireDrake(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Each creature you control with a +1/+1 counter on it has flying.
		this.addAbility(new SapphireDrakeAbility1(state));
	}
}
