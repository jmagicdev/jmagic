package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Skyshroud Claim")
@Types({Type.SORCERY})
@ManaCost("3G")
@Printings({@Printings.Printed(ex = Nemesis.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class SkyshroudClaim extends Card
{
	public SkyshroudClaim(GameState state)
	{
		super(state);

		EventFactory factory = new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, "Search your library for up to two Forest cards and put them onto the battlefield. Then shuffle your library.");
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.CONTROLLER, You.instance());
		factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
		// I'm saying just "2" here, even though the card says "up to two" --
		// since the search is for a specific kind of card, "up to" is
		// automatically implied by the rules.
		factory.parameters.put(EventType.Parameter.NUMBER, numberGenerator(2));
		factory.parameters.put(EventType.Parameter.TO, Battlefield.instance());
		factory.parameters.put(EventType.Parameter.TYPE, Identity.instance(HasSubType.instance(SubType.FOREST)));
		this.addEffect(factory);
	}
}
