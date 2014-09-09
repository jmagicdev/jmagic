package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Demolish")
@Types({Type.SORCERY})
@ManaCost("3R")
@ColorIdentity({Color.RED})
public final class Demolish extends Card
{
	public Demolish(GameState state)
	{
		super(state);

		SetGenerator artifact = HasType.instance(Type.ARTIFACT);
		SetGenerator land = HasType.instance(Type.LAND);
		SetGenerator targetableTypes = Union.instance(artifact, land);
		SetGenerator targets = Intersect.instance(InZone.instance(Battlefield.instance()), targetableTypes);

		Target target = this.addTarget(targets, "target artifact or land");
		this.addEffect(destroy(targetedBy(target), "Destroy target artifact or land."));
	}
}
