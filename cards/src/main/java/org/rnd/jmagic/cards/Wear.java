package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Wear")
@Types({Type.INSTANT})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = DragonsMaze.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class Wear extends Card
{
	public Wear(GameState state)
	{
		super(state);

		Target target = this.addTarget(ArtifactPermanents.instance(), "target artifact");
		this.addEffect(destroy(targetedBy(target), "Destroy target artifact."));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Fuse(state));
	}
}
