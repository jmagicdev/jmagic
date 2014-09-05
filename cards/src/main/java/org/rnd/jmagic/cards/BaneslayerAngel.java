package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Baneslayer Angel")
@Types({Type.CREATURE})
@SubTypes({SubType.ANGEL})
@ManaCost("3WW")
@Printings({@Printings.Printed(ex = Magic2011.class, r = Rarity.MYTHIC), @Printings.Printed(ex = Magic2010.class, r = Rarity.MYTHIC)})
@ColorIdentity({Color.WHITE})
public final class BaneslayerAngel extends Card
{
	public BaneslayerAngel(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.FirstStrike(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Lifelink(state));
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Protection.From(state, Union.instance(HasSubType.instance(SubType.DEMON), HasSubType.instance(SubType.DRAGON)), "Demons and from Dragons"));
	}
}
