package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Disciple of the Old Ways")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.HUMAN})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN, Color.RED})
public final class DiscipleoftheOldWays extends Card
{
	public static final class DiscipleoftheOldWaysAbility0 extends ActivatedAbility
	{
		public DiscipleoftheOldWaysAbility0(GameState state)
		{
			super(state, "(R): Disciple of the Old Ways gains first strike until end of turn.");
			this.setManaCost(new ManaPool("(R)"));
			this.addEffect(addAbilityUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, org.rnd.jmagic.abilities.keywords.FirstStrike.class, "Disciple of the Old Ways gains first strike until end of turn."));
		}
	}

	public DiscipleoftheOldWays(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// (R): Disciple of the Old Ways gains first strike until end of turn.
		this.addAbility(new DiscipleoftheOldWaysAbility0(state));
	}
}
