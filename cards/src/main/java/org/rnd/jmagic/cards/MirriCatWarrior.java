package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Mirri, Cat Warrior")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.CAT})
@ManaCost("1GG")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.EXODUS, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class MirriCatWarrior extends Card
{
	public MirriCatWarrior(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Landwalk.Forestwalk(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vigilance(state));
	}
}
