package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Surge Node")
@Types({Type.ARTIFACT})
@ManaCost("1")
@Printings({@Printings.Printed(ex = Expansion.NEW_PHYREXIA, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class SurgeNode extends Card
{
	public static final class SurgeNodeAbility1 extends ActivatedAbility
	{
		public SurgeNodeAbility1(GameState state)
		{
			super(state, "(1), (T), Remove a charge counter from Surge Node: Put a charge counter on target artifact.");
			this.setManaCost(new ManaPool("(1)"));
			this.costsTap = true;
			// Remove a charge counter from Surge Node
			this.addCost(removeCounters(1, Counter.CounterType.CHARGE, ABILITY_SOURCE_OF_THIS, "Remove a charge counter from Surge Node"));

			SetGenerator target = targetedBy(this.addTarget(ArtifactPermanents.instance(), "target artifact"));
			this.addEffect(putCounters(1, Counter.CounterType.CHARGE, target, "Put a charge counter on target artifact."));
		}
	}

	public SurgeNode(GameState state)
	{
		super(state);

		// Surge Node enters the battlefield with six charge counters on it.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldWithCounters(state, this.getName(), 6, Counter.CounterType.CHARGE));

		// (1), (T), Remove a charge counter from Surge Node: Put a charge
		// counter on target artifact.
		this.addAbility(new SurgeNodeAbility1(state));
	}
}
