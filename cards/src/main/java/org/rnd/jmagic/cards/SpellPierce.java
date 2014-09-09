package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Spell Pierce")
@Types({Type.INSTANT})
@ManaCost("U")
@ColorIdentity({Color.BLUE})
public final class SpellPierce extends Card
{
	public SpellPierce(GameState state)
	{
		super(state);

		Target target = this.addTarget(RelativeComplement.instance(Spells.instance(), HasType.instance(Type.CREATURE)), "target noncreature spell");
		this.addEffect(counterTargetUnlessControllerPays("(2)", target));
	}
}
