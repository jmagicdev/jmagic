package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Skullcrack")
@Types({Type.INSTANT})
@ManaCost("1R")
@ColorIdentity({Color.RED})
public final class Skullcrack extends Card
{
	public Skullcrack(GameState state)
	{
		super(state);

		// Players can't gain life this turn. Damage can't be prevented this
		// turn. Skullcrack deals 3 damage to target player.

		SimpleEventPattern gainPattern = new SimpleEventPattern(EventType.GAIN_LIFE_ONE_PLAYER);
		gainPattern.put(EventType.Parameter.PLAYER, Players.instance());

		ContinuousEffect.Part noGaining = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
		noGaining.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(gainPattern));
		this.addEffect(createFloatingEffect("Players can't gain life this turn.", noGaining));

		ContinuousEffect.Part noPreventing = new ContinuousEffect.Part(ContinuousEffectType.DAMAGE_CANT_BE_PREVENTED);
		this.addEffect(createFloatingEffect("Damage can't be prevented this turn.", noPreventing));

		SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
		this.addEffect(spellDealDamage(3, target, "Skullcrack deals 3 damage to target player."));
	}
}
