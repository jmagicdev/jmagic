package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Stensia Bloodhall")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK, Color.RED})
public final class StensiaBloodhall extends Card
{
	public static final class StensiaBloodhallAbility1 extends ActivatedAbility
	{
		public StensiaBloodhallAbility1(GameState state)
		{
			super(state, "(3)(B)(R), (T): Stensia Bloodhall deals 2 damage to target player.");
			this.setManaCost(new ManaPool("(3)(B)(R)"));
			this.costsTap = true;

			SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
			this.addEffect(permanentDealDamage(2, target, "Stensia Bloodhall deals 2 damage to target player."));
		}
	}

	public StensiaBloodhall(GameState state)
	{
		super(state);

		// (T): Add (1) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));

		// (3)(B)(R), (T): Stensia Bloodhall deals 2 damage to target player.
		this.addAbility(new StensiaBloodhallAbility1(state));
	}
}
