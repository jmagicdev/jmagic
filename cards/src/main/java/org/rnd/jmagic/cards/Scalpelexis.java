package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Scalpelexis")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("4U")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.JUDGMENT, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class Scalpelexis extends Card
{
	public static final class Scalp extends EventTriggeredAbility
	{
		/**
		 * @eparam PLAYER: the player to mill
		 * @eparam RESULT: empty
		 */
		public static final EventType SCALPELEXIS_MILL_EVENT = new EventType("SCALPELEXIS_MILL_EVENT")
		{
			@Override
			public Parameter affects()
			{
				return null;
			}

			@Override
			public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
			{
				boolean ret = true;
				Set cause = parameters.get(EventType.Parameter.CAUSE);
				Zone library = parameters.get(EventType.Parameter.PLAYER).getOne(Player.class).getLibrary(game.actualState);

				millLoop: while(true)
				{
					Set exile = new Set();

					for(int i = 1; i <= 4; i++)
					{
						GameObject card = library.peekAtPosition(i);
						if(card != null)
							exile.add(card);
					}

					java.util.Map<EventType.Parameter, Set> millParameters = new java.util.HashMap<EventType.Parameter, Set>();
					millParameters.put(EventType.Parameter.CAUSE, cause);
					millParameters.put(EventType.Parameter.TO, new Set(game.actualState.exileZone()));
					millParameters.put(EventType.Parameter.OBJECT, exile);
					Event millEvent = createEvent(game, "That player exiles the top four cards of his or her library.", EventType.MOVE_OBJECTS, millParameters);

					ret = millEvent.perform(event, true) && ret;

					java.util.Set<Card> result = NewObjectOf.instance(millEvent.getResultGenerator()).evaluate(game.physicalState, null).getAll(Card.class);

					java.util.Iterator<Card> iter = result.iterator();

					while(iter.hasNext())
					{
						Card card = iter.next();
						iter.remove();

						for(Card c: result)
							if(c.getName().equals(card.getName()))
							{
								library = game.actualState.<Zone>get(library.ID);
								continue millLoop;
							}
					}

					break;
				}

				event.setResult(Empty.set);
				return ret;
			}

			@Override
			public String toString()
			{
				return "SCALPELEXIS_MILL_EVENT";
			}
		};

		public Scalp(GameState state)
		{
			super(state, "Whenever Scalpelexis deals combat damage to a player, that player exiles the top four cards of his or her library. If two or more of those cards have the same name, repeat this process.");

			this.addPattern(whenDealsCombatDamageToAPlayer(ABILITY_SOURCE_OF_THIS));

			EventType.ParameterMap millParameters = new EventType.ParameterMap();
			millParameters.put(EventType.Parameter.CAUSE, This.instance());
			millParameters.put(EventType.Parameter.PLAYER, TakerOfDamage.instance(TriggerDamage.instance(This.instance())));
			this.addEffect(new EventFactory(SCALPELEXIS_MILL_EVENT, millParameters, "That player exiles the top four cards of his or her library. If two or more of those cards have the same name, repeat this process."));
		}
	}

	public Scalpelexis(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(5);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new Scalp(state));
	}
}
