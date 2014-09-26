package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Leaping Master")
@Types({Type.CREATURE})
@SubTypes({SubType.MONK, SubType.HUMAN})
@ManaCost("1R")
@ColorIdentity({Color.RED, Color.WHITE})
public final class LeapingMaster extends Card
{
	public static final class LeapingMasterAbility0 extends ActivatedAbility
	{
		public LeapingMasterAbility0(GameState state)
		{
			super(state, "(2)(W): Leaping Master gains flying until end of turn.");
			this.setManaCost(new ManaPool("(2)(W)"));
			this.addEffect(addAbilityUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, org.rnd.jmagic.abilities.keywords.Flying.class, "Leaping Master gains flying until end of turn."));
		}
	}

	public LeapingMaster(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// (2)(W): Leaping Master gains flying until end of turn.
		this.addAbility(new LeapingMasterAbility0(state));
	}
}
