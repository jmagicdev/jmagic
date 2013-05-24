package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Neurok Hoversail")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("1")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN, r = Rarity.COMMON)})
@ColorIdentity({})
public final class NeurokHoversail extends Card
{
	public static final class NeurokHoversailAbility0 extends StaticAbility
	{
		public NeurokHoversailAbility0(GameState state)
		{
			super(state, "Equipped creature has flying.");
			this.addEffectPart(addAbilityToObject(EquippedBy.instance(This.instance()), org.rnd.jmagic.abilities.keywords.Flying.class));
		}
	}

	public NeurokHoversail(GameState state)
	{
		super(state);

		// Equipped creature has flying.
		this.addAbility(new NeurokHoversailAbility0(state));

		// Equip (2) ((2): Attach to target creature you control. Equip only as
		// a sorcery. This card enters the battlefield unattached and stays on
		// the battlefield if the creature leaves.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(2)"));
	}
}
