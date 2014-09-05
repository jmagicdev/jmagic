package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Redirect")
@Types({Type.INSTANT})
@ManaCost("UU")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.RARE), @Printings.Printed(ex = Magic2012.class, r = Rarity.RARE), @Printings.Printed(ex = Magic2011.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class Redirect extends Card
{
	public Redirect(GameState state)
	{
		super(state);

		// You may choose new targets for target spell.
		SetGenerator targetSpell = targetedBy(this.addTarget(Spells.instance(), "target spell"));

		EventFactory newTargets = new EventFactory(EventType.CHANGE_TARGETS, "Choose new targets for target spell");
		newTargets.parameters.put(EventType.Parameter.OBJECT, targetSpell);
		newTargets.parameters.put(EventType.Parameter.PLAYER, You.instance());
		this.addEffect(youMay(newTargets, "You may choose new targets for target spell."));
	}
}
