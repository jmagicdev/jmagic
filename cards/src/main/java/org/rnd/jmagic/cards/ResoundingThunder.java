package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Resounding Thunder")
@Types({Type.INSTANT})
@ManaCost("2R")
@ColorIdentity({Color.BLACK, Color.RED, Color.GREEN})
public final class ResoundingThunder extends Card
{
	public static final class ResoundingTrigger extends EventTriggeredAbility
	{
		public ResoundingTrigger(GameState state)
		{
			super(state, "When you cycle Resounding Thunder, it deals 6 damage to target creature or player.");

			this.canTrigger = NonEmpty.instance();

			this.addPattern(whenYouCycleThis());

			Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");

			this.addEffect(permanentDealDamage(6, targetedBy(target), "Resounding Thunder deals 6 damage to target creature or player."));
		}
	}

	public ResoundingThunder(GameState state)
	{
		super(state);

		Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");
		this.addEffect(spellDealDamage(3, targetedBy(target), "Resounding Thunder deals 3 damage to target creature or player."));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Cycling(state, "(5)(B)(R)(G)"));
		this.addAbility(new ResoundingTrigger(state));
	}
}
