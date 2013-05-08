package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Assassin's Strike")
@Types({Type.SORCERY})
@ManaCost("4BB")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class AssassinsStrike extends Card
{
	public AssassinsStrike(GameState state)
	{
		super(state);

		// Destroy target creature. Its controller discards a card.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(destroy(target, "Destroy target creature."));
		this.addEffect(discardCards(ControllerOf.instance(target), 1, "Its controller discards a card."));
	}
}
