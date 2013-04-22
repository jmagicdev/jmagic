package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Harbor Bandit")
@Types({Type.CREATURE})
@SubTypes({SubType.ROGUE, SubType.HUMAN})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class HarborBandit extends Card
{
	public static final class HarborBanditAbility0 extends StaticAbility
	{
		public HarborBanditAbility0(GameState state)
		{
			super(state, "Harbor Bandit gets +1/+1 as long as you control an Island.");

			this.addEffectPart(modifyPowerAndToughness(This.instance(), +1, +1));
			this.canApply = Both.instance(this.canApply, Intersect.instance(ControlledBy.instance(You.instance()), HasSubType.instance(SubType.ISLAND)));
		}
	}

	public static final class HarborBanditAbility1 extends ActivatedAbility
	{
		public HarborBanditAbility1(GameState state)
		{
			super(state, "(1)(U): Harbor Bandit is unblockable this turn.");
			this.setManaCost(new ManaPool("(1)(U)"));

			this.addEffect(createFloatingEffect("Harbor Bandit is unblockable this turn.", unblockable(ABILITY_SOURCE_OF_THIS)));
		}
	}

	public HarborBandit(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Harbor Bandit gets +1/+1 as long as you control an Island.
		this.addAbility(new HarborBanditAbility0(state));

		// (1)(U): Harbor Bandit is unblockable this turn.
		this.addAbility(new HarborBanditAbility1(state));
	}
}
