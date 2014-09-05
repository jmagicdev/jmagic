package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Into the Core")
@Types({Type.INSTANT})
@ManaCost("2RR")
@Printings({@Printings.Printed(ex = MirrodinBesieged.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class IntotheCore extends Card
{
	public IntotheCore(GameState state)
	{
		super(state);

		Target target = this.addTarget(ArtifactPermanents.instance(), "two target artifacts");
		target.setSingleNumber(numberGenerator(2));

		// Exile two target artifacts.
		this.addEffect(exile(targetedBy(target), "Exile two target artifacts."));
	}
}
