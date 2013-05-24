package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Tinder Wall")
@Types({Type.CREATURE})
@SubTypes({SubType.PLANT,SubType.WALL})
@ManaCost("G")
@Printings({@Printings.Printed(ex = Expansion.ICE_AGE, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN, Color.RED})
public final class TinderWall extends Card
{
	public static final class TinderWallAbility1 extends ActivatedAbility
	{
		public TinderWallAbility1(GameState state)
		{
			super(state, "Sacrifice Tinder Wall: Add (R)(R) to your mana pool.");
			this.addCost(sacrificeThis("Tinder Wall"));
			this.addEffect(addManaToYourManaPoolFromAbility("(R)(R)"));
		}
	}

	public static final class TinderWallAbility2 extends ActivatedAbility
	{
		public TinderWallAbility2(GameState state)
		{
			super(state, "(R), Sacrifice Tinder Wall: Tinder Wall deals 2 damage to target creature it's blocking.");
			this.setManaCost(new ManaPool("(R)"));
			this.addCost(sacrificeThis("Tinder Wall"));
			
			Target target = this.addTarget(Intersect.instance(CreaturePermanents.instance(),BlockedBy.instance(ABILITY_SOURCE_OF_THIS)), "target creature it's blocking.");
			permanentDealDamage(numberGenerator(2), targetedBy(target), "Tinder Wall deals 2 damage to target creature it's blocking.");
		}
	}

	public TinderWall(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(3);

		// Defender (This creature can't attack.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));

		// Sacrifice Tinder Wall: Add (R)(R) to your mana pool.
		this.addAbility(new TinderWallAbility1(state));

		// (R), Sacrifice Tinder Wall: Tinder Wall deals 2 damage to target creature it's blocking.
		this.addAbility(new TinderWallAbility2(state));
	}
}
