package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Simian Spirit Guide")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT, SubType.APE})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = Expansion.PLANAR_CHAOS, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class SimianSpiritGuide extends Card
{
	public static final class SimianSpiritGuideAbility0 extends ActivatedAbility
	{
		public SimianSpiritGuideAbility0(GameState state)
		{
			super(state, "Exile Simian Spirit Guide from your hand: Add (R) to your mana pool.");
			this.addCost(exile(ABILITY_SOURCE_OF_THIS, "Exile Simian Spirit Guide"));
			this.activateOnlyFromHand();
			this.addEffect(addManaToYourManaPoolFromAbility("(R)"));
		}
	}

	public SimianSpiritGuide(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Exile Simian Spirit Guide from your hand: Add (R) to your mana pool.
		this.addAbility(new SimianSpiritGuideAbility0(state));
	}
}
