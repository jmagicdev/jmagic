package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Fallen Ferromancer")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SHAMAN})
@ManaCost("3R")
@Printings({@Printings.Printed(ex = NewPhyrexia.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class FallenFerromancer extends Card
{
	public static final class FallenFerromancerAbility1 extends ActivatedAbility
	{
		public FallenFerromancerAbility1(GameState state)
		{
			super(state, "(1)(R), (T): Fallen Ferromancer deals 1 damage to target creature or player.");
			this.setManaCost(new ManaPool("(1)(R)"));
			this.costsTap = true;
			SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));
			this.addEffect(permanentDealDamage(1, target, "Fallen Ferromancer deals 1 damage to target creature or player."));
		}
	}

	public FallenFerromancer(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Infect (This creature deals damage to creatures in the form of -1/-1
		// counters and to players in the form of poison counters.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Infect(state));

		// (1)(R), (T): Fallen Ferromancer deals 1 damage to target creature or
		// player.
		this.addAbility(new FallenFerromancerAbility1(state));
	}
}
