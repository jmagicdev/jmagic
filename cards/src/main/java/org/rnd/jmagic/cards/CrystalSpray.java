package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Crystal Spray")
@Types({Type.INSTANT})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Invasion.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class CrystalSpray extends Card
{
	public CrystalSpray(GameState state)
	{
		super(state);

		Target target = this.addTarget(Union.instance(Permanents.instance(), Spells.instance()), "target spell or permanent");

		// Change the text of target spell or permanent by replacing all
		// instances of one color word or basic land type with another until end
		// of turn.
		EventType.ParameterMap parameters = new EventType.ParameterMap();
		parameters.put(EventType.Parameter.CAUSE, This.instance());
		parameters.put(EventType.Parameter.TARGET, targetedBy(target));
		this.addEffect(new EventFactory(EventType.TEXT_CHANGE_COLOR_OR_BASIC_LAND_TYPE, parameters, "Change the text of target spell or permanent by replacing all instances of one color word with another or one basic land type with another."));

		// Draw a card.
		this.addEffect(drawCards(You.instance(), 1, "\n\nDraw a card."));
	}
}
