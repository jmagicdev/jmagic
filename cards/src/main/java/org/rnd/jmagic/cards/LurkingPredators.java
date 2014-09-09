package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Lurking Predators")
@Types({Type.ENCHANTMENT})
@ManaCost("4GG")
@ColorIdentity({Color.GREEN})
public final class LurkingPredators extends Card
{
	public static final class LurkersPounce extends EventTriggeredAbility
	{
		public LurkersPounce(GameState state)
		{
			super(state, "Whenever an opponent casts a spell, reveal the top card of your library. If it's a creature card, put it onto the battlefield. Otherwise, you may put that card on the bottom of your library.");

			// Whenever an opponent casts a spell,
			SetGenerator opponentsControl = ControlledBy.instance(OpponentsOf.instance(You.instance()), Stack.instance());
			SetGenerator spellsOpponentsControl = Intersect.instance(Spells.instance(), opponentsControl);

			SimpleEventPattern triggerPattern = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			triggerPattern.put(EventType.Parameter.OBJECT, spellsOpponentsControl);
			this.addPattern(triggerPattern);

			// reveal the top card of your library.
			SetGenerator topCard = TopCards.instance(1, LibraryOf.instance(You.instance()));

			EventType.ParameterMap revealParameters = new EventType.ParameterMap();
			revealParameters.put(EventType.Parameter.CAUSE, This.instance());
			revealParameters.put(EventType.Parameter.OBJECT, topCard);
			this.addEffect(new EventFactory(EventType.REVEAL, revealParameters, "Reveal the top card of your library."));

			// If it's a creature card, put it onto the battlefield.
			EventType.ParameterMap ontoFieldParameters = new EventType.ParameterMap();
			ontoFieldParameters.put(EventType.Parameter.CAUSE, This.instance());
			ontoFieldParameters.put(EventType.Parameter.CONTROLLER, You.instance());
			ontoFieldParameters.put(EventType.Parameter.OBJECT, topCard);

			// Otherwise, you may put that card on the bottom of your library.
			EventType.ParameterMap bottomParameters = new EventType.ParameterMap();
			bottomParameters.put(EventType.Parameter.CAUSE, This.instance());
			bottomParameters.put(EventType.Parameter.INDEX, numberGenerator(-1));
			bottomParameters.put(EventType.Parameter.OBJECT, topCard);

			EventType.ParameterMap mayParameters = new EventType.ParameterMap();
			mayParameters.put(EventType.Parameter.PLAYER, You.instance());
			mayParameters.put(EventType.Parameter.EVENT, Identity.instance(new EventFactory(EventType.PUT_INTO_LIBRARY, bottomParameters, "Put that card on the bottom of your library.")));

			SetGenerator itsACreature = Intersect.instance(topCard, HasType.instance(Type.CREATURE));

			EventType.ParameterMap ifParameters = new EventType.ParameterMap();
			ifParameters.put(EventType.Parameter.IF, itsACreature);
			ifParameters.put(EventType.Parameter.THEN, Identity.instance(new EventFactory(EventType.PUT_ONTO_BATTLEFIELD, ontoFieldParameters, "Put that card onto the battlefield.")));
			ifParameters.put(EventType.Parameter.ELSE, Identity.instance(new EventFactory(EventType.PLAYER_MAY, mayParameters, "You may put that card on the bottom of your library.")));
			this.addEffect(new EventFactory(EventType.IF_CONDITION_THEN_ELSE, ifParameters, "If it's a creature card, put it onto the battlefield. Otherwise, you may put that card on the bottom of your library."));
		}
	}

	public LurkingPredators(GameState state)
	{
		super(state);

		this.addAbility(new LurkersPounce(state));
	}
}
