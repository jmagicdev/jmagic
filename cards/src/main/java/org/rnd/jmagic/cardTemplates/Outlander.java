package org.rnd.jmagic.cardTemplates;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public abstract class Outlander extends Card
{
	public Outlander(GameState state, Color protectionFrom)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Protection.From(state, HasColor.instance(protectionFrom), protectionFrom.toString()));
	}
}
