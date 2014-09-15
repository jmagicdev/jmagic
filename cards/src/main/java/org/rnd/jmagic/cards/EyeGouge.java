package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Eye Gouge")
@Types({Type.INSTANT})
@ManaCost("B")
@ColorIdentity({Color.BLACK})
public final class EyeGouge extends Card
{
	public EyeGouge(GameState state)
	{
		super(state);

		// Target creature gets -1/-1 until end of turn.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(ptChangeUntilEndOfTurn(target, -1, -1, "Target creature gets -1/-1 until end of turn."));

		// If it's a Cyclops, destroy it.
		SetGenerator isCyclops = Intersect.instance(HasSubType.instance(SubType.CYCLOPS), target);
		this.addEffect(ifThen(isCyclops, destroy(target, "Destroy it."), "If it's a Cyclops, destroy it."));
	}
}
