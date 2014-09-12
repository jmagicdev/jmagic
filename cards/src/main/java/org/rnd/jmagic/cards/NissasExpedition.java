package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Nissa's Expedition")
@Types({Type.SORCERY})
@ManaCost("4G")
@ColorIdentity({Color.GREEN})
public final class NissasExpedition extends Card
{
	public NissasExpedition(GameState state)
	{
		super(state);

		// Convoke (Your creatures can help cast this spell. Each creature you
		// tap while casting this spell pays for (1) or one mana of that
		// creature's color.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Convoke(state));

		// Search your library for up to two basic land cards, put them onto the
		// battlefield tapped, then shuffle your library.
		EventType.ParameterMap parameters = new EventType.ParameterMap();
		parameters.put(EventType.Parameter.CAUSE, This.instance());
		parameters.put(EventType.Parameter.CONTROLLER, You.instance());
		parameters.put(EventType.Parameter.PLAYER, You.instance());
		parameters.put(EventType.Parameter.NUMBER, Identity.instance(new org.rnd.util.NumberRange(0, 2)));
		parameters.put(EventType.Parameter.TO, Battlefield.instance());
		parameters.put(EventType.Parameter.TAPPED, Empty.instance());
		parameters.put(EventType.Parameter.TYPE, Identity.instance(Intersect.instance(HasSuperType.instance(SuperType.BASIC), HasType.instance(Type.LAND))));
		this.addEffect(new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, parameters, "Search your library for up to two basic land cards, put them onto the battlefield tapped, then shuffle your library."));
	}
}
