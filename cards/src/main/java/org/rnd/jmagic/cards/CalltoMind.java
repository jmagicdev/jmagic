package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Call to Mind")
@Types({Type.SORCERY})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Magic2011.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class CalltoMind extends Card
{
	public CalltoMind(GameState state)
	{
		super(state);

		SetGenerator target = targetedBy(this.addTarget(Intersect.instance(HasType.instance(Type.INSTANT, Type.SORCERY), InZone.instance(GraveyardOf.instance(You.instance()))), "target instant or sorcery card from your graveyard"));

		EventFactory factory = new EventFactory(EventType.MOVE_OBJECTS, "Return target instant or sorcery card from your graveyard to your hand.");
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
		factory.parameters.put(EventType.Parameter.OBJECT, target);
		this.addEffect(factory);

	}
}
