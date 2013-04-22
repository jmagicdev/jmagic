package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Cloudthresher")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("2GGGG")
@Printings({@Printings.Printed(ex = Expansion.LORWYN, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class Cloudthresher extends Card
{
	public static final class CloudthresherAbility2 extends EventTriggeredAbility
	{
		public CloudthresherAbility2(GameState state)
		{
			super(state, "When Cloudthresher enters the battlefield, it deals 2 damage to each creature with flying and each player.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator flyers = Intersect.instance(CreaturePermanents.instance(), HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class));
			this.addEffect(permanentDealDamage(2, Union.instance(flyers, Players.instance()), "Cloudthresher deals 2 damage to each creature with flying and each player."));
		}
	}

	public Cloudthresher(GameState state)
	{
		super(state);

		this.setPower(7);
		this.setToughness(7);

		// Flash
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flash(state));

		// Reach (This can block creatures with flying.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Reach(state));

		// When Cloudthresher enters the battlefield, it deals 2 damage to each
		// creature with flying and each player.
		this.addAbility(new CloudthresherAbility2(state));

		// Evoke (2)(G)(G) (You may cast this spell for its evoke cost. If you
		// do, it's sacrificed when it enters the battlefield.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Evoke(state, "(2)(G)(G)"));
	}
}
