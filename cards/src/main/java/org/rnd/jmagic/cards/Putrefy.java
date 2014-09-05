package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Putrefy")
@Types({Type.INSTANT})
@ManaCost("1BG")
@Printings({@Printings.Printed(ex = RavnicaCityOfGuilds.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN, Color.BLACK})
public final class Putrefy extends Card
{
	public Putrefy(GameState state)
	{
		super(state);

		// Destroy target artifact or creature. It can't be regenerated.
		Target t = this.addTarget(Union.instance(ArtifactPermanents.instance(), CreaturePermanents.instance()), "target artifact or creature");
		this.addEffects(bury(this, targetedBy(t), "Destroy target artifact or creature. It can't be regenerated."));
	}
}
