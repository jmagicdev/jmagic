package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Blood Cultist")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.HUMAN})
@ManaCost("1BR")
@Printings({@Printings.Printed(ex = ShardsOfAlara.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK, Color.RED})
public final class BloodCultist extends Card
{
	public static final class CreaturePing extends ActivatedAbility
	{
		public CreaturePing(GameState state)
		{
			super(state, "(T): Blood Cultist deals 1 damage to target creature.");

			this.costsTap = true;

			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

			this.addEffect(permanentDealDamage(1, targetedBy(target), "Blood Cultist deals 1 damage to target creature."));
		}
	}

	public BloodCultist(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		this.addAbility(new CreaturePing(state));

		this.addAbility(new org.rnd.jmagic.abilities.VampireKill(state, "Blood Cultist"));
	}
}
