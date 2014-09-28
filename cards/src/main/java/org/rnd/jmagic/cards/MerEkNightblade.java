package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Mer-Ek Nightblade")
@Types({Type.CREATURE})
@SubTypes({SubType.ORC, SubType.ASSASSIN})
@ManaCost("3B")
@ColorIdentity({Color.BLACK})
public final class MerEkNightblade extends Card
{
	public static final class MerEkNightbladeAbility1 extends StaticAbility
	{
		public MerEkNightbladeAbility1(GameState state)
		{
			super(state, "Each creature you control with a +1/+1 counter on it has deathtouch.");
			this.addEffectPart(addAbilityToObject(CREATURES_YOU_CONTROL_WITH_PLUS_ONE_COUNTER, org.rnd.jmagic.abilities.keywords.Deathtouch.class));
		}
	}

	public MerEkNightblade(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// Outlast (B) ((B), (T): Put a +1/+1 counter on this creature. Outlast
		// only as a sorcery.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Outlast(state, "(B)"));

		// Each creature you control with a +1/+1 counter on it has deathtouch.
		this.addAbility(new MerEkNightbladeAbility1(state));
	}
}
