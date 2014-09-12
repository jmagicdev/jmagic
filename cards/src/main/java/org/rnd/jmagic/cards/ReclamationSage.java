package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Reclamation Sage")
@Types({Type.CREATURE})
@SubTypes({SubType.SHAMAN, SubType.ELF})
@ManaCost("2G")
@ColorIdentity({Color.GREEN})
public final class ReclamationSage extends Card
{
	public static final class ReclamationSageAbility0 extends EventTriggeredAbility
	{
		public ReclamationSageAbility0(GameState state)
		{
			super(state, "When Reclamation Sage enters the battlefield, you may destroy target artifact or enchantment.");
			this.addPattern(whenThisEntersTheBattlefield());

			Target target = this.addTarget(Union.instance(ArtifactPermanents.instance(), EnchantmentPermanents.instance()), "target artifact or enchantment");
			this.addEffect(youMay(destroy(targetedBy(target), "Destroy target artifact or enchantment.")));
		}
	}

	public ReclamationSage(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// When Reclamation Sage enters the battlefield, you may destroy target
		// artifact or enchantment.
		this.addAbility(new ReclamationSageAbility0(state));
	}
}
