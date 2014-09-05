package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Faerie Trickery")
@Types({Type.INSTANT, Type.TRIBAL})
@SubTypes({SubType.FAERIE})
@ManaCost("1UU")
@Printings({@Printings.Printed(ex = Lorwyn.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class FaerieTrickery extends Card
{
	public FaerieTrickery(GameState state)
	{
		super(state);

		Target target = this.addTarget(RelativeComplement.instance(Spells.instance(), HasSubType.instance(SubType.FAERIE)), "target non-Faerie spell");

		EventFactory factory = new EventFactory(EventType.COUNTER, "Counter target non-Faerie spell. If that spell is countered this way, exile it instead of putting it into its owner's graveyard.");
		factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		factory.parameters.put(EventType.Parameter.OBJECT, targetedBy(target));
		factory.parameters.put(EventType.Parameter.TO, ExileZone.instance());
		this.addEffect(factory);
	}
}
