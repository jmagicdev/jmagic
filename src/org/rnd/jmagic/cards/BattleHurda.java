package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Battle Hurda")
@Types({Type.CREATURE})
@SubTypes({SubType.GIANT})
@ManaCost("4W")
@Printings({@Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class BattleHurda extends Card
{
	public BattleHurda(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// First strike
		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));
	}
}
