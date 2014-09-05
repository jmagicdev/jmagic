package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Horseshoe Crab")
@Types({Type.CREATURE})
@SubTypes({SubType.CRAB})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = UrzasSaga.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class HorseshoeCrab extends Card
{
	public static final class Untap extends ActivatedAbility
	{
		public Untap(GameState state)
		{
			super(state, "(U): Untap Horseshoe Crab.");

			this.setManaCost(new ManaPool("U"));

			this.addEffect(untap(ABILITY_SOURCE_OF_THIS, "Untap Horseshoe Crab."));
		}
	}

	public HorseshoeCrab(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(3);

		this.addAbility(new Untap(state));
	}
}
