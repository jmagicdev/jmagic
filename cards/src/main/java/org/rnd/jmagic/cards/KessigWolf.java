package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Kessig Wolf")
@Types({Type.CREATURE})
@SubTypes({SubType.WOLF})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class KessigWolf extends Card
{
	public static final class KessigWolfAbility0 extends ActivatedAbility
	{
		public KessigWolfAbility0(GameState state)
		{
			super(state, "(1)(R): Kessig Wolf gains first strike until end of turn.");
			this.setManaCost(new ManaPool("(1)(R)"));
			this.addEffect(addAbilityUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, org.rnd.jmagic.abilities.keywords.FirstStrike.class, "Kessig Wolf gains first strike until end of turn."));
		}
	}

	public KessigWolf(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(1);

		// (1)(R): Kessig Wolf gains first strike until end of turn.
		this.addAbility(new KessigWolfAbility0(state));
	}
}
