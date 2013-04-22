package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Echo Circlet")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.COMMON)})
@ColorIdentity({})
public final class EchoCirclet extends Card
{
	public static final class EchoCircletAbility0 extends StaticAbility
	{
		public EchoCircletAbility0(GameState state)
		{
			super(state, "Equipped creature can block an additional creature.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.CAN_BLOCK_AN_ADDITIONAL_CREATURE);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, EquippedBy.instance(This.instance()));
			this.addEffectPart(part);
		}
	}

	public EchoCirclet(GameState state)
	{
		super(state);

		// Equipped creature can block an additional creature.
		this.addAbility(new EchoCircletAbility0(state));

		// Equip (1)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(1)"));
	}
}
