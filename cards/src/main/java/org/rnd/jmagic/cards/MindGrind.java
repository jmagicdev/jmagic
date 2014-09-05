package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Mind Grind")
@Types({Type.SORCERY})
@ManaCost("XUB")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class MindGrind extends Card
{
	public MindGrind(GameState state)
	{
		super(state);

		// Each opponent reveals cards from the top of his or her library until
		// he or she reveals X land cards, then puts all cards revealed this way
		// into his or her graveyard. X can't be 0.
		DynamicEvaluation eachOpponent = DynamicEvaluation.instance();
		SetGenerator toReveal = TopMost.instance(LibraryOf.instance(eachOpponent), ValueOfX.instance(This.instance()), HasType.instance(Type.LAND));
		EventFactory reveal = reveal(toReveal, "Each opponent reveals cards from the top of his or her library until he or she reveals X land cards,");
		EventFactory move = putIntoGraveyard(toReveal, "then puts all cards revealed this way into his or her graveyard.");

		EventFactory factory = new EventFactory(FOR_EACH_PLAYER, "Each opponent reveals cards from the top of his or her library until he or she reveals X land cards, then puts all cards revealed this way into his or her graveyard. X can't be 0.");
		factory.parameters.put(EventType.Parameter.PLAYER, OpponentsOf.instance(You.instance()));
		factory.parameters.put(EventType.Parameter.TARGET, Identity.instance(eachOpponent));
		factory.parameters.put(EventType.Parameter.EFFECT, Identity.instance(sequence(reveal, move)));
		this.addEffect(factory);

		this.setMinimumX(1);
	}
}
