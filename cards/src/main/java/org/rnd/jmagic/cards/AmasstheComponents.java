package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Amass the Components")
@Types({Type.SORCERY})
@ManaCost("3U")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class AmasstheComponents extends Card
{
	public static final PlayerInterface.ChooseReason REASON = new PlayerInterface.ChooseReason("AmasstheComponents", "Put a card on the bottom of your library.", false);

	public AmasstheComponents(GameState state)
	{
		super(state);

		// Draw three cards, then put a card from your hand on the bottom of
		// your library.
		this.addEffect(drawCards(You.instance(), 3, "Draw three cards,"));

		EventType.ParameterMap parameters = new EventType.ParameterMap();
		parameters.put(EventType.Parameter.CAUSE, This.instance());
		parameters.put(EventType.Parameter.PLAYER, ControllerOf.instance(This.instance()));
		parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
		parameters.put(EventType.Parameter.INDEX, numberGenerator(-1));
		parameters.put(EventType.Parameter.REASON, Identity.instance(REASON));
		this.addEffect(new EventFactory(PUT_INTO_LIBRARY_FROM_HAND_CHOICE, parameters, "then put a card from your hand on the bottom of your library."));
	}
}
