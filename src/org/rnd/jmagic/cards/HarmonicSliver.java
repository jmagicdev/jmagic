package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Harmonic Sliver")
@Types({Type.CREATURE})
@SubTypes({SubType.SLIVER})
@ManaCost("1GW")
@Printings({@Printings.Printed(ex = Expansion.TIME_SPIRAL, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class HarmonicSliver extends Card
{
	public static final class ETBDestroy extends EventTriggeredAbility
	{
		public ETBDestroy(GameState state)
		{
			super(state, "When this permanent enters the battlefield, destroy target artifact or enchantment.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator target = targetedBy(this.addTarget(Union.instance(ArtifactPermanents.instance(), EnchantmentPermanents.instance()), "target artifact or enchantment"));
			this.addEffect(destroy(target, "Destroy target artifact or enchantment."));
		}
	}

	public HarmonicSliver(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// All Slivers have
		// "When this permanent enters the battlefield, destroy target artifact or enchantment."
		this.addAbility(new org.rnd.jmagic.abilities.AllSliversHave(state, ETBDestroy.class, "All Slivers have \"When this permanent enters the battlefield, destroy target artifact or enchantment.\""));
	}
}
