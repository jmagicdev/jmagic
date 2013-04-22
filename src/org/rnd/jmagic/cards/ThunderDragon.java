package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Thunder Dragon")
@Types({Type.CREATURE})
@SubTypes({SubType.DRAGON})
@ManaCost("5RR")
@Printings({@Printings.Printed(ex = Expansion.DRAGONS, r = Rarity.RARE), @Printings.Printed(ex = Expansion.STARTER, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class ThunderDragon extends Card
{
	public static final class ThunderDragonAbility1 extends EventTriggeredAbility
	{
		public ThunderDragonAbility1(GameState state)
		{
			super(state, "When Thunder Dragon enters the battlefield, it deals 3 damage to each creature without flying.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator hasFlying = HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class);
			SetGenerator withoutFlying = RelativeComplement.instance(CreaturePermanents.instance(), hasFlying);
			this.addEffect(permanentDealDamage(3, withoutFlying, "It deals 3 damage to each creature without flying."));
		}
	}

	public ThunderDragon(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Thunder Dragon enters the battlefield, it deals 3 damage to each
		// creature without flying.
		this.addAbility(new ThunderDragonAbility1(state));
	}
}
