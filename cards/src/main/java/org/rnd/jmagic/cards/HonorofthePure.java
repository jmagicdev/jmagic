package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Honor of the Pure")
@Types({Type.ENCHANTMENT})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class HonorofthePure extends Card
{
	public HonorofthePure(GameState state)
	{
		super(state);

		// White creatures you control get +1/+1.
		SetGenerator whiteCreatures = Intersect.instance(HasColor.instance(Color.WHITE), CreaturePermanents.instance());
		SetGenerator youControl = ControlledBy.instance(You.instance());
		SetGenerator who = Intersect.instance(whiteCreatures, youControl);
		this.addAbility(new org.rnd.jmagic.abilities.StaticPTChange(state, who, "White creatures you control", +1, +1, true));
	}
}
