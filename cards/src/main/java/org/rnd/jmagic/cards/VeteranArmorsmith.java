package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Veteran Armorsmith")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.HUMAN})
@ManaCost("WW")
@ColorIdentity({Color.WHITE})
public final class VeteranArmorsmith extends Card
{
	public VeteranArmorsmith(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		SetGenerator soldiers = Intersect.instance(HasSubType.instance(SubType.SOLDIER), CREATURES_YOU_CONTROL);
		SetGenerator others = RelativeComplement.instance(soldiers, This.instance());
		this.addAbility(new org.rnd.jmagic.abilities.StaticPTChange(state, others, "Other Soldier creatures you control", +0, +1, true));
	}
}
