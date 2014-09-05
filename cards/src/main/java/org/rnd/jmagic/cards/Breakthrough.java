package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Breakthrough")
@Types({Type.SORCERY})
@ManaCost("XU")
@Printings({@Printings.Printed(ex = Torment.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class Breakthrough extends Card
{
	public static final PlayerInterface.ChooseReason REASON = new PlayerInterface.ChooseReason("Breakthrough", "Choose X cards in your hand", false);

	public Breakthrough(GameState state)
	{
		super(state);

		// Draw four cards, then choose X cards in your hand and discard the
		// rest.
		this.addEffect(drawCards(You.instance(), 4, "Draw four cards,"));

		SetGenerator inYourHand = InZone.instance(HandOf.instance(You.instance()));
		EventFactory choose = playerChoose(You.instance(), ValueOfX.instance(This.instance()), inYourHand, PlayerInterface.ChoiceType.OBJECTS, REASON, "then choose X cards in your hand");

		EventFactory discard = new EventFactory(EventType.DISCARD_CARDS, "and discard the rest.");
		discard.parameters.put(EventType.Parameter.CAUSE, This.instance());
		discard.parameters.put(EventType.Parameter.CARD, RelativeComplement.instance(inYourHand, EffectResult.instance(choose)));

		this.addEffect(sequence(choose, discard));
	}
}
