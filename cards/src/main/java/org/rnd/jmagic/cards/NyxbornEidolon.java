package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;


@Name("Nyxborn Eidolon")
@Types({Type.ENCHANTMENT, Type.CREATURE})
@SubTypes({SubType.SPIRIT})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = Theros.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class NyxbornEidolon extends Card
{
	public NyxbornEidolon(GameState state)
	{
		super(state);
		
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Bestow(state, "(4)(B)"));
		
		this.addAbility(new org.rnd.jmagic.abilities.StaticPTChange(state, EnchantedBy.instance(This.instance()), "Enchanted creature", +2, +1, false));

		this.setPower(2);
		this.setToughness(1);
	}
}
