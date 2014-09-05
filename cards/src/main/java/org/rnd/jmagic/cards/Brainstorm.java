package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Brainstorm")
@Types({Type.INSTANT})
@ManaCost("U")
@Printings({@Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.COMMON), @Printings.Printed(ex = MercadianMasques.class, r = Rarity.COMMON), @Printings.Printed(ex = FifthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = IceAge.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class Brainstorm extends Card
{
	public static final PlayerInterface.ChooseReason REASON = new PlayerInterface.ChooseReason("Brainstorm", "Put two cards on top of your library.", false);

	public Brainstorm(GameState state)
	{
		super(state);

		this.addEffect(drawCards(You.instance(), 3, "Draw three cards,"));

		EventType.ParameterMap parameters = new EventType.ParameterMap();
		parameters.put(EventType.Parameter.CAUSE, This.instance());
		parameters.put(EventType.Parameter.PLAYER, ControllerOf.instance(This.instance()));
		parameters.put(EventType.Parameter.NUMBER, numberGenerator(2));
		parameters.put(EventType.Parameter.REASON, Identity.instance(REASON));
		this.addEffect(new EventFactory(PUT_INTO_LIBRARY_FROM_HAND_CHOICE, parameters, "then put two cards from your hand on top of your library in any order."));
	}
}
