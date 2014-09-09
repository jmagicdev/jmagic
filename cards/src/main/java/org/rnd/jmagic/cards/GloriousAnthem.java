package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Glorious Anthem")
@Types({Type.ENCHANTMENT})
@ManaCost("1WW")
@ColorIdentity({Color.WHITE})
public final class GloriousAnthem extends Card
{
	public GloriousAnthem(GameState state)
	{
		super(state);

		SetGenerator creatures = CreaturePermanents.instance();
		SetGenerator youControl = ControlledBy.instance(ControllerOf.instance(This.instance()));
		SetGenerator creaturesYouControl = Intersect.instance(creatures, youControl);
		this.addAbility(new org.rnd.jmagic.abilities.StaticPTChange(state, creaturesYouControl, "Creatures you control", +1, +1, true));
	}
}
