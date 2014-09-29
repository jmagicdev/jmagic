package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Utter End")
@Types({Type.INSTANT})
@ManaCost("2WB")
@ColorIdentity({Color.WHITE, Color.BLACK})
public final class UtterEnd extends Card
{
	public UtterEnd(GameState state)
	{
		super(state);

		// Exile target nonland permanent.
		SetGenerator nonlandPermanent = RelativeComplement.instance(Permanents.instance(), HasType.instance(Type.LAND));
		SetGenerator target = targetedBy(this.addTarget(nonlandPermanent, "target nonland permanent"));
		this.addEffect(exile(target, "Exile target nonland permanent."));
	}
}
