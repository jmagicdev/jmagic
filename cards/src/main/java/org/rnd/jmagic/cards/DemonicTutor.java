package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Demonic Tutor")
@Types({Type.SORCERY})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = RevisedEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = UnlimitedEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = LimitedEditionBeta.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = LimitedEditionAlpha.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class DemonicTutor extends Card
{
	public DemonicTutor(GameState state)
	{
		super(state);

		// Search your library for a card and put that card into your hand. Then
		// shuffle your library.
		EventType.ParameterMap parameters = new EventType.ParameterMap();
		parameters.put(EventType.Parameter.CAUSE, This.instance());
		parameters.put(EventType.Parameter.PLAYER, You.instance());
		parameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
		parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
		this.addEffect(new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, parameters, "Search your library for a card and put that card into your hand. Then shuffle your library."));

	}
}
