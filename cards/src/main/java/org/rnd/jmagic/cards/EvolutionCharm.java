package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Evolution Charm")
@Types({Type.INSTANT})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = PlanarChaos.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class EvolutionCharm extends Card
{
	public EvolutionCharm(GameState state)
	{
		super(state);

		// Choose one

		// Search your library for a basic land card, reveal it, put it into
		// your hand, then shuffle your library
		{
			EventType.ParameterMap searchParameters = new EventType.ParameterMap();
			searchParameters.put(EventType.Parameter.CAUSE, This.instance());
			searchParameters.put(EventType.Parameter.PLAYER, You.instance());
			searchParameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			searchParameters.put(EventType.Parameter.TYPE, Identity.instance(Intersect.instance(HasSuperType.instance(SuperType.BASIC), HasType.instance(Type.LAND))));
			searchParameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
			this.addEffect(1, new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, searchParameters, "Search your library for a basic land card, reveal it, put it into your hand, then shuffle your library."));
		}

		// Return target creature card from your graveyard to your hand
		{
			SetGenerator graveyard = GraveyardOf.instance(You.instance());
			Target target = this.addTarget(2, Intersect.instance(HasType.instance(Type.CREATURE), InZone.instance(graveyard)), "target creature from your graveyard");

			EventType.ParameterMap moveParameters = new EventType.ParameterMap();
			moveParameters.put(EventType.Parameter.CAUSE, This.instance());
			moveParameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
			moveParameters.put(EventType.Parameter.OBJECT, targetedBy(target));
			this.addEffect(2, new EventFactory(EventType.MOVE_OBJECTS, moveParameters, "Return target creature card from your graveyard to your hand."));
		}

		// Target creature gains flying until end of turn.
		{
			Target target = this.addTarget(3, CreaturePermanents.instance(), "target creature");
			this.addEffect(3, addAbilityUntilEndOfTurn(targetedBy(target), org.rnd.jmagic.abilities.keywords.Flying.class, "Target creature gains flying until end of turn."));
		}
	}
}
