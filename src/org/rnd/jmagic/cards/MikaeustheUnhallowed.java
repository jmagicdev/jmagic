package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Mikaeus, the Unhallowed")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.ZOMBIE, SubType.CLERIC})
@ManaCost("3BBB")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.MYTHIC)})
@ColorIdentity({Color.BLACK})
public final class MikaeustheUnhallowed extends Card
{
	public static final class MikaeustheUnhallowedAbility1 extends EventTriggeredAbility
	{
		public MikaeustheUnhallowedAbility1(GameState state)
		{
			super(state, "Whenever a Human deals damage to you, destroy it.");
			this.addPattern(new SimpleDamagePattern(HasSubType.instance(SubType.HUMAN), You.instance()));

			SetGenerator it = SourceOfDamage.instance(TriggerDamage.instance(This.instance()));
			this.addEffect(destroy(it, "Destroy that Human"));
		}
	}

	public static final class MikaeustheUnhallowedAbility2 extends StaticAbility
	{
		public MikaeustheUnhallowedAbility2(GameState state)
		{
			super(state, "Other non-Human creatures you control get +1/+1 and have undying.");

			SetGenerator otherNonHumans = RelativeComplement.instance(CREATURES_YOU_CONTROL, Union.instance(HasSubType.instance(SubType.HUMAN), This.instance()));
			this.addEffectPart(modifyPowerAndToughness(otherNonHumans, +1, +1));
			this.addEffectPart(addAbilityToObject(otherNonHumans, org.rnd.jmagic.abilities.keywords.Undying.class));
		}
	}

	public MikaeustheUnhallowed(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Intimidate
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Intimidate(state));

		// Whenever a Human deals damage to you, destroy it.
		this.addAbility(new MikaeustheUnhallowedAbility1(state));

		// Other non-Human creatures you control get +1/+1 and have undying.
		// (When a creature with undying dies, if it had no +1/+1 counters on
		// it, return it to the battlefield under its owner's control with a
		// +1/+1 counter on it.)
		this.addAbility(new MikaeustheUnhallowedAbility2(state));
	}
}
