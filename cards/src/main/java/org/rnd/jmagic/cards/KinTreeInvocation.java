package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Kin-Tree Invocation")
@Types({Type.SORCERY})
@ManaCost("BG")
@ColorIdentity({Color.BLACK, Color.GREEN})
public final class KinTreeInvocation extends Card
{
	public KinTreeInvocation(GameState state)
	{
		super(state);

		// Put an X/X black and green Spirit Warrior creature token onto the
		// battlefield, where X is the greatest toughness among creatures you
		// control.
		SetGenerator X = Maximum.instance(ToughnessOf.instance(CREATURES_YOU_CONTROL));

		CreateTokensFactory spiritWarrior = new CreateTokensFactory(numberGenerator(1), X, X, "Put an X/X black and green Spirit Warrior creature token onto the battlefield, where X is the greatest toughness among creatures you control.");
		spiritWarrior.setColors(Color.BLACK, Color.GREEN);
		spiritWarrior.setSubTypes(SubType.SPIRIT, SubType.WARRIOR);
		this.addEffect(spiritWarrior.getEventFactory());
	}
}
