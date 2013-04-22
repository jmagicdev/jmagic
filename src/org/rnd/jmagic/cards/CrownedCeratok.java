package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Crowned Ceratok")
@Types({Type.CREATURE})
@SubTypes({SubType.RHINO})
@ManaCost("3G")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class CrownedCeratok extends Card
{
	public static final class CrownedCeratokAbility1 extends StaticAbility
	{
		public CrownedCeratokAbility1(GameState state)
		{
			super(state, "Each creature you control with a +1/+1 counter on it has trample.");

			SetGenerator creatures = Intersect.instance(CREATURES_YOU_CONTROL, HasCounterOfType.instance(Counter.CounterType.PLUS_ONE_PLUS_ONE));
			this.addEffectPart(addAbilityToObject(creatures, org.rnd.jmagic.abilities.keywords.Trample.class));
		}
	}

	public CrownedCeratok(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(3);

		// Trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));

		// Each creature you control with a +1/+1 counter on it has trample.
		this.addAbility(new CrownedCeratokAbility1(state));
	}
}
