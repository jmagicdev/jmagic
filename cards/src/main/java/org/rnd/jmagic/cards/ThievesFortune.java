package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import static org.rnd.jmagic.Convenience.*;

@Name("Thieves' Fortune")
@Types({Type.INSTANT, Type.TRIBAL})
@SubTypes({SubType.ROGUE})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Expansion.MORNINGTIDE, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class ThievesFortune extends Card
{
	public ThievesFortune(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Prowl(state, "(U)"));

		EventType.ParameterMap parameters = new EventType.ParameterMap();
		parameters.put(EventType.Parameter.CAUSE, This.instance());
		parameters.put(EventType.Parameter.NUMBER, numberGenerator(4));
		parameters.put(EventType.Parameter.PLAYER, You.instance());
		parameters.put(EventType.Parameter.ZONE, LibraryOf.instance(You.instance()));
		this.addEffect(new EventFactory(LOOK_AT_THE_TOP_N_CARDS_PUT_ONE_INTO_HAND_AND_THE_REST_ON_BOTTOM, parameters, "Look at the top four cards of your library. Put one of them into your hand and the rest on the bottom of your library in any order."));
	}
}
