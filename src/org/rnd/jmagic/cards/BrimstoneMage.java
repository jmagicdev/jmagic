package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Brimstone Mage")
@Types({Type.CREATURE})
@SubTypes({SubType.SHAMAN, SubType.HUMAN})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class BrimstoneMage extends Card
{
	public static final class Ping1 extends ActivatedAbility
	{
		public Ping1(GameState state)
		{
			super(state, "(T): Brimstone Mage deals 1 damage to target creature or player.");

			this.costsTap = true;

			Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");

			this.addEffect(permanentDealDamage(1, targetedBy(target), "Brimstone Mage deals 1 damage to target creature or player."));
		}
	}

	public static final class Ping3 extends ActivatedAbility
	{
		public Ping3(GameState state)
		{
			super(state, "(T): Brimstone Mage deals 3 damage to target creature or player.");

			this.costsTap = true;

			Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");

			this.addEffect(permanentDealDamage(3, targetedBy(target), "Brimstone Mage deals 3 damage to target creature or player."));
		}
	}

	public BrimstoneMage(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Level up (3)(R) ((3)(R): Put a level counter on this. Level up only
		// as a sorcery.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.LevelUp(state, "(3)(R)"));

		// LEVEL 1-2
		// 2/3
		// (T): Brimstone Mage deals 1 damage to target creature or player.
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Level(state, 1, 2, 2, 3, "\"(T): Brimstone Mage deals 1 damage to target creature or player.\"", Ping1.class));

		// LEVEL 3+
		// 2/4
		// (T): Brimstone Mage deals 3 damage to target creature or player.
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Level(state, 3, 2, 4, "\"(T): Brimstone Mage deals 3 damage to target creature or player.\"", Ping3.class));
	}
}
