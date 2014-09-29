package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Set Adrift")
@Types({Type.SORCERY})
@ManaCost("5U")
@ColorIdentity({Color.BLUE})
public final class SetAdrift extends Card
{
	public SetAdrift(GameState state)
	{
		super(state);

		// Delve (Each card you exile from your graveyard while casting this
		// spell pays for (1).)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Delve(state));

		// Put target nonland permanent on top of its owner's library.
		SetGenerator nonlands = RelativeComplement.instance(Permanents.instance(), HasType.instance(Type.LAND));
		SetGenerator target = targetedBy(this.addTarget(nonlands, "target nonland permanent"));
		this.addEffect(putOnTopOfLibrary(target, "Put target nonland permanent on top of its owner's library."));
	}
}
