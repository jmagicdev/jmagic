package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Pestilence Demon")
@Types({Type.CREATURE})
@SubTypes({SubType.DEMON})
@ManaCost("5BBB")
@Printings({@Printings.Printed(ex = RiseOfTheEldrazi.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class PestilenceDemon extends Card
{
	public static final class PestilenceDemonAbility1 extends ActivatedAbility
	{
		public PestilenceDemonAbility1(GameState state)
		{
			super(state, "(B): Pestilence Demon deals 1 damage to each creature and each player.");
			this.setManaCost(new ManaPool("(B)"));
			this.addEffect(permanentDealDamage(1, CREATURES_AND_PLAYERS, "Pestilence Demon deals 1 damage to each creature and each player."));
		}
	}

	public PestilenceDemon(GameState state)
	{
		super(state);

		this.setPower(7);
		this.setToughness(6);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// (B): Pestilence Demon deals 1 damage to each creature and each
		// player.
		this.addAbility(new PestilenceDemonAbility1(state));
	}
}
