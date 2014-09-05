package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Hunting Triad")
@Types({Type.SORCERY, Type.TRIBAL})
@SubTypes({SubType.ELF})
@ManaCost("3G")
@Printings({@Printings.Printed(ex = Morningtide.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class HuntingTriad extends Card
{
	public HuntingTriad(GameState state)
	{
		super(state);

		// Put three 1/1 green Elf Warrior creature tokens onto the battlefield.
		CreateTokensFactory tokens = new CreateTokensFactory(3, 1, 1, "Put three 1/1 green Elf Warrior creature tokens onto the battlefield.");
		tokens.setColors(Color.GREEN);
		tokens.setSubTypes(SubType.ELF, SubType.WARRIOR);
		this.addEffect(tokens.getEventFactory());

		// Reinforce 3
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Reinforce(state, numberGenerator(3), "3", "3G"));
	}
}
