package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Miner's Bane")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("4RR")
@ColorIdentity({Color.RED})
public final class MinersBane extends Card
{
	public static final class MinersBaneAbility0 extends ActivatedAbility
	{
		public MinersBaneAbility0(GameState state)
		{
			super(state, "(2)(R): Miner's Bane gets +1/+0 and gains trample until end of turn.");
			this.setManaCost(new ManaPool("(2)(R)"));
			this.addEffect(ptChangeAndAbilityUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +1, +0, "Miner's Bane gets +1/+0 and gains trample until end of turn.", org.rnd.jmagic.abilities.keywords.Trample.class));
		}
	}

	public MinersBane(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(3);

		// (2)(R): Miner's Bane gets +1/+0 and gains trample until end of turn.
		// (If it would assign enough damage to its blockers to destroy them,
		// you may have it assign the rest of its damage to defending player or
		// planeswalker.)
		this.addAbility(new MinersBaneAbility0(state));
	}
}
