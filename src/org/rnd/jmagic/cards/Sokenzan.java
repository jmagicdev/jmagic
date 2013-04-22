package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.gameTypes.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sokenzan")
@Types({Type.PLANE})
@SubTypes({SubType.KAMIGAWA})
@Printings({@Printings.Printed(ex = Expansion.PLANECHASE, r = Rarity.COMMON)})
@ColorIdentity({})
public final class Sokenzan extends Card
{
	public static final class CombatReady extends StaticAbility
	{
		public CombatReady(GameState state)
		{
			super(state, "All creatures get +1/+1 and have haste.");

			this.addEffectPart(modifyPowerAndToughness(CreaturePermanents.instance(), 1, 1));

			this.addEffectPart(addAbilityToObject(CreaturePermanents.instance(), org.rnd.jmagic.abilities.keywords.Haste.class));

			this.canApply = Planechase.staticAbilityCanApply;
		}
	}

	public static final class RelentlessChaos extends EventTriggeredAbility
	{
		public RelentlessChaos(GameState state)
		{
			super(state, "Whenever you roll (C), untap all creatures that attacked this turn. After this main phase, there is an additional combat phase followed by an additional main phase.");

			this.addPattern(Planechase.wheneverYouRollChaos());

			// Copied from Relentless Assault
			{
				// Untap all creatures that attacked this turn.
				this.addEffect(untap(Intersect.instance(CreaturePermanents.instance(), AttackedThisTurn.instance()), "Untap all creatures that attacked this turn."));

				// After this main phase there is an additional combat phase
				// followed by an additional main phase.
				SetGenerator thisMainPhase = Intersect.instance(CurrentPhase.instance(), MainPhaseOf.instance(Players.instance()));

				java.util.List<Phase.PhaseType> combatAndMain = new java.util.LinkedList<Phase.PhaseType>();
				combatAndMain.add(Phase.PhaseType.COMBAT);
				combatAndMain.add(Phase.PhaseType.POSTCOMBAT_MAIN);

				EventType.ParameterMap mainPhaseParameters = new EventType.ParameterMap();
				mainPhaseParameters.put(EventType.Parameter.CAUSE, This.instance());
				mainPhaseParameters.put(EventType.Parameter.TARGET, thisMainPhase);
				mainPhaseParameters.put(EventType.Parameter.PHASE, Identity.instance((Object)combatAndMain));
				this.addEffect(new EventFactory(EventType.TAKE_EXTRA_PHASE, mainPhaseParameters, "After this main phase, there is an additional combat phase followed by an additional main phase."));
			}

			this.canTrigger = Planechase.triggeredAbilityCanTrigger;
		}
	}

	public Sokenzan(GameState state)
	{
		super(state);

		this.addAbility(new CombatReady(state));

		this.addAbility(new RelentlessChaos(state));
	}
}
