package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Hold the Gates")
@Types({Type.ENCHANTMENT})
@ManaCost("2W")
@ColorIdentity({Color.WHITE})
public final class HoldtheGates extends Card
{
	public static final class HoldtheGatesAbility0 extends StaticAbility
	{
		public HoldtheGatesAbility0(GameState state)
		{
			super(state, "Creatures you control get +0/+1 for each Gate you control and have vigilance.");

			SetGenerator gatesYouControl = Intersect.instance(ControlledBy.instance(You.instance()), HasSubType.instance(SubType.GATE));
			this.addEffectPart(modifyPowerAndToughness(CREATURES_YOU_CONTROL, numberGenerator(+0), Count.instance(gatesYouControl)));
			this.addEffectPart(addAbilityToObject(CREATURES_YOU_CONTROL, org.rnd.jmagic.abilities.keywords.Vigilance.class));
		}
	}

	public HoldtheGates(GameState state)
	{
		super(state);

		// Creatures you control get +0/+1 for each Gate you control and have
		// vigilance.
		this.addAbility(new HoldtheGatesAbility0(state));
	}
}
