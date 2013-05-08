package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Battle Rampart")
@Types({Type.CREATURE})
@SubTypes({SubType.WALL})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.MERCADIAN_MASQUES, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class BattleRampart extends Card
{
	public static final class BattleRampartAbility1 extends ActivatedAbility
	{
		public BattleRampartAbility1(GameState state)
		{
			super(state, "(T): Target creature gains haste until end of turn.");
			this.costsTap = true;

			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");

			this.addEffect(addAbilityUntilEndOfTurn(targetedBy(target), org.rnd.jmagic.abilities.keywords.Haste.class, "Target creature gains haste until end of turn."));
		}
	}

	public BattleRampart(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(3);

		// Defender
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));

		// (T): Target creature gains haste until end of turn.
		this.addAbility(new BattleRampartAbility1(state));
	}
}
