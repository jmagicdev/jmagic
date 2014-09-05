package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Squallmonger")
@Types({Type.CREATURE})
@SubTypes({SubType.MONGER})
@ManaCost("3G")
@Printings({@Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = MercadianMasques.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class Squallmonger extends Card
{
	public static final class SquallmongerAbility0 extends ActivatedAbility
	{
		public SquallmongerAbility0(GameState state)
		{
			super(state, "(2): Squallmonger deals 1 damage to each creature with flying and each player. Any player may activate this ability.");
			this.setManaCost(new ManaPool("(2)"));

			SetGenerator takers = Union.instance(Intersect.instance(CreaturePermanents.instance(), HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class)), Players.instance());
			this.addEffect(spellDealDamage(1, takers, "Squallmonger deals 1 damage to each creature with flying and each player."));

			this.anyPlayerMayActivateThisAbility();
		}
	}

	public Squallmonger(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// (2): Squallmonger deals 1 damage to each creature with flying and
		// each player. Any player may activate this ability.
		this.addAbility(new SquallmongerAbility0(state));
	}
}
