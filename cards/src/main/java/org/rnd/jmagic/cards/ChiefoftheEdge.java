package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Chief of the Edge")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WARRIOR})
@ManaCost("WB")
@ColorIdentity({Color.WHITE, Color.BLACK})
public final class ChiefoftheEdge extends Card
{
	public static final class ChiefoftheEdgeAbility0 extends StaticAbility
	{
		public ChiefoftheEdgeAbility0(GameState state)
		{
			super(state, "Other Warrior creatures you control get +1/+0.");
			SetGenerator warriors = Intersect.instance(CREATURES_YOU_CONTROL, HasSubType.instance(SubType.WARRIOR));
			SetGenerator yourWarriors = Intersect.instance(ControlledBy.instance(You.instance()), warriors);
			this.addEffectPart(modifyPowerAndToughness(yourWarriors, +1, +0));
		}
	}

	public ChiefoftheEdge(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		// Other Warrior creatures you control get +1/+0.
		this.addAbility(new ChiefoftheEdgeAbility0(state));
	}
}
