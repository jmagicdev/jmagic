package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Miming Slime")
@Types({Type.SORCERY})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class MimingSlime extends Card
{
	public MimingSlime(GameState state)
	{
		super(state);

		// Put an X/X green Ooze creature token onto the battlefield, where X is
		// the greatest power among creatures you control.

		SetGenerator X = Maximum.instance(PowerOf.instance(CREATURES_YOU_CONTROL));

		CreateTokensFactory factory = new CreateTokensFactory(numberGenerator(1), X, X, "Put an X/X green Ooze creature token onto the battlefield, where X is the greatest power among creatures you control.");
		factory.setColors(Color.GREEN);
		factory.setSubTypes(SubType.OOZE);
		this.addEffect(factory.getEventFactory());
	}
}
