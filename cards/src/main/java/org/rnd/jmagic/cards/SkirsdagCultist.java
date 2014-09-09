package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Skirsdag Cultist")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SHAMAN})
@ManaCost("2RR")
@ColorIdentity({Color.RED})
public final class SkirsdagCultist extends Card
{
	public static final class SkirsdagCultistAbility0 extends ActivatedAbility
	{
		public SkirsdagCultistAbility0(GameState state)
		{
			super(state, "(R), (T), Sacrifice a creature: Skirsdag Cultist deals 2 damage to target creature or player.");
			this.setManaCost(new ManaPool("(R)"));
			this.costsTap = true;
			this.addCost(sacrificeACreature());

			Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");
			this.addEffect(permanentDealDamage(2, targetedBy(target), "Skirsdag Cultist deals 2 damage to target creature or player."));
		}
	}

	public SkirsdagCultist(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// (R), (T), Sacrifice a creature: Skirsdag Cultist deals 2 damage to
		// target creature or player.
		this.addAbility(new SkirsdagCultistAbility0(state));
	}
}
