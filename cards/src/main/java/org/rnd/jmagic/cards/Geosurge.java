package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Geosurge")
@Types({Type.SORCERY})
@ManaCost("RRRR")
@Printings({@Printings.Printed(ex = NewPhyrexia.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class Geosurge extends Card
{
	public Geosurge(GameState state)
	{
		super(state);

		// Add (R)(R)(R)(R)(R)(R)(R) to your mana pool. Spend this mana only to
		// cast artifact or creature spells.

		EventFactory addCreatureMana = new EventFactory(ADD_RESTRICTED_MANA, "Add (R)(R)(R)(R)(R)(R)(R) to your mana pool. Spend this mana only to cast artifact or creature spells.");
		addCreatureMana.parameters.put(EventType.Parameter.SOURCE, This.instance());
		addCreatureMana.parameters.put(EventType.Parameter.PLAYER, You.instance());
		addCreatureMana.parameters.put(EventType.Parameter.TYPE, Identity.instance(new TypePattern(Type.ARTIFACT, Type.CREATURE)));
		addCreatureMana.parameters.put(EventType.Parameter.PERMANENT, Empty.instance());
		addCreatureMana.parameters.put(EventType.Parameter.MANA, Identity.instance(Color.RED));
		addCreatureMana.parameters.put(EventType.Parameter.NUMBER, numberGenerator(7));
		this.addEffect(addCreatureMana);
	}
}
