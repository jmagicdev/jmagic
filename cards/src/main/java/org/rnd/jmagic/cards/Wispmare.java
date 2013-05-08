package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Wispmare")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = Expansion.LORWYN, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class Wispmare extends Card
{
	public static final class WispmareAbility1 extends EventTriggeredAbility
	{
		public WispmareAbility1(GameState state)
		{
			super(state, "When Wispmare enters the battlefield, destroy target enchantment.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator target = targetedBy(this.addTarget(EnchantmentPermanents.instance(), "target enchantment"));
			this.addEffect(destroy(target, "Destroy target enchantment."));
		}
	}

	public Wispmare(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(3);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Wispmare enters the battlefield, destroy target enchantment.
		this.addAbility(new WispmareAbility1(state));

		// Evoke (W) (You may cast this spell for its evoke cost. If you do,
		// it's sacrificed when it enters the battlefield.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Evoke(state, "(W)"));
	}
}
