package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Yew Spirit")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT, SubType.TREEFOLK})
@ManaCost("4G")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class YewSpirit extends Card
{
	public static final class YewSpiritAbility0 extends ActivatedAbility
	{
		public YewSpiritAbility0(GameState state)
		{
			super(state, "(2)(G)(G): Yew Spirit gets +X/+X until end of turn, where X is its power.");
			this.setManaCost(new ManaPool("(2)(G)(G)"));

			SetGenerator X = PowerOf.instance(ABILITY_SOURCE_OF_THIS);
			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, X, X, "Yew Spirit gets +X/+X until end of turn, where X is its power."));
		}
	}

	public YewSpirit(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// (2)(G)(G): Yew Spirit gets +X/+X until end of turn, where X is its
		// power.
		this.addAbility(new YewSpiritAbility0(state));
	}
}
