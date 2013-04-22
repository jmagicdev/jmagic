package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Spiketail Hatchling")
@Types({Type.CREATURE})
@SubTypes({SubType.DRAKE})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.EIGHTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.PROPHECY, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class SpiketailHatchling extends Card
{
	// Sacrifice Spiketail Hatchling: Counter target spell unless its controller
	// pays 1.
	public static final class SpiketailHatchlingAbility extends ActivatedAbility
	{
		public SpiketailHatchlingAbility(GameState state)
		{
			super(state, "Sacrifice Spiketail Hatchling: Counter target spell unless its controller pays (1).");

			this.addCost(sacrificeThis("Spiketail Hatchling"));

			Target target = this.addTarget(Spells.instance(), "target spell");

			this.addEffect(counterTargetUnlessControllerPays("(1)", target));
		}
	}

	public SpiketailHatchling(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new SpiketailHatchlingAbility(state));
	}
}
