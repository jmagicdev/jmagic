package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Bile Blight")
@Types({Type.INSTANT})
@ManaCost("BB")
@Printings({@Printings.Printed(ex = Expansion.BORN_OF_THE_GODS, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class BileBlight extends Card
{
	public BileBlight(GameState state)
	{
		super(state);


		// Target creature and all other creatures with the same name as that creature get -3/-3 until end of turn.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		SetGenerator withTheSameName = Intersect.instance(CreaturePermanents.instance(), HasName.instance(NameOf.instance(target)));
		this.addEffect(ptChangeUntilEndOfTurn(Union.instance(target, withTheSameName), -3, -3, "Target creature and all other creatures with the same name as that creature get -3/-3 until end of turn."));
	}
}
