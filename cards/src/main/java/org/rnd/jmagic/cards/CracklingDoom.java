package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Crackling Doom")
@Types({Type.INSTANT})
@ManaCost("RWB")
@ColorIdentity({Color.WHITE, Color.BLACK, Color.RED})
public final class CracklingDoom extends Card
{
	public CracklingDoom(GameState state)
	{
		super(state);

		// Crackling Doom deals 2 damage to each opponent.
		this.addEffect(spellDealDamage(2, OpponentsOf.instance(You.instance()), "Crackling Doom deals 2 damage to each opponent."));

		// Each opponent sacrifices a creature with the greatest power among
		// creatures he or she controls.
		DynamicEvaluation eachPlayer = DynamicEvaluation.instance();

		SetGenerator greatestPower = Maximum.instance(PowerOf.instance(ControlledBy.instance(eachPlayer)));
		EventFactory eachSacrifice = sacrifice(eachPlayer, 1, HasPower.instance(greatestPower), "Sacrifice a creature with the greatest power among creatures you control.");

		EventFactory allSacrifice = new EventFactory(FOR_EACH_PLAYER, "Each opponent sacrifices a creature with the greatest power among creatures he or she controls.");
		allSacrifice.parameters.put(EventType.Parameter.PLAYER, OpponentsOf.instance(You.instance()));
		allSacrifice.parameters.put(EventType.Parameter.TARGET, Identity.instance(eachPlayer));
		allSacrifice.parameters.put(EventType.Parameter.EFFECT, Identity.instance(eachSacrifice));
		this.addEffect(allSacrifice);
	}
}
