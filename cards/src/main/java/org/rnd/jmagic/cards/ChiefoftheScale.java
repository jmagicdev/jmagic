package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Chief of the Scale")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WARRIOR})
@ManaCost("WB")
@ColorIdentity({Color.WHITE, Color.BLACK})
public final class ChiefoftheScale extends Card
{
	public static final class ChiefoftheScaleAbility0 extends StaticAbility
	{
		public ChiefoftheScaleAbility0(GameState state)
		{
			super(state, "Other Warrior creatures you control get +0/+1.");
			SetGenerator warriors = Intersect.instance(CREATURES_YOU_CONTROL, HasSubType.instance(SubType.WARRIOR));
			SetGenerator yourWarriors = Intersect.instance(ControlledBy.instance(You.instance()), warriors);
			this.addEffectPart(modifyPowerAndToughness(yourWarriors, +0, +1));
		}
	}

	public ChiefoftheScale(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// Other Warrior creatures you control get +0/+1.
		this.addAbility(new ChiefoftheScaleAbility0(state));
	}
}
