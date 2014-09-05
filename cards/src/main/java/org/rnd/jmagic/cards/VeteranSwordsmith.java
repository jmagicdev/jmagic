package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Veteran Swordsmith")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.HUMAN})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = Magic2010.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class VeteranSwordsmith extends Card
{
	public VeteranSwordsmith(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		SetGenerator soldiers = Intersect.instance(HasSubType.instance(SubType.SOLDIER), CREATURES_YOU_CONTROL);
		SetGenerator others = RelativeComplement.instance(soldiers, This.instance());
		this.addAbility(new org.rnd.jmagic.abilities.StaticPTChange(state, others, "Other Soldier creatures you control", +1, +0, true));

	}
}
