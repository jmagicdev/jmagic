package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Flash of Insight")
@Types({Type.INSTANT})
@ManaCost("X1U")
@Printings({@Printings.Printed(ex = Expansion.JUDGMENT, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class FlashofInsight extends Card
{
	public FlashofInsight(GameState state)
	{
		super(state);

		EventType.ParameterMap parameters = new EventType.ParameterMap();
		parameters.put(EventType.Parameter.CAUSE, This.instance());
		parameters.put(EventType.Parameter.NUMBER, ValueOfX.instance(This.instance()));
		parameters.put(EventType.Parameter.PLAYER, You.instance());
		parameters.put(EventType.Parameter.ZONE, LibraryOf.instance(You.instance()));
		this.addEffect(new EventFactory(LOOK_AT_THE_TOP_N_CARDS_PUT_ONE_INTO_HAND_AND_THE_REST_ON_BOTTOM, parameters, "Look at the top X cards of your library. Put one of them into your hand and the rest on the bottom of your library in any order."));

		EventFactory exile = new EventFactory(EventType.EXILE_CHOICE, "Exile X blue cards from your graveyard.");
		exile.parameters.put(EventType.Parameter.CAUSE, CurrentGame.instance());
		exile.parameters.put(EventType.Parameter.NUMBER, ValueOfX.instance(This.instance()));
		exile.parameters.put(EventType.Parameter.OBJECT, Intersect.instance(HasColor.instance(Color.BLUE), InZone.instance(GraveyardOf.instance(You.instance()))));
		exile.parameters.put(EventType.Parameter.PLAYER, You.instance());
		exile.usesX();
		CostCollection flashbackCost = new CostCollection(org.rnd.jmagic.abilities.keywords.Flashback.COST_TYPE, "1U", exile);
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flashback(state, flashbackCost));
	}
}
