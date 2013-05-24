package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Redirect")
@Types({Type.INSTANT})
@ManaCost("UU")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.RARE), @Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.RARE), @Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.RARE)})
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
