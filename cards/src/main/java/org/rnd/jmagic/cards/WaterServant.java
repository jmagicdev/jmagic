package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Water Servant")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("2UU")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class WaterServant extends Card
{
	public static final class WaterServantAbility0 extends ActivatedAbility
	{
		public WaterServantAbility0(GameState state)
		{
			super(state, "(U): Water Servant gets +1/-1 until end of turn.");
			this.setManaCost(new ManaPool("(U)"));
			this.addEffect(createFloatingEffect("Water Servant gets +1/-1 until end of turn.", modifyPowerAndToughness(ABILITY_SOURCE_OF_THIS, +1, -1)));
		}
	}

	public static final class WaterServantAbility1 extends ActivatedAbility
	{
		public WaterServantAbility1(GameState state)
		{
			super(state, "(U): Water Servant gets -1/+1 until end of turn.");
			this.setManaCost(new ManaPool("(U)"));
			this.addEffect(createFloatingEffect("Water Servant gets -1/+1 until end of turn.", modifyPowerAndToughness(ABILITY_SOURCE_OF_THIS, -1, +1)));
		}
	}

	public WaterServant(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(4);

		// (U): Water Servant gets +1/-1 until end of turn.
		this.addAbility(new WaterServantAbility0(state));

		// (U): Water Servant gets -1/+1 until end of turn.
		this.addAbility(new WaterServantAbility1(state));
	}
}
