package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Sublime Archangel")
@Types({Type.CREATURE})
@SubTypes({SubType.ANGEL})
@ManaCost("2WW")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.MYTHIC)})
@ColorIdentity({Color.WHITE})
public final class SublimeArchangel extends Card
{
	public static final class SublimeArchangelAbility2 extends StaticAbility
	{
		public SublimeArchangelAbility2(GameState state)
		{
			super(state, "Other creatures you control have exalted.");
			this.addEffectPart(addAbilityToObject(RelativeComplement.instance(CREATURES_YOU_CONTROL, This.instance()), new SimpleAbilityFactory(org.rnd.jmagic.abilities.keywords.Exalted.class)));
		}
	}

	public SublimeArchangel(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(3);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Exalted (Whenever a creature you control attacks alone, that creature
		// gets +1/+1 until end of turn.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Exalted(state));

		// Other creatures you control have exalted. (If a creature has multiple
		// instances of exalted, each triggers separately.)
		this.addAbility(new SublimeArchangelAbility2(state));
	}
}
