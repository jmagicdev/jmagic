package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Abzan Falconer")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SOLDIER})
@ManaCost("2W")
@ColorIdentity({Color.WHITE})
public final class AbzanFalconer extends Card
{
	public static final class AbzanFalconerAbility1 extends StaticAbility
	{
		public AbzanFalconerAbility1(GameState state)
		{
			super(state, "Each creature you control with a +1/+1 counter on it has flying.");
			this.addEffectPart(addAbilityToObject(CREATURES_YOU_CONTROL_WITH_PLUS_ONE_COUNTER, org.rnd.jmagic.abilities.keywords.Flying.class));
		}
	}

	public AbzanFalconer(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// Outlast (W) ((W), (T): Put a +1/+1 counter on this creature. Outlast
		// only as a sorcery.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Outlast(state, "(W)"));

		// Each creature you control with a +1/+1 counter on it has flying.
		this.addAbility(new AbzanFalconerAbility1(state));
	}
}
