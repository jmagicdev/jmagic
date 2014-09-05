package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Bogardan Firefiend")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL, SubType.SPIRIT})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = Weatherlight.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class BogardanFirefiend extends Card
{
	public static final class PostMortemShock extends EventTriggeredAbility
	{
		public PostMortemShock(GameState state)
		{
			super(state, "When Bogardan Firefiend dies, it deals 2 damage to target creature.");
			this.addPattern(whenThisDies());
			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");
			this.addEffect(permanentDealDamage(2, targetedBy(target), "Bogardan Firefiend deals 2 damage to target creature."));
		}
	}

	public BogardanFirefiend(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		this.addAbility(new PostMortemShock(state));
	}
}
