package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Gate Hound")
@Types({Type.CREATURE})
@SubTypes({SubType.HOUND})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = Expansion.RAVNICA, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class GateHound extends Card
{
	public static final class Vigilant extends StaticAbility
	{
		public Vigilant(GameState state)
		{
			super(state, "Creatures you control have vigilance as long as Gate Hound is enchanted.");

			SetGenerator thisIsEnchanted = Intersect.instance(HasSubType.instance(SubType.AURA), AttachedTo.instance(This.instance()));
			this.canApply = Both.instance(this.canApply, thisIsEnchanted);

			this.addEffectPart(addAbilityToObject(CREATURES_YOU_CONTROL, org.rnd.jmagic.abilities.keywords.Vigilance.class));
		}
	}

	public GateHound(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Creatures you control have vigilance as long as Gate Hound is
		// enchanted.
		this.addAbility(new Vigilant(state));
	}
}
