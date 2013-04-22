package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Sleep")
@Types({Type.SORCERY})
@ManaCost("2UU")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class Sleep extends Card
{
	/**
	 * DO NOT move this event into EventType for other "tap don't untap" events.
	 * Most of these events say "controller's next untap step"; Sleep's says
	 * "that player's next untap step".
	 * 
	 * @eparam CAUSE: Sleep
	 * @eparam PLAYER: Sleep's target
	 * @eparam RESULT: empty
	 */
	public static final EventType SLEEP_EVENT = new EventType("SLEEP_EVENT")
	{

		@Override
		public Parameter affects()
		{
			return null;
		}

		@Override
		public boolean perform(Game game, Event event, java.util.Map<Parameter, Set> parameters)
		{
			Set cause = parameters.get(Parameter.CAUSE);
			Player player = parameters.get(Parameter.PLAYER).getOne(Player.class);
			Set thoseCreatures = Intersect.instance(ControlledBy.instance(Identity.instance(player)), CreaturePermanents.instance()).evaluate(game, null);

			// Tap all creatures target player controls.
			java.util.Map<Parameter, Set> tapParameters = new java.util.HashMap<Parameter, Set>();
			tapParameters.put(Parameter.CAUSE, cause);
			tapParameters.put(Parameter.OBJECT, thoseCreatures);
			createEvent(game, "Tap all creatures that player controls.", TAP_PERMANENTS, tapParameters).perform(event, true);

			// Those creatures don't untap during that player's next untap step.
			EventPattern untapping = new UntapDuringControllersUntapStep(Identity.instance(thoseCreatures));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(untapping));

			SetGenerator thatPlayersUntap = UntapStepOf.instance(Identity.instance(player));
			SetGenerator untapStepOver = Intersect.instance(PreviousStep.instance(), thatPlayersUntap);

			java.util.Map<Parameter, Set> noUntapParameters = new java.util.HashMap<Parameter, Set>();
			noUntapParameters.put(EventType.Parameter.CAUSE, cause);
			noUntapParameters.put(EventType.Parameter.EFFECT, new Set(part));
			noUntapParameters.put(EventType.Parameter.EXPIRES, new Set(untapStepOver));
			createEvent(game, "Those creatures don't untap during that player's next untap step.", CREATE_FLOATING_CONTINUOUS_EFFECT, noUntapParameters).perform(event, true);

			event.setResult(Empty.set);
			return true;
		}

	};

	public Sleep(GameState state)
	{
		super(state);

		Target target = this.addTarget(Players.instance(), "target player");

		EventType.ParameterMap parameters = new EventType.ParameterMap();
		parameters.put(EventType.Parameter.CAUSE, This.instance());
		parameters.put(EventType.Parameter.PLAYER, targetedBy(target));
		this.addEffect(new EventFactory(SLEEP_EVENT, parameters, "Tap all creatures target player controls. Those creatures don't untap during that player's next untap step."));
	}
}
