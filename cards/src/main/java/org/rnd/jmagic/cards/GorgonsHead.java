package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Gorgon's Head")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("1")
@ColorIdentity({})
public final class GorgonsHead extends Card
{
	public static final class GorgonsHeadAbility0 extends StaticAbility
	{
		public GorgonsHeadAbility0(GameState state)
		{
			super(state, "Equipped creature has deathtouch.");
			this.addEffectPart(addAbilityToObject(EquippedBy.instance(This.instance()), org.rnd.jmagic.abilities.keywords.Deathtouch.class));
		}
	}

	public GorgonsHead(GameState state)
	{
		super(state);

		// Equipped creature has deathtouch.
		this.addAbility(new GorgonsHeadAbility0(state));

		// Equip (2)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(2)"));
	}
}
