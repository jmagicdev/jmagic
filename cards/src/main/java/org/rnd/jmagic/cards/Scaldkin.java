package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Scaldkin")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("3U")
@ColorIdentity({Color.RED, Color.BLUE})
public final class Scaldkin extends Card
{
	public static final class ScaldkinAbility1 extends ActivatedAbility
	{
		public ScaldkinAbility1(GameState state)
		{
			super(state, "(2)(R), Sacrifice Scaldkin: Scaldkin deals 2 damage to target creature or player.");
			this.setManaCost(new ManaPool("(2)(R)"));
			this.addCost(sacrificeThis("Scaldkin"));
			SetGenerator target = targetedBy(this.addTarget(CREATURES_AND_PLAYERS, "target creature or player"));
			this.addEffect(permanentDealDamage(2, target, "Scaldkin"));
		}
	}

	public Scaldkin(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// (2)(R), Sacrifice Scaldkin: Scaldkin deals 2 damage to target
		// creature or player.
		this.addAbility(new ScaldkinAbility1(state));
	}
}
