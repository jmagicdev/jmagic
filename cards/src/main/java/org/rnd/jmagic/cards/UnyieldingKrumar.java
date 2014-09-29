package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Unyielding Krumar")
@Types({Type.CREATURE})
@SubTypes({SubType.ORC, SubType.WARRIOR})
@ManaCost("3B")
@ColorIdentity({Color.WHITE, Color.BLACK})
public final class UnyieldingKrumar extends Card
{
	public static final class UnyieldingKrumarAbility0 extends ActivatedAbility
	{
		public UnyieldingKrumarAbility0(GameState state)
		{
			super(state, "(1)(W): Unyielding Krumar gains first strike until end of turn.");
			this.setManaCost(new ManaPool("(1)(W)"));
			this.addEffect(addAbilityUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, org.rnd.jmagic.abilities.keywords.FirstStrike.class, "Unyielding Krumar gains first strike until end of turn."));
		}
	}

	public UnyieldingKrumar(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// (1)(W): Unyielding Krumar gains first strike until end of turn.
		this.addAbility(new UnyieldingKrumarAbility0(state));
	}
}
