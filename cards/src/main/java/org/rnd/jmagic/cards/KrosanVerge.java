package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Krosan Verge")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.JUDGMENT, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class KrosanVerge extends Card
{
	/**
	 * @eparam CAUSE: the reason for the search
	 * @eparam PLAYER: the player searching
	 * @eparam RESULT: the results of the MOVE_OBJECTS event
	 */
	public static final EventType KROSAN_SEARCH = new EventType("KROSAN_SEARCH")
	{
		@Override
		public Parameter affects()
		{
			return Parameter.PLAYER;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set cause = parameters.get(Parameter.CAUSE);
			Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);

			Set object = new Set();

			for(SubType type: new SubType[] {SubType.FOREST, SubType.PLAINS})
			{
				java.util.Map<Parameter, Set> searchParameters = new java.util.HashMap<Parameter, Set>();
				searchParameters.put(Parameter.CAUSE, cause);
				searchParameters.put(Parameter.PLAYER, new Set(player));
				searchParameters.put(Parameter.NUMBER, ONE);
				searchParameters.put(Parameter.CARD, new Set(player.getLibrary(game.actualState)));
				searchParameters.put(Parameter.TYPE, new Set(Intersect.instance(HasSubType.instance(type), Cards.instance())));

				Event searchEvent = createEvent(game, "Search your library", EventType.SEARCH, searchParameters);
				searchEvent.perform(event, true);

				java.util.Map<Parameter, Set> shuffleParameters = new java.util.HashMap<Parameter, Set>();
				shuffleParameters.put(Parameter.CAUSE, cause);
				shuffleParameters.put(Parameter.PLAYER, new Set(player));

				Event shuffleEvent = createEvent(game, player + " shuffles their library", EventType.SHUFFLE_LIBRARY, shuffleParameters);
				shuffleEvent.perform(event, true);

				player = player.getActual();

				for(GameObject oldObject: searchEvent.getResult().getAll(GameObject.class))
					object.add(game.actualState.get(oldObject.getPhysical().futureSelf));
			}

			java.util.Map<Parameter, Set> ontoBattlefieldTappedParameters = new java.util.HashMap<Parameter, Set>();
			ontoBattlefieldTappedParameters.put(Parameter.CAUSE, cause);
			ontoBattlefieldTappedParameters.put(Parameter.CONTROLLER, new Set(player));
			ontoBattlefieldTappedParameters.put(Parameter.OBJECT, object);
			Event putOntoBattlefieldTapped = createEvent(game, "Put " + object + " onto the battlefield tapped", EventType.PUT_ONTO_BATTLEFIELD_TAPPED, ontoBattlefieldTappedParameters);

			boolean ret = putOntoBattlefieldTapped.perform(event, true);
			Set movedObjects = NewObjectOf.instance(putOntoBattlefieldTapped.getResultGenerator()).evaluate(game, null);
			event.setResult(Identity.instance(movedObjects));
			return ret;
		}
	};

	public static final class KrosanSearch extends ActivatedAbility
	{
		public KrosanSearch(GameState state)
		{
			super(state, "(2), (T), Sacrifice Krosan Verge: Search your library for a Forest card and a Plains card and put them onto the battlefield tapped. Then shuffle your library.");

			this.setManaCost(new ManaPool("2"));

			this.costsTap = true;

			this.addCost(sacrificeThis("Krosan Verge"));

			EventFactory factory = new EventFactory(KROSAN_SEARCH, "Search your library for a Forest card and a Plains card and put them onto the battlefield tapped. Then shuffle your library.");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(factory);
		}
	}

	public KrosanVerge(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, "Krosan Verge"));

		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));

		this.addAbility(new KrosanSearch(state));
	}
}
