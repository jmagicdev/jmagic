package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Excommunicate")
@Types({Type.SORCERY})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.SHARDS_OF_ALARA, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class Excommunicate extends Card
{
	public Excommunicate(GameState state)
	{
		super(state);

		// Put target creature
		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

		// on top of
		EventType.ParameterMap parameters = new EventType.ParameterMap();
		parameters.put(EventType.Parameter.CAUSE, This.instance());
		parameters.put(EventType.Parameter.INDEX, numberGenerator(1));
		parameters.put(EventType.Parameter.OBJECT, targetedBy(target));

		// its owner's library.
		this.addEffect(new EventFactory(EventType.PUT_INTO_LIBRARY, parameters, "Put target creature on top of its owner's library."));
	}
}
