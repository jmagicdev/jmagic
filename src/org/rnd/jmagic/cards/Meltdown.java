package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Meltdown")
@Types({Type.SORCERY})
@ManaCost("XR")
@Printings({@Printings.Printed(ex = Expansion.URZAS_SAGA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class Meltdown extends Card
{
	public Meltdown(GameState state)
	{
		super(state);

		// Destroy each artifact with converted mana cost X or less.
		this.addEffect(destroy(Intersect.instance(ArtifactPermanents.instance(), HasConvertedManaCost.instance(Between.instance(Empty.instance(), ValueOfX.instance(This.instance())))), "Destroy each artifact with converted mana cost X or less."));
	}
}
