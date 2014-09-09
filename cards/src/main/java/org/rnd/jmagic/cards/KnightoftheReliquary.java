package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Knight of the Reliquary")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.KNIGHT})
@ManaCost("1GW")
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class KnightoftheReliquary extends Card
{
	public static final class LandPump extends StaticAbility
	{
		public LandPump(GameState state)
		{
			super(state, "Knight of the Reliquary gets +1/+1 for each land card in your graveyard.");

			// Knight of the Reliquary gets +1/+1 for each land card in your
			// graveyard.
			SetGenerator inYourGraveyard = InZone.instance(GraveyardOf.instance(You.instance()));
			SetGenerator landsInYourGraveyard = Intersect.instance(HasType.instance(Type.LAND), inYourGraveyard);
			SetGenerator boost = Count.instance(landsInYourGraveyard);

			this.addEffectPart(modifyPowerAndToughness(This.instance(), boost, boost));
		}
	}

	public static final class LandFetch extends ActivatedAbility
	{
		public LandFetch(GameState state)
		{
			super(state, "(T), Sacrifice a Forest or Plains: Search your library for a land card, put it onto the battlefield, then shuffle your library.");

			this.costsTap = true;
			this.addCost(sacrifice(You.instance(), 1, HasSubType.instance(SubType.FOREST, SubType.PLAINS), "Sacrifice a Forest or Plains"));

			// Search your library for a land card, put it onto the battlefield,
			// then shuffle your library.
			EventType.ParameterMap searchParameters = new EventType.ParameterMap();
			searchParameters.put(EventType.Parameter.CAUSE, This.instance());
			searchParameters.put(EventType.Parameter.CONTROLLER, You.instance());
			searchParameters.put(EventType.Parameter.PLAYER, You.instance());
			searchParameters.put(EventType.Parameter.NUMBER, numberGenerator(1));
			searchParameters.put(EventType.Parameter.TO, Battlefield.instance());
			searchParameters.put(EventType.Parameter.TYPE, Identity.instance(HasType.instance(Type.LAND)));
			this.addEffect(new EventFactory(EventType.SEARCH_LIBRARY_AND_PUT_INTO, searchParameters, "Search your library for a land card, put it onto the battlefield, then shuffle your library."));

		}
	}

	public KnightoftheReliquary(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new LandPump(state));

		this.addAbility(new LandFetch(state));
	}
}
