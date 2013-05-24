package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Silverchase Fox")
@Types({Type.CREATURE})
@SubTypes({SubType.FOX})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class SilverchaseFox extends Card
{
	public static final class SilverchaseFoxAbility0 extends ActivatedAbility
	{
		public SilverchaseFoxAbility0(GameState state)
		{
			super(state, "(1)(W), Sacrifice Silverchase Fox: Exile target enchantment.");
			this.setManaCost(new ManaPool("(1)(W)"));
			this.addCost(sacrificeThis("Silverchase Fox"));
			SetGenerator target = targetedBy(this.addTarget(EnchantmentPermanents.instance(), "target enchantment"));
			this.addEffect(exile(target, "Exile target enchantment."));
		}
	}

	public SilverchaseFox(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// (1)(W), Sacrifice Silverchase Fox: Exile target enchantment.
		this.addAbility(new SilverchaseFoxAbility0(state));
	}
}
