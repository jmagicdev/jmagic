package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Femeref Archers")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.ARCHER})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.SEVENTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.SIXTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.MIRAGE, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class FemerefArchers extends Card
{
	public static final class Potshot extends ActivatedAbility
	{
		public Potshot(GameState state)
		{
			super(state, "(T): Femeref Archers deals 4 damage to target attacking creature with flying.");

			this.costsTap = true;

			Target target = this.addTarget(Intersect.instance(Intersect.instance(Attacking.instance(), CreaturePermanents.instance()), HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class)), "target attacking creature with flying");

			this.addEffect(permanentDealDamage(4, targetedBy(target), "Femeref Archers deals 4 damage to target attacking creature with flying."));
		}
	}

	public FemerefArchers(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new Potshot(state));
	}
}
