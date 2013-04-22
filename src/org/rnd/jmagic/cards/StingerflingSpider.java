package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Stingerfling Spider")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIDER})
@ManaCost("4G")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class StingerflingSpider extends Card
{
	public static final class StingerflingSpiderAbility1 extends EventTriggeredAbility
	{
		public StingerflingSpiderAbility1(GameState state)
		{
			super(state, "When Stingerfling Spider enters the battlefield, you may destroy target creature with flying.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator legal = Intersect.instance(CreaturePermanents.instance(), HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class));
			SetGenerator target = targetedBy(this.addTarget(legal, "target creature with flying"));
			this.addEffect(youMay(destroy(target, "Destroy target creature with flying.")));
		}
	}

	public StingerflingSpider(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(5);

		// Reach (This creature can block creatures with flying.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Reach(state));

		// When Stingerfling Spider enters the battlefield, you may destroy
		// target creature with flying.
		this.addAbility(new StingerflingSpiderAbility1(state));
	}
}
