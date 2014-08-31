package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Hull Breach")
@Types({Type.SORCERY})
@ManaCost("RG")
@Printings({@Printings.Printed(ex = Expansion.COMMANDER, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.PLANESHIFT, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN, Color.RED})
public final class HullBreach extends Card
{
	public HullBreach(GameState state)
	{
		super(state);

		// Choose one \u2014 Destroy target artifact; or destroy target
		// enchantment; or destroy target artifact and target enchantment.
		{
			SetGenerator target = targetedBy(this.addTarget(1, ArtifactPermanents.instance(), "target artifact"));
			this.addEffect(1, destroy(target, "Destroy target artifact."));
		}
		{
			SetGenerator target = targetedBy(this.addTarget(2, EnchantmentPermanents.instance(), "target enchantment"));
			this.addEffect(2, destroy(target, "Destroy target enchantment."));
		}
		{
			SetGenerator targetArtifact = targetedBy(this.addTarget(3, ArtifactPermanents.instance(), "target artifact"));
			SetGenerator targetEnchantment = targetedBy(this.addTarget(3, EnchantmentPermanents.instance(), "target enchantment"));
			this.addEffect(3, destroy(Union.instance(targetArtifact, targetEnchantment), "Destroy target artifact and target enchantment."));
		}
	}
}
