package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Chill of Foreboding")
@Types({Type.SORCERY})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class ChillofForeboding extends Card
{
	public ChillofForeboding(GameState state)
	{
		super(state);

		// Each player puts the top five cards of his or her library into his or
		// her graveyard.
		DynamicEvaluation dynamic = DynamicEvaluation.instance();
		EventFactory eachPlayer = new EventFactory(FOR_EACH_PLAYER, "Each player puts the top five cards of his or her library into his or her graveyard.");
		eachPlayer.parameters.put(EventType.Parameter.TARGET, Identity.instance(dynamic));
		eachPlayer.parameters.put(EventType.Parameter.EFFECT, Identity.instance(millCards(dynamic, 5, "Put the top five cards of your library into your graveyard")));
		this.addEffect(eachPlayer);

		// Flashback (7)(U) (You may cast this card from your graveyard for its
		// flashback cost. Then exile it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flashback(state, "(7)(U)"));
	}
}
