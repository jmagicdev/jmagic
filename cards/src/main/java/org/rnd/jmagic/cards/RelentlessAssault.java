package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Relentless Assault")
@Types({Type.SORCERY})
@ManaCost("2RR")
@ColorIdentity({Color.RED})
public final class RelentlessAssault extends Card
{
	public RelentlessAssault(GameState state)
	{
		super(state);

		// Untap all creatures that attacked this turn.
		this.addEffect(untap(Intersect.instance(CreaturePermanents.instance(), AttackedThisTurn.instance()), "Untap all creatures that attacked this turn."));

		// After this main phase there is an additional combat phase followed by
		// an additional main phase.
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
}
