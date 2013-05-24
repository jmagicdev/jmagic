package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ad Nauseam")
@Types({Type.INSTANT})
@ManaCost("3BB")
@Printings({@Printings.Printed(ex = Expansion.SHARDS_OF_ALARA, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class AdNauseam extends Card
{
	public static final PlayerInterface.ChooseReason REASON = new PlayerInterface.ChooseReason("AdNauseam", "Repeat this process?", true);

	/**
	 * @eparam PLAYER: You
	 */
	public static final EventType YOU_MAKE_ME_SICK = new EventType("YOU_MAKE_ME_SICK")
	{

		@Override
		public Parameter affects()
		{
			return Parameter.PLAYER;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Player you = parameters.get(Parameter.PLAYER).getOne(Player.class);
			Zone library = you.getLibrary(game.actualState);
			Zone hand = you.getHand(game.actualState);
			if(!library.objects.isEmpty())
			{
				Set cause = new Set(event.getSource());
				boolean repeat = true;
				while(repeat)
				{
					GameObject topCard = library.objects.iterator().next();

					java.util.Map<Parameter, Set> revealParameters = new java.util.HashMap<Parameter, Set>();
					revealParameters.put(EventType.Parameter.CAUSE, cause);
					revealParameters.put(EventType.Parameter.OBJECT, new Set(topCard));
					Event reveal = createEvent(game, "Reveal the top card of your library", REVEAL, revealParameters);
					reveal.perform(event, true);

					java.util.Map<Parameter, Set> moveParameters = new java.util.HashMap<Parameter, Set>();
					moveParameters.put(EventType.Parameter.CAUSE, cause);
					moveParameters.put(EventType.Parameter.TO, new Set(hand));
					moveParameters.put(EventType.Parameter.OBJECT, new Set(topCard));
					Event move = createEvent(game, "Put that card into your hand", MOVE_OBJECTS, moveParameters);
					move.perform(event, true);

					java.util.Map<Parameter, Set> lifeParameters = new java.util.HashMap<Parameter, Set>();
					lifeParameters.put(EventType.Parameter.CAUSE, cause);
					lifeParameters.put(EventType.Parameter.PLAYER, new Set(you));
					lifeParameters.put(EventType.Parameter.NUMBER, new Set(topCard.getConvertedManaCost()));
					Event life = createEvent(game, "You lose life equal to that card's converted mana cost", LOSE_LIFE, lifeParameters);
					life.perform(event, true);

					library = library.getActual();
					if(library.objects.isEmpty())
						break;

					you = you.getActual();
					java.util.List<Answer> answer = you.sanitizeAndChoose(game.actualState, 1, Answer.mayChoices(), PlayerInterface.ChoiceType.ENUM, REASON);
					if(answer.contains(Answer.NO))
						repeat = false;
				}
			}
			event.setResult(Empty.set);
			return true;
		}

	};

	public AdNauseam(GameState state)
	{
		super(state);

		// Reveal the top card of your library and put that card into your hand.
		// You lose life equal to its converted mana cost. You may repeat this
		// process any number of times.
		EventFactory effect = new EventFactory(YOU_MAKE_ME_SICK, "Reveal the top card of your library and put that card into your hand. You lose life equal to its converted mana cost. You may repeat this process any number of times.");
		effect.parameters.put(EventType.Parameter.PLAYER, You.instance());
		this.addEffect(effect);
	}
}
