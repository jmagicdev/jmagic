package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Tuskguard Captain")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WARRIOR})
@ManaCost("2G")
@ColorIdentity({Color.GREEN})
public final class TuskguardCaptain extends Card
{
	public static final class TuskguardCaptainAbility1 extends StaticAbility
	{
		public TuskguardCaptainAbility1(GameState state)
		{
			super(state, "Each creature you control with a +1/+1 counter on it has trample.");

			this.addEffectPart(addAbilityToObject(CREATURES_YOU_CONTROL_WITH_PLUS_ONE_COUNTER, org.rnd.jmagic.abilities.keywords.Trample.class));
		}
	}

	public TuskguardCaptain(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// Outlast (G) ((G), (T): Put a +1/+1 counter on this creature. Outlast
		// only as a sorcery.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Outlast(state, "(G)"));

		// Each creature you control with a +1/+1 counter on it has trample.
		this.addAbility(new TuskguardCaptainAbility1(state));
	}
}
