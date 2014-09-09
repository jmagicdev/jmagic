package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Bramblecrush")
@Types({Type.SORCERY})
@ManaCost("2GG")
@ColorIdentity({Color.GREEN})
public final class Bramblecrush extends Card
{
	public Bramblecrush(GameState state)
	{
		super(state);

		// Destroy target noncreature permanent.
		SetGenerator nonCreatures = RelativeComplement.instance(Permanents.instance(), HasType.instance(Type.CREATURE));
		SetGenerator target = targetedBy(this.addTarget(nonCreatures, "target noncreature permanent"));
		this.addEffect(destroy(target, "Destroy target noncreature permanent."));
	}
}
