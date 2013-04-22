package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Vampire Bats")
@Types({Type.CREATURE})
@SubTypes({SubType.BAT})
@ManaCost("B")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.FIFTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.FOURTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.LEGENDS, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class VampireBats extends Card
{
	public static final class VampireBreath extends ActivatedAbility
	{
		public VampireBreath(GameState state)
		{
			super(state, "(B): Vampire Bats gets +1/+0 until end of turn. Activate this ability no more than twice each turn.");

			this.setManaCost(new ManaPool("B"));

			this.perTurnLimit(2);

			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, (+0), (+1), "Vampire Bats gets +0/+1 until end of turn."));
		}
	}

	public VampireBats(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new VampireBreath(state));
	}
}
