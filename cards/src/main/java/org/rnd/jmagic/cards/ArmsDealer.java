package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Arms Dealer")
@Types({Type.CREATURE})
@SubTypes({SubType.ROGUE, SubType.GOBLIN})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = MercadianMasques.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class ArmsDealer extends Card
{
	public static final class ArmsDealerAbility0 extends ActivatedAbility
	{
		public ArmsDealerAbility0(GameState state)
		{
			super(state, "(1)(R), Sacrifice a Goblin: Arms Dealer deals 4 damage to target creature.");
			this.setManaCost(new ManaPool("(1)(R)"));
			this.addCost(sacrifice(You.instance(), 1, HasSubType.instance(SubType.GOBLIN), "Sacrifice a Goblin"));

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(permanentDealDamage(4, target, "Arms Dealer deals 4 damage to target creature."));
		}
	}

	public ArmsDealer(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (1)(R), Sacrifice a Goblin: Arms Dealer deals 4 damage to target
		// creature.
		this.addAbility(new ArmsDealerAbility0(state));
	}
}
