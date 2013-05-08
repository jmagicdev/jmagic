package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Telemin Performance")
@Types({Type.SORCERY})
@ManaCost("3UU")
@Printings({@Printings.Printed(ex = Expansion.CONFLUX, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class TeleminPerformance extends Card
{
	public TeleminPerformance(GameState state)
	{
		super(state);

		SetGenerator target = targetedBy(this.addTarget(OpponentsOf.instance(You.instance()), "target opponent"));
		SetGenerator targetLibrary = LibraryOf.instance(target);
		SetGenerator toReveal = TopMost.instance(targetLibrary, numberGenerator(1), HasType.instance(Type.CREATURE));

		EventFactory reveal = reveal(toReveal, "Target opponent reveals cards from the top of his or her library until he or she reveals a creature card.");
		this.addEffect(reveal);

		SetGenerator revealed = EffectResult.instance(reveal);
		this.addEffect(putIntoGraveyard(RelativeComplement.instance(revealed, HasType.instance(Type.CREATURE)), "That player puts all noncreature cards revealed this way into his or her graveyard."));

		this.addEffect(putOntoBattlefield(Intersect.instance(revealed, HasType.instance(Type.CREATURE)), "Then you put the creature card onto the battlefield under your control."));
	}
}
