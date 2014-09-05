package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Green Sun's Zenith")
@Types({Type.SORCERY})
@ManaCost("XG")
@Printings({@Printings.Printed(ex = MirrodinBesieged.class, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class GreenSunsZenith extends Card
{
	public GreenSunsZenith(GameState state)
	{
		super(state);

		SetGenerator filter = Intersect.instance(Intersect.instance(HasColor.instance(Color.GREEN), HasType.instance(Type.CREATURE)), HasConvertedManaCost.instance(Between.instance(numberGenerator(0), ValueOfX.instance(This.instance()))));

		// Search your library for a green creature card with converted mana
		// cost X or less, put it onto the battlefield, then shuffle your
		// library.
		EventType.ParameterMap searchParameters = new EventType.ParameterMap();
		searchParameters.put(EventType.Parameter.CAUSE, This.instance());
		searchParameters.put(EventType.Parameter.CONTROLLER, You.instance());
		searchParameters.put(EventType.Parameter.PLAYER, You.instance());
		searchParameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
		searchParameters.put(EventType.Parameter.TYPE, Identity.instance(filter));
		searchParameters.put(EventType.Parameter.TO, Battlefield.instance());

		this.addEffect(new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, searchParameters, "Search your library for a green creature card with converted mana cost X or less, put it onto the battlefield, then shuffle your library."));

		// Shuffle Green Sun's Zenith into its owner's library.
		EventFactory shuffle = new EventFactory(EventType.SHUFFLE_INTO_LIBRARY, "Shuffle Green Sun's Zenith into its owner's library.");
		shuffle.parameters.put(EventType.Parameter.CAUSE, This.instance());
		shuffle.parameters.put(EventType.Parameter.OBJECT, Union.instance(This.instance(), You.instance()));
		this.addEffect(shuffle);
	}
}
