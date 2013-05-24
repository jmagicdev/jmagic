package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Psychic Surgery")
@Types({Type.ENCHANTMENT})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class PsychicSurgery extends Card
{
	/**
	 * @eparam CAUSE: the cause
	 * @eparam PLAYER: the player looking
	 * @eparam OBJECT: the cards being put back
	 * @eparam RESULT: empty
	 */
	public static final EventType PSYCHIC_SURGERY_REORDER = new EventType("PSYCHIC_SURGERY_REORDER")
	{
		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			event.setResult(Empty.set);

			Set cause = parameters.get(Parameter.CAUSE);
			java.util.Set<GameObject> choices = parameters.get(Parameter.OBJECT).getAll(GameObject.class);
			java.util.List<GameObject> ordered = null;
			if(choices.size() > 1)
				ordered = parameters.get(Parameter.PLAYER).getOne(Player.class).sanitizeAndChoose(game.actualState, choices.size(), choices, PlayerInterface.ChoiceType.MOVEMENT_LIBRARY, PlayerInterface.ChooseReason.ORDER_LIBRARY_TARGET);
			else
				ordered = new java.util.LinkedList<GameObject>(choices);

			for(GameObject o: ordered)
			{
				java.util.Map<Parameter, Set> moveParameters = new java.util.HashMap<Parameter, Set>();
				moveParameters.put(EventType.Parameter.CAUSE, cause);
				moveParameters.put(EventType.Parameter.INDEX, ONE);
				moveParameters.put(EventType.Parameter.OBJECT, new Set(o));
				createEvent(game, "Put a card back.", EventType.PUT_INTO_LIBRARY, moveParameters).perform(event, true);
			}

			return true;
		}
	};

	public static final class PsychicSurgeryAbility0 extends EventTriggeredAbility
	{
		public PsychicSurgeryAbility0(GameState state)
		{
			super(state, "Whenever an opponent shuffles his or her library, you may look at the top two cards of that library. You may exile one of those cards. Then put the rest on top of that library in any order.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.SHUFFLE_ONE_LIBRARY);
			pattern.put(EventType.Parameter.PLAYER, OpponentsOf.instance(You.instance()));
			this.addPattern(pattern);

			SetGenerator thatLibrary = EventResult.instance(TriggerEvent.instance(This.instance()));
			EventFactory look = new EventFactory(EventType.LOOK, "Look at the top two cards of that library.");
			look.parameters.put(EventType.Parameter.CAUSE, This.instance());
			look.parameters.put(EventType.Parameter.OBJECT, TopCards.instance(2, thatLibrary));
			look.parameters.put(EventType.Parameter.PLAYER, You.instance());
			look.parameters.put(EventType.Parameter.EFFECT, Identity.instance(Exists.instance(This.instance())));
			this.addEffect(look);

			SetGenerator thoseTwo = EffectResult.instance(look);

			EventFactory exile = new EventFactory(EventType.EXILE_CHOICE, "Exile one of those cards.");
			exile.parameters.put(EventType.Parameter.CAUSE, This.instance());
			exile.parameters.put(EventType.Parameter.OBJECT, thoseTwo);
			exile.parameters.put(EventType.Parameter.PLAYER, This.instance());
			this.addEffect(youMay(exile, "You may exile one of those cards."));

			EventFactory putBack = new EventFactory(PSYCHIC_SURGERY_REORDER, "Then put the rest on top of that library in any order.");
			putBack.parameters.put(EventType.Parameter.CAUSE, This.instance());
			putBack.parameters.put(EventType.Parameter.PLAYER, You.instance());
			putBack.parameters.put(EventType.Parameter.OBJECT, thoseTwo);
			this.addEffect(putBack);
		}
	}

	public PsychicSurgery(GameState state)
	{
		super(state);

		// Whenever an opponent shuffles his or her library, you may look at the
		// top two cards of that library. You may exile one of those cards. Then
		// put the rest on top of that library in any order.
		this.addAbility(new PsychicSurgeryAbility0(state));
	}
}
