package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Nacatl Savage")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.CAT})
@ManaCost("1G")
@ColorIdentity({Color.GREEN})
public final class NacatlSavage extends Card
{
	public NacatlSavage(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Protection.From(state, org.rnd.jmagic.engine.generators.HasType.instance(Type.ARTIFACT), "artifacts"));
	}
}
