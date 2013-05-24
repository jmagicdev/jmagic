package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Bloodfire Colossus")
@Types({Type.CREATURE})
@SubTypes({SubType.GIANT})
@ManaCost("6RR")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.NINTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.APOCALYPSE, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class BloodfireColossus extends Card
{
	public static final class Explode extends ActivatedAbility
	{
		public Explode(GameState state)
		{
			super(state, "(R), Sacrifice Bloodfire Colossus: Bloodfire Colossus deals 6 damage to each creature and each player.");

			this.setManaCost(new ManaPool("R"));

			this.addCost(sacrificeThis("Bloodfire Colossus"));

			this.addEffect(permanentDealDamage(6, CREATURES_AND_PLAYERS, "Bloodfire Colossus deals 6 damage to each creature and each player."));
		}
	}

	public BloodfireColossus(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(6);

		this.addAbility(new Explode(state));
	}
}
