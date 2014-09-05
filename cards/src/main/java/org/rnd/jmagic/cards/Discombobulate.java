package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.Convenience;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Discombobulate")
@Types({Type.INSTANT})
@ManaCost("2UU")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Onslaught.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class Discombobulate extends Card
{
	public Discombobulate(GameState state)
	{
		super(state);

		Target target = this.addTarget(Spells.instance(), "target spell");

		this.addEffect(Convenience.counter(targetedBy(target), "Counter target spell."));

		EventType.ParameterMap lookParameters = new EventType.ParameterMap();
		lookParameters.put(EventType.Parameter.CAUSE, This.instance());
		lookParameters.put(EventType.Parameter.PLAYER, You.instance());
		lookParameters.put(EventType.Parameter.NUMBER, numberGenerator(4));
		this.addEffect(new EventFactory(EventType.LOOK_AND_PUT_BACK, lookParameters, "Look at the top four cards of your library, then put them back in any order."));
	}
}
