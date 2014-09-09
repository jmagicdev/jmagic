package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Nighthaze")
@Types({Type.SORCERY})
@ManaCost("B")
@ColorIdentity({Color.BLACK})
public final class Nighthaze extends Card
{
	public Nighthaze(GameState state)
	{
		super(state);

		// Target creature gains swampwalk until end of turn.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(addAbilityUntilEndOfTurn(target, org.rnd.jmagic.abilities.keywords.Landwalk.Swampwalk.class, "Target creature gains swampwalk until end of turn."));

		// Draw a card.
		this.addEffect(drawCards(You.instance(), 1, "\n\nDraw a card."));
	}
}
