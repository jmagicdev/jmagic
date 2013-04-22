package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Boggart Birth Rite")
@Types({Type.SORCERY, Type.TRIBAL})
@SubTypes({SubType.GOBLIN})
@ManaCost("B")
@Printings({@Printings.Printed(ex = Expansion.LORWYN, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class BoggartBirthRite extends Card
{
	public BoggartBirthRite(GameState state)
	{
		super(state);

		Target target = this.addTarget(Intersect.instance(HasSubType.instance(SubType.GOBLIN), InZone.instance(GraveyardOf.instance(You.instance()))), "target Goblin card from your graveard");

		EventFactory factory = new EventFactory(EventType.MOVE_OBJECTS, "Return target Goblin card from your graveyard to your hand.");
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
		factory.parameters.put(EventType.Parameter.OBJECT, targetedBy(target));
		this.addEffect(factory);
	}
}
