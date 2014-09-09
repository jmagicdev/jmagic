package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mystic Retrieval")
@Types({Type.SORCERY})
@ManaCost("3U")
@ColorIdentity({Color.BLUE, Color.RED})
public final class MysticRetrieval extends Card
{
	public MysticRetrieval(GameState state)
	{
		super(state);

		// Return target instant or sorcery card from your graveyard to your
		// hand.
		SetGenerator target = targetedBy(this.addTarget(Intersect.instance(HasType.instance(Type.INSTANT, Type.SORCERY), InZone.instance(GraveyardOf.instance(You.instance()))), "target instant or sorcery card from your graveyard"));

		EventFactory factory = new EventFactory(EventType.MOVE_OBJECTS, "Return target instant or sorcery card from your graveyard to your hand.");
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
		factory.parameters.put(EventType.Parameter.OBJECT, target);
		this.addEffect(factory);

		// Flashback (2)(R) (You may cast this card from your graveyard for its
		// flashback cost. Then exile it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flashback(state, "(2)(R)"));
	}
}
