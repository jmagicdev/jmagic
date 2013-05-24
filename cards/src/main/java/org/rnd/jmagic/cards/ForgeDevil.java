package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Forge Devil")
@Types({Type.CREATURE})
@SubTypes({SubType.DEVIL})
@ManaCost("R")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class ForgeDevil extends Card
{
	public static final class ForgeDevilAbility0 extends EventTriggeredAbility
	{
		public ForgeDevilAbility0(GameState state)
		{
			super(state, "When Forge Devil enters the battlefield, it deals 1 damage to target creature and 1 damage to you.");
			this.addPattern(whenThisEntersTheBattlefield());
			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(permanentDealDamage(1, Union.instance(target, You.instance()), "It deals 1 damage to target creature and 1 damage to you."));
		}
	}

	public ForgeDevil(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// When Forge Devil enters the battlefield, it deals 1 damage to target
		// creature and 1 damage to you.
		this.addAbility(new ForgeDevilAbility0(state));
	}
}
