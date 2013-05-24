package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Tumble Magnet")
@Types({Type.ARTIFACT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.COMMON)})
@ColorIdentity({})
public final class TumbleMagnet extends Card
{
	public static final class TumbleMagnetAbility1 extends ActivatedAbility
	{
		public TumbleMagnetAbility1(GameState state)
		{
			super(state, "(T), Remove a charge counter from Tumble Magnet: Tap target artifact or creature.");
			this.costsTap = true;
			this.addCost(removeCounters(1, Counter.CounterType.CHARGE, ABILITY_SOURCE_OF_THIS, "Remove a charge counter from Tumble Magnet"));
			SetGenerator target = targetedBy(this.addTarget(Union.instance(ArtifactPermanents.instance(), CreaturePermanents.instance()), "target artifact or creature"));
			this.addEffect(tap(target, "Tap target artifact or creature."));
		}
	}

	public TumbleMagnet(GameState state)
	{
		super(state);

		// Tumble Magnet enters the battlefield with three charge counters on
		// it.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldWithCounters(state, "Tumble Magnet", 3, Counter.CounterType.CHARGE));

		// (T), Remove a charge counter from Tumble Magnet: Tap target artifact
		// or creature.
		this.addAbility(new TumbleMagnetAbility1(state));
	}
}
