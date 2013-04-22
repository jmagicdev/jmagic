package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Silklash Spider")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIDER})
@ManaCost("3GG")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.RARE), @Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.ONSLAUGHT, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class SilklashSpider extends Card
{
	// XGG: Silklash Spider deals X damage to each creature with flying.
	public static final class Hurricane extends ActivatedAbility
	{
		public Hurricane(GameState state)
		{
			super(state, "(X)(G)(G): Silklash Spider deals X damage to each creature with flying.");
			this.setManaCost(new ManaPool("XGG"));

			SetGenerator victims = Intersect.instance(CreaturePermanents.instance(), HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class));
			this.addEffect(permanentDealDamage(ValueOfX.instance(This.instance()), victims, "Silklash Spider deals X damage to each creature with flying."));
		}
	}

	public SilklashSpider(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(7);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Reach(state));
		this.addAbility(new Hurricane(state));
	}
}
