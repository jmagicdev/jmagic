package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Flames of the Blood Hand")
@Types({Type.INSTANT})
@ManaCost("2R")
@ColorIdentity({Color.RED})
public final class FlamesoftheBloodHand extends Card
{
	public FlamesoftheBloodHand(GameState state)
	{
		super(state);

		// Flames of the Blood Hand deals 4 damage to target player. The damage
		// can't be prevented.
		SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));

		EventFactory dealDamage = spellDealDamage(4, target, "Flames of the Blood Hand deals 4 damage to target player. The damage can't be prevented.");
		dealDamage.parameters.put(EventType.Parameter.PREVENT, Empty.instance());
		this.addEffect(dealDamage);

		// If that player would gain life this turn, that player gains no life
		// instead.
		SimpleEventPattern gainLife = new SimpleEventPattern(EventType.GAIN_LIFE_ONE_PLAYER);
		gainLife.put(EventType.Parameter.PLAYER, target);
		EventReplacementEffect replacement = new EventReplacementEffect(this.game, "If that player would gain life this turn, that player gains no life instead.", gainLife);
		this.addEffect(createFloatingReplacement(replacement, "If that player would gain life this turn, that player gains no life instead."));
	}
}
