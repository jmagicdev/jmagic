package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Manabond")
@Types({Type.ENCHANTMENT})
@ManaCost("G")
@ColorIdentity({Color.GREEN})
public final class Manabond extends Card
{
	/**
	 * @eparam CAUSE: Manabond's triggered ability
	 * @eparam PLAYER: controller of CAUSE
	 * @eparam FROM: all the cards in PLAYER's hand
	 * @eparam CARD: land cards in FROM
	 */
	public static final EventType MANA_DROP = new EventType("MANA_DROP")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean attempt(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set cause = parameters.get(Parameter.CAUSE);
			Set hand = parameters.get(Parameter.FROM);

			java.util.Map<Parameter, Set> revealParameters = new java.util.HashMap<Parameter, Set>();
			revealParameters.put(Parameter.CAUSE, cause);
			revealParameters.put(Parameter.OBJECT, hand);
			Event reveal = createEvent(game, "Reveal your hand", EventType.REVEAL, revealParameters);
			if(!reveal.attempt(event))
				return false;

			Set you = parameters.get(Parameter.PLAYER);
			Set lands = parameters.get(Parameter.CARD);

			java.util.Map<Parameter, Set> dropParameters = new java.util.HashMap<Parameter, Set>();
			dropParameters.put(Parameter.CAUSE, cause);
			dropParameters.put(Parameter.CONTROLLER, you);
			dropParameters.put(Parameter.OBJECT, lands);
			Event drop = createEvent(game, "Put all land cards from it onto the battlefield", EventType.PUT_ONTO_BATTLEFIELD, dropParameters);
			return drop.attempt(event);
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set cause = parameters.get(Parameter.CAUSE);
			Set hand = parameters.get(Parameter.FROM);

			java.util.Map<Parameter, Set> revealParameters = new java.util.HashMap<Parameter, Set>();
			revealParameters.put(Parameter.CAUSE, cause);
			revealParameters.put(Parameter.OBJECT, hand);
			Event reveal = createEvent(game, "Reveal your hand", EventType.REVEAL, revealParameters);
			reveal.perform(event, true);

			Set you = parameters.get(Parameter.PLAYER);
			Set lands = parameters.get(Parameter.CARD);

			java.util.Map<Parameter, Set> dropParameters = new java.util.HashMap<Parameter, Set>();
			dropParameters.put(Parameter.CAUSE, cause);
			dropParameters.put(Parameter.CONTROLLER, you);
			dropParameters.put(Parameter.OBJECT, lands);
			Event drop = createEvent(game, "Put all land cards from it onto the battlefield", EventType.PUT_ONTO_BATTLEFIELD, dropParameters);
			drop.perform(event, true);

			event.setResult(Empty.set);
			return true;
		}
	};

	public static final class ManaDrop extends EventTriggeredAbility
	{
		public ManaDrop(GameState state)
		{
			super(state, "At the beginning of your end step, you may reveal your hand and put all land cards from it onto the battlefield. If you do, discard your hand.");
			this.addPattern(atTheBeginningOfYourEndStep());

			SetGenerator inYourHand = InZone.instance(HandOf.instance(You.instance()));
			SetGenerator landsInYourHand = Intersect.instance(HasType.instance(Type.LAND), inYourHand);

			EventFactory drop = new EventFactory(MANA_DROP, "Reveal your hand and put all land cards from it onto the battlefield");
			drop.parameters.put(EventType.Parameter.CAUSE, This.instance());
			drop.parameters.put(EventType.Parameter.PLAYER, You.instance());
			drop.parameters.put(EventType.Parameter.FROM, inYourHand);
			drop.parameters.put(EventType.Parameter.CARD, landsInYourHand);

			EventFactory effect = new EventFactory(EventType.IF_EVENT_THEN_ELSE, "You may reveal your hand and put all land cards from it onto the battlefield. If you do, discard your hand.");
			effect.parameters.put(EventType.Parameter.IF, Identity.instance(youMay(drop, "You may reveal your hand and put all land cards from it onto the battlefield")));
			effect.parameters.put(EventType.Parameter.THEN, Identity.instance(discardHand(You.instance(), "Discard your hand")));
			this.addEffect(effect);
		}
	}

	public Manabond(GameState state)
	{
		super(state);

		// At the beginning of your end step, you may reveal your hand and put
		// all land cards from it onto the battlefield. If you do, discard your
		// hand.
		this.addAbility(new ManaDrop(state));
	}
}
