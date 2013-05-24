package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Trespassing Souleater")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.CONSTRUCT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class TrespassingSouleater extends Card
{
	public static final class TrespassingSouleaterAbility0 extends ActivatedAbility
	{
		public TrespassingSouleaterAbility0(GameState state)
		{
			super(state, "(u/p): Trespassing Souleater is unblockable this turn.");
			this.setManaCost(new ManaPool("(u/p)"));

			this.addEffect(createFloatingEffect("Trespassing Souleater is unblockable this turn.", unblockable(ABILITY_SOURCE_OF_THIS)));
		}
	}

	public TrespassingSouleater(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// (u/p): Trespassing Souleater is unblockable this turn. ((u/p) can be
		// paid with either (U) or 2 life.)
		this.addAbility(new TrespassingSouleaterAbility0(state));
	}
}
