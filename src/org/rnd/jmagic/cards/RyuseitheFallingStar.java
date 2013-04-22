package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ryusei, the Falling Star")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.SPIRIT, SubType.DRAGON})
@ManaCost("5R")
@Printings({@Printings.Printed(ex = Expansion.CHAMPIONS_OF_KAMIGAWA, r = Rarity.RARE), @Printings.Printed(ex = Expansion.PROMO, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class RyuseitheFallingStar extends Card
{
	public static final class DeathDamage extends EventTriggeredAbility
	{
		public DeathDamage(GameState state)
		{
			super(state, "When Ryusei, the Falling Star dies, it deals 5 damage to each creature without flying.");
			this.addPattern(whenThisDies());

			SetGenerator creaturesWithoutFlying = RelativeComplement.instance(CreaturePermanents.instance(), HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class));
			this.addEffect(permanentDealDamage(5, creaturesWithoutFlying, "Ryusei, the Falling Star deals 5 damage to each creature without flying."));
		}
	}

	public RyuseitheFallingStar(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Ryusei, the Falling Star is put into a graveyard from the
		// battlefield, it deals 5 damage to each creature without flying.
		this.addAbility(new DeathDamage(state));
	}
}
