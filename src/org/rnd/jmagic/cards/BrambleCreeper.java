package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Bramble Creeper")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("4G")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class BrambleCreeper extends Card
{
	public static final class AttackBoost extends EventTriggeredAbility
	{
		public AttackBoost(GameState state)
		{
			super(state, "Whenever Bramble Creeper attacks, it gets +5/+0 until end of turn.");

			// Whenever Bramble Creeper attacks,
			this.addPattern(whenThisAttacks());

			// it gets +5/+0 until end of turn.
			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, (+5), (+0), "Bramble Creeper gets +5/+0 until end of turn."));
		}
	}

	public BrambleCreeper(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(3);

		this.addAbility(new AttackBoost(state));
	}
}
