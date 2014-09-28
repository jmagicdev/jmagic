package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Longshot Squad")
@Types({Type.CREATURE})
@SubTypes({SubType.ARCHER, SubType.HOUND})
@ManaCost("3G")
@ColorIdentity({Color.GREEN})
public final class LongshotSquad extends Card
{
	public static final class LongshotSquadAbility1 extends StaticAbility
	{
		public LongshotSquadAbility1(GameState state)
		{
			super(state, "Each creature you control with a +1/+1 counter on it has reach.");
			this.addEffectPart(addAbilityToObject(CREATURES_YOU_CONTROL_WITH_PLUS_ONE_COUNTER, org.rnd.jmagic.abilities.keywords.Reach.class));
		}
	}

	public LongshotSquad(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Outlast (1)(G) ((1)(G), (T): Put a +1/+1 counter on this creature.
		// Outlast only as a sorcery.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Outlast(state, "(1)(G)"));

		// Each creature you control with a +1/+1 counter on it has reach. (A
		// creature with reach can block creatures with flying.)
		this.addAbility(new LongshotSquadAbility1(state));
	}
}
