package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Spidersilk Net")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("0")
@ColorIdentity({})
public final class SpidersilkNet extends Card
{
	public SpidersilkNet(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.StaticPTChange(state, EquippedBy.instance(This.instance()), "Equipped creature", 0, 2, org.rnd.jmagic.abilities.keywords.Reach.class, false));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(2)"));
	}
}
