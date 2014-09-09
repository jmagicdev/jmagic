package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Tracker's Instincts")
@Types({Type.SORCERY})
@ManaCost("1G")
@ColorIdentity({Color.BLUE, Color.GREEN})
public final class TrackersInstincts extends Card
{
	public static final PlayerInterface.ChooseReason REASON = new PlayerInterface.ChooseReason("Tracker's Instincts", "Choose a creature card to put into your hand", true);

	public TrackersInstincts(GameState state)
	{
		super(state);

		// Reveal the top four cards of your library. Put a creature card from
		// among them into your hand and the rest into your graveyard.
		EventFactory reveal = reveal(TopCards.instance(4, LibraryOf.instance(You.instance())), "Reveal the top four cards of your library.");
		this.addEffect(reveal);

		SetGenerator revealed = EffectResult.instance(reveal);

		EventFactory choose = playerChoose(You.instance(), 1, Intersect.instance(revealed, HasType.instance(Type.CREATURE)), PlayerInterface.ChoiceType.OBJECTS, REASON, "");
		this.addEffect(choose);

		SetGenerator chosen = EffectResult.instance(choose);

		EventFactory putIntoHand = putIntoHand(chosen, You.instance(), "Put a creature card from among them into your hand");
		EventFactory putIntoGraveyard = putIntoGraveyard(RelativeComplement.instance(revealed, chosen), "and the rest into your graveyard.");
		this.addEffect(simultaneous(putIntoHand, putIntoGraveyard));

		// Flashback (2)(U) (You may cast this card from your graveyard for its
		// flashback cost. Then exile it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flashback(state, "(2)(U)"));
	}
}
