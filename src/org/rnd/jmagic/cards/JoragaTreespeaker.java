package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Joraga Treespeaker")
@Types({Type.CREATURE})
@SubTypes({SubType.DRUID, SubType.ELF})
@ManaCost("G")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class JoragaTreespeaker extends Card
{
	public static final class TapForTwoGreen extends org.rnd.jmagic.abilities.TapForMana
	{
		public TapForTwoGreen(GameState state)
		{
			super(state, "(G)(G)");
		}
	}

	public static final class EveryoneTapsForTwoGreen extends StaticAbility
	{
		public EveryoneTapsForTwoGreen(GameState state)
		{
			super(state, "Elves you control have \"(T): Add (G)(G) to your mana pool.\"");

			SetGenerator elvesYouControl = Intersect.instance(HasSubType.instance(SubType.ELF), ControlledBy.instance(You.instance()));
			this.addEffectPart(addAbilityToObject(elvesYouControl, TapForTwoGreen.class));
		}
	}

	public JoragaTreespeaker(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Level up (1)(G)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.LevelUp(state, "(1)(G)"));

		// LEVEL 1-4
		// 1/2
		// (T): Add (G)(G) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Level(state, 1, 4, 1, 2, "(T): Add (G)(G) to your mana pool.", TapForTwoGreen.class));

		// LEVEL 5+
		// 1/4
		// Elves you control have "(T): Add (G)(G) to your mana pool."
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Level(state, 5, 1, 4, "Elves you control have \"(T): Add (G)(G) to your mana pool.\"", EveryoneTapsForTwoGreen.class));
	}
}
