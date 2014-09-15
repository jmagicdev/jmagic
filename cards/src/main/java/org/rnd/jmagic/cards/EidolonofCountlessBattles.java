package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Eidolon of Countless Battles")
@Types({Type.ENCHANTMENT, Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("1WW")
@ColorIdentity({Color.WHITE})
public final class EidolonofCountlessBattles extends Card
{
	public static final class EidolonofCountlessBattlesAbility1 extends StaticAbility
	{
		public EidolonofCountlessBattlesAbility1(GameState state)
		{
			super(state, "Eidolon of Countless Battles and enchanted creature each get +1/+1 for each creature you control and +1/+1 for each Aura you control.");

			SetGenerator stuff = Union.instance(This.instance(), EnchantedBy.instance(This.instance()));
			SetGenerator yourAuras = Intersect.instance(ControlledBy.instance(You.instance()), HasSubType.instance(SubType.AURA));
			SetGenerator amount = Count.instance(Union.instance(CREATURES_YOU_CONTROL, yourAuras));
			this.addEffectPart(modifyPowerAndToughness(stuff, amount, amount));
		}
	}

	public EidolonofCountlessBattles(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(0);

		// Bestow (2)(W)(W) (If you cast this card for its bestow cost, it's an
		// Aura spell with enchant creature. It becomes a creature again if it's
		// not attached to a creature.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Bestow(state, "(2)(W)(W)"));

		// Eidolon of Countless Battles and enchanted creature each get +1/+1
		// for each creature you control and +1/+1 for each Aura you control.
		this.addAbility(new EidolonofCountlessBattlesAbility1(state));
	}
}
