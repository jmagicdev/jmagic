package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Keening Apparition")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class KeeningApparition extends Card
{
	public static final class KeeningApparitionAbility0 extends ActivatedAbility
	{
		public KeeningApparitionAbility0(GameState state)
		{
			super(state, "Sacrifice Keening Apparition: Destroy target enchantment.");
			this.addCost(sacrificeThis("Keening Apparition"));

			Target target = this.addTarget(EnchantmentPermanents.instance(), "target enchantment");
			this.addEffect(destroy(targetedBy(target), "Destroy target enchantment."));
		}
	}

	public KeeningApparition(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Sacrifice Keening Apparition: Destroy target enchantment.
		this.addAbility(new KeeningApparitionAbility0(state));
	}
}
