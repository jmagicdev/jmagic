package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Bladed Pinions")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("2")
@ColorIdentity({})
public final class BladedPinions extends Card
{
	public static final class BladedPinionsAbility0 extends StaticAbility
	{
		public BladedPinionsAbility0(GameState state)
		{
			super(state, "Equipped creature has flying and first strike.");

			this.addEffectPart(addAbilityToObject(EquippedBy.instance(This.instance()), org.rnd.jmagic.abilities.keywords.Flying.class, org.rnd.jmagic.abilities.keywords.FirstStrike.class));
		}
	}

	public BladedPinions(GameState state)
	{
		super(state);

		// Equipped creature has flying and first strike.
		this.addAbility(new BladedPinionsAbility0(state));

		// Equip (2)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(2)"));
	}
}
