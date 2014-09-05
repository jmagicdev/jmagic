package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Finest Hour")
@Types({Type.ENCHANTMENT})
@ManaCost("2GWU")
@Printings({@Printings.Printed(ex = AlaraReborn.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE, Color.WHITE, Color.GREEN})
public final class FinestHour extends Card
{
	public static final class ExaltedMoreCombat extends org.rnd.jmagic.abilityTemplates.ExaltedBase
	{
		private static final class FirstCombatPhase extends SetGenerator
		{
			@Override
			public Set evaluate(GameState state, Identified thisObject)
			{
				for(Phase ran: state.currentTurn().phasesRan)
					if(ran.type == Phase.PhaseType.COMBAT)
						return Empty.set;
				return NonEmpty.set;
			}
		}

		public ExaltedMoreCombat(GameState state)
		{
			super(state, "Whenever a creature you control attacks alone, if it's the first combat phase of the turn, untap that creature. After this phase, there is an additional combat phase.");

			this.interveningIf = new FirstCombatPhase();

			SetGenerator triggerEvent = TriggerEvent.instance(This.instance());
			SetGenerator thatCreature = EventParameter.instance(triggerEvent, EventType.Parameter.OBJECT);

			this.addEffect(untap(thatCreature, "Untap that creature."));

			java.util.List<Phase.PhaseType> combatPhase = new java.util.LinkedList<Phase.PhaseType>();
			combatPhase.add(Phase.PhaseType.COMBAT);

			EventFactory moreCombat = new EventFactory(EventType.TAKE_EXTRA_PHASE, "After this phase, there is an additional combat phase.");
			moreCombat.parameters.put(EventType.Parameter.CAUSE, This.instance());
			moreCombat.parameters.put(EventType.Parameter.TARGET, CurrentPhase.instance());
			moreCombat.parameters.put(EventType.Parameter.PHASE, Identity.instance((Object)combatPhase));
			this.addEffect(moreCombat);
		}
	}

	public FinestHour(GameState state)
	{
		super(state);

		// Exalted
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Exalted(state));

		// Whenever a creature you control attacks alone, if it's the first
		// combat phase of the turn, untap that creature. After this phase,
		// there is an additional combat phase.
		this.addAbility(new ExaltedMoreCombat(state));
	}
}
