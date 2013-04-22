package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Goblin Artillery")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.GOBLIN})
@ManaCost("1RR")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class GoblinArtillery extends Card
{
	public static final class Pingback extends ActivatedAbility
	{
		public Pingback(GameState state)
		{
			super(state, "(T): Goblin Artillery deals 2 damage to target creature or player and 3 damage to you.");

			// (T):
			this.costsTap = true;

			// TODO : These should happen at the same time. A damage replacement
			// effect that
			// Goblin Artillery deals 2 damage to target creature or player
			Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");
			this.addEffect(permanentDealDamage(2, targetedBy(target), "Goblin Artillery deals 2 damage to target creature or player"));

			// and 3 damage to you.
			this.addEffect(permanentDealDamage(3, You.instance(), "and 3 damage to you."));
		}
	}

	public GoblinArtillery(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(3);

		this.addAbility(new Pingback(state));
	}
}
