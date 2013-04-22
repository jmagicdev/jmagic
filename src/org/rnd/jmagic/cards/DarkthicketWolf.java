package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Darkthicket Wolf")
@Types({Type.CREATURE})
@SubTypes({SubType.WOLF})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class DarkthicketWolf extends Card
{
	public static final class DarkthicketWolfAbility0 extends ActivatedAbility
	{
		public DarkthicketWolfAbility0(GameState state)
		{
			super(state, "(2)(G): Darkthicket Wolf gets +2/+2 until end of turn. Activate this ability only once each turn.");
			this.setManaCost(new ManaPool("(2)(G)"));
			this.perTurnLimit(1);

			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +2, +2, "Darkthicket Wolf gets +2/+2 until end of turn."));
		}
	}

	public DarkthicketWolf(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// (2)(G): Darkthicket Wolf gets +2/+2 until end of turn. Activate this
		// ability only once each turn.
		this.addAbility(new DarkthicketWolfAbility0(state));
	}
}
