package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Rootwalla")
@Types({Type.CREATURE})
@SubTypes({SubType.LIZARD})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.TEMPEST, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class Rootwalla extends Card
{
	public static final class Pump extends ActivatedAbility
	{
		public Pump(GameState state)
		{
			super(state, "(1)(G): Rootwalla gets +2/+2 until end of turn. Activate this ability only once each turn.");
			this.setManaCost(new ManaPool("1G"));
			this.perTurnLimit(1);

			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, (+2), (+2), "Rootwalla gets +2/+2 until end of turn."));
		}
	}

	public Rootwalla(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new Pump(state));
	}
}
