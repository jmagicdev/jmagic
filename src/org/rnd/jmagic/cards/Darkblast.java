package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Darkblast")
@Types({Type.INSTANT})
@ManaCost("B")
@Printings({@Printings.Printed(ex = Expansion.RAVNICA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class Darkblast extends Card
{
	public Darkblast(GameState state)
	{
		super(state);

		// Target creature gets -1/-1 until end of turn.
		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");
		this.addEffect(ptChangeUntilEndOfTurn(targetedBy(target), (-1), (-1), "Target creature gets -1/-1 until end of turn."));

		// Dredge 3 (If you would draw a card, instead you may put exactly three
		// cards from the top of your library into your graveyard. If you do,
		// return this card from your graveyard to your hand. Otherwise, draw a
		// card.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Dredge(state, 3));
	}
}
