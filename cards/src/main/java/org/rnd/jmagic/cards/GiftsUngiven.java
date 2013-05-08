package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Gifts Ungiven")
@Types({Type.INSTANT})
@ManaCost("3U")
@Printings({@Printings.Printed(ex = Expansion.CHAMPIONS_OF_KAMIGAWA, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class GiftsUngiven extends Card
{
	PlayerInterface.ChooseReason REASON = new PlayerInterface.ChooseReason("GiftsUngiven", "Choose two cards to put into that player's graveyard.", true);

	/**
	 * @eparam PLAYER: you
	 */
	public static final EventType GIFTS_UNGIVEN_SEARCH = new EventType("GIFTS_UNGIVEN_SEARCH")
	{
		@Override
		public Parameter affects()
		{
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Player you = parameters.get(Parameter.PLAYER).getOne(Player.class);
			Zone library = you.getLibrary(game.actualState);

			while(true)
			{
				java.util.Map<Parameter, Set> searchParameters = new java.util.HashMap<Parameter, Set>();
				searchParameters.put(Parameter.CAUSE, new Set(event.getSource()));
				searchParameters.put(Parameter.PLAYER, new Set(you));
				searchParameters.put(Parameter.NUMBER, new Set(4));
				searchParameters.put(Parameter.CARD, new Set(library));
				// adding a redundant type parameter to keep the search
				// "restricted"
				// -- this means they can fail to find.
				searchParameters.put(Parameter.TYPE, new Set(Identity.instance(library.objects)));
				Event search = createEvent(game, "Search your library for four cards with different names.", EventType.SEARCH, searchParameters);
				search.perform(event, false);

				Set found = search.getResult();
				java.util.Collection<String> names = new java.util.HashSet<String>();
				boolean differentNames = true;
				for(GameObject o: found.getAll(GameObject.class))
				{
					String name = o.getName();
					if(names.contains(name))
					{
						differentNames = false;
						break;
					}
					names.add(name);
				}

				if(differentNames)
				{
					event.setResult(found);
					return true;
				}
				you = you.getActual();
				library = library.getActual();
			}
		}
	};

	public GiftsUngiven(GameState state)
	{
		super(state);

		// Search your library for four cards with different names
		EventFactory search = new EventFactory(GIFTS_UNGIVEN_SEARCH, "Search your library for four cards with different names");
		search.parameters.put(EventType.Parameter.PLAYER, You.instance());
		this.addEffect(search);

		// and reveal them.
		SetGenerator thoseCards = EffectResult.instance(search);
		this.addEffect(reveal(thoseCards, "and reveal them."));

		// Target opponent chooses two of those cards.
		SetGenerator target = targetedBy(this.addTarget(OpponentsOf.instance(You.instance()), "target opponent"));
		EventFactory choose = playerChoose(target, 2, thoseCards, PlayerInterface.ChoiceType.OBJECTS, this.REASON, "Target opponent chooses two of those cards.");
		this.addEffect(choose);

		// Put the chosen cards into your graveyard and the rest into your hand.
		SetGenerator chosenCards = EffectResult.instance(choose);
		EventFactory toYard = putIntoGraveyard(chosenCards, "Put the chosen cards into your graveyard");
		EventFactory toHand = putIntoHand(RelativeComplement.instance(thoseCards, chosenCards), You.instance(), "and the rest into your hand.");
		this.addEffect(simultaneous(toYard, toHand));

		// Then shuffle your library.
		this.addEffect(shuffleYourLibrary("Then shuffle your library."));
	}
}
