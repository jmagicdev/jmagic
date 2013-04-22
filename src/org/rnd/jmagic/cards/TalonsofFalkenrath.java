package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Talons of Falkenrath")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class TalonsofFalkenrath extends Card
{
	public static final class FalkenBreath extends ActivatedAbility
	{
		public FalkenBreath(GameState state)
		{
			super(state, "(1)(R): This creature gets +2/+0 until end of turn.");
			this.setManaCost(new ManaPool("(1)(R)"));
			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +2, +0, "This creature gets +2/+0 until end of turn."));
		}
	}

	public static final class TalonsofFalkenrathAbility2 extends StaticAbility
	{
		public TalonsofFalkenrathAbility2(GameState state)
		{
			super(state, "Enchanted creature has \"(1)(R): This creature gets +2/+0 until end of turn.\"");

			this.addEffectPart(addAbilityToObject(EnchantedBy.instance(This.instance()), FalkenBreath.class));
		}
	}

	public TalonsofFalkenrath(GameState state)
	{
		super(state);

		// Flash (You may cast this spell any time you could cast an instant.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flash(state));

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Enchanted creature has
		// "(1)(R): This creature gets +2/+0 until end of turn."
		this.addAbility(new TalonsofFalkenrathAbility2(state));
	}
}
