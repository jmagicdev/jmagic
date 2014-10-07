package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Humbler of Mortals")
@Types({Type.ENCHANTMENT, Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("4GG")
@ColorIdentity({Color.GREEN})
public final class HumblerofMortals extends Card
{
	public static final class HumblerofMortalsAbility0 extends EventTriggeredAbility
	{
		public HumblerofMortalsAbility0(GameState state)
		{
			super(state, "Constellation \u2014 Whenever Humbler of Mortals or another enchantment enters the battlefield under your control, creatures you control gain trample until end of turn.");
			this.addPattern(constellation());
			this.addEffect(addAbilityUntilEndOfTurn(CREATURES_YOU_CONTROL, org.rnd.jmagic.abilities.keywords.Trample.class, "Creatures you control gain trample until end of turn."));
		}
	}

	public HumblerofMortals(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Constellation \u2014 Whenever Humbler of Mortals or another
		// enchantment enters the battlefield under your control, creatures you
		// control gain trample until end of turn.
		this.addAbility(new HumblerofMortalsAbility0(state));
	}
}
