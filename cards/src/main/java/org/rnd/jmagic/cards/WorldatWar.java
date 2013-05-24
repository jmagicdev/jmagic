package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("World at War")
@Types({Type.SORCERY})
@ManaCost("3RR")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class WorldatWar extends Card
{
	public static final class FirstPostcombatMain extends SetGenerator
	{
		private static SetGenerator _instance = new FirstPostcombatMain();

		private FirstPostcombatMain()
		{
			// singleton
		}

		public static SetGenerator instance()
		{
			return _instance;
		}

		@Override
		public Set evaluate(GameState state, Identified thisObject)
		{
			for(Phase phase: state.currentTurn().phasesRan)
				if(phase.type == Phase.PhaseType.POSTCOMBAT_MAIN)
					return Empty.set;

			if(state.currentPhase().type == Phase.PhaseType.POSTCOMBAT_MAIN)
				return new Set(state.currentPhase());

			for(Phase phase: state.currentTurn().phases)
				if(phase.type == Phase.PhaseType.POSTCOMBAT_MAIN)
					return new Set(phase);

			return Empty.set;
		}
	}

	public WorldatWar(GameState state)
	{
		super(state);

		// After the first postcombat main phase this turn, there's an
		// additional combat phase followed by an additional main phase. At the
		// beginning of that combat, untap all creatures that attacked this
		// turn.
		java.util.List<Phase.PhaseType> combatAndMain = new java.util.LinkedList<Phase.PhaseType>();
		combatAndMain.add(Phase.PhaseType.COMBAT);
		combatAndMain.add(Phase.PhaseType.POSTCOMBAT_MAIN);

		EventFactory newPhases = new EventFactory(EventType.TAKE_EXTRA_PHASE, "After the first postcombat main phase this turn, there's an additional combat phase followed by an additional main phase.");
		newPhases.parameters.put(EventType.Parameter.CAUSE, This.instance());
		newPhases.parameters.put(EventType.Parameter.TARGET, FirstPostcombatMain.instance());
		newPhases.parameters.put(EventType.Parameter.PHASE, Identity.instance((Object)combatAndMain));
		this.addEffect(newPhases);

		// from the delayed trigger's perspective, "this" is the delayed trigger
		SetGenerator worldAtWar = ABILITY_SOURCE_OF_THIS;
		SetGenerator createdPhases = effectResultFrom(newPhases, worldAtWar);
		SetGenerator thatCombat = Intersect.instance(CombatPhaseOf.instance(Players.instance()), createdPhases);
		SimpleEventPattern atTheBeginningOfThatCombat = new SimpleEventPattern(EventType.BEGIN_PHASE);
		atTheBeginningOfThatCombat.put(EventType.Parameter.PHASE, thatCombat);

		EventFactory untap = untap(Intersect.instance(CreaturePermanents.instance(), AttackedThisTurn.instance()), "Untap all creatures that attacked this turn");
		EventFactory untapLater = new EventFactory(EventType.CREATE_DELAYED_TRIGGER, "At the beginning of that combat, untap all creatures that attacked this turn.");
		untapLater.parameters.put(EventType.Parameter.CAUSE, This.instance());
		untapLater.parameters.put(EventType.Parameter.EVENT, Identity.instance(atTheBeginningOfThatCombat));
		untapLater.parameters.put(EventType.Parameter.EFFECT, Identity.instance(untap));
		this.addEffect(untapLater);

		// Rebound (If you cast this spell from your hand, exile it as it
		// resolves. At the beginning of your next upkeep, you may cast this
		// card from exile without paying its mana cost.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Rebound(state));
	}
}
