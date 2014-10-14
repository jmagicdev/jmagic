package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Underworld Coinsmith")
@Types({Type.ENCHANTMENT,Type.CREATURE})
@SubTypes({SubType.HUMAN,SubType.CLERIC})
@ManaCost("WB")
@ColorIdentity({Color.WHITE, Color.BLACK})
public final class UnderworldCoinsmith extends Card
{
	public static final class UnderworldCoinsmithAbility0 extends EventTriggeredAbility
	{
		public UnderworldCoinsmithAbility0(GameState state)
		{
			super(state, "Constellation \u2014 Whenever Underworld Coinsmith or another enchantment enters the battlefield under your control, you gain 1 life.");
			this.addPattern(constellation());
		}
	}

	public static final class UnderworldCoinsmithAbility1 extends ActivatedAbility
	{
		public UnderworldCoinsmithAbility1(GameState state)
		{
			super(state, "(W)(B), Pay 1 life: Each opponent loses 1 life.");
			this.setManaCost(new ManaPool("(W)(B)"));
			this.addEffect(payLife(You.instance(), 1, "Pay 1 life."));
		}
	}

	public UnderworldCoinsmith(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Constellation \u2014 Whenever Underworld Coinsmith or another enchantment enters the battlefield under your control, you gain 1 life.
		this.addAbility(new UnderworldCoinsmithAbility0(state));

		// (W)(B), Pay 1 life: Each opponent loses 1 life.
		this.addAbility(new UnderworldCoinsmithAbility1(state));
	}
}
