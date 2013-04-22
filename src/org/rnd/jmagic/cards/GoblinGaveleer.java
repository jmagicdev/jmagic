package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Goblin Gaveleer")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.GOBLIN})
@ManaCost("R")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class GoblinGaveleer extends Card
{
	public static final class GoblinGaveleerAbility1 extends StaticAbility
	{
		public GoblinGaveleerAbility1(GameState state)
		{
			super(state, "Goblin Gaveleer gets +2/+0 for each Equipment attached to it.");

			SetGenerator count = Count.instance(Intersect.instance(AttachedTo.instance(This.instance()), HasSubType.instance(SubType.EQUIPMENT)));

			this.addEffectPart(modifyPowerAndToughness(This.instance(), Multiply.instance(numberGenerator(2), count), numberGenerator(0)));
		}
	}

	public GoblinGaveleer(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));

		// Goblin Gaveleer gets +2/+0 for each Equipment attached to it.
		this.addAbility(new GoblinGaveleerAbility1(state));
	}
}
