package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Increasing Confusion")
@Types({Type.SORCERY})
@ManaCost("XU")
@ColorIdentity({Color.BLUE})
public final class IncreasingConfusion extends Card
{
	public IncreasingConfusion(GameState state)
	{
		super(state);

		// Target player puts the top X cards of his or her library into his or
		// her graveyard. If Increasing Confusion was cast from a graveyard,
		// that player puts twice that many cards into his or her graveyard
		// instead.
		SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
		SetGenerator X = ValueOfX.instance(This.instance());
		SetGenerator number = IfThenElse.instance(Intersect.instance(ZoneCastFrom.instance(This.instance()), GraveyardOf.instance(Players.instance())), Multiply.instance(numberGenerator(2), X), X);

		this.addEffect(millCards(target, number, "Target player puts the top X cards of his or her library into his or her graveyard. If Increasing Confusion was cast from a graveyard, that player puts twice that many cards into his or her graveyard instead."));

		// Flashback (X)(U) (You may cast this card from your graveyard for its
		// flashback cost. Then exile it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flashback(state, "(X)(U)"));
	}
}
