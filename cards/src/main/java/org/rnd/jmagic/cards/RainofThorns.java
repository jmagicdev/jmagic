package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Rain of Thorns")
@Types({Type.SORCERY})
@ManaCost("4GG")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class RainofThorns extends Card
{
	public RainofThorns(GameState state)
	{
		super(state);

		// Choose one or more \u2014 Destroy target artifact; destroy target
		// enchantment; and/or destroy target land.
		this.setNumModes(new Set(new org.rnd.util.NumberRange(1, null)));

		{
			SetGenerator target = targetedBy(this.addTarget(1, ArtifactPermanents.instance(), "target artifact"));
			this.addEffect(1, destroy(target, "Destroy target artifact"));
		}

		{
			SetGenerator target = targetedBy(this.addTarget(2, EnchantmentPermanents.instance(), "target enchantment"));
			this.addEffect(2, destroy(target, "destroy target enchantment"));
		}

		{
			SetGenerator target = targetedBy(this.addTarget(3, LandPermanents.instance(), "target land"));
			this.addEffect(3, destroy(target, "destroy target land"));
		}
	}
}
