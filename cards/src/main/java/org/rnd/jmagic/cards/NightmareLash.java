package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Nightmare Lash")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("4")
@Printings({@Printings.Printed(ex = Mirrodin.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class NightmareLash extends Card
{
	public static final class NightmareLashAbility0 extends StaticAbility
	{
		public NightmareLashAbility0(GameState state)
		{
			super(state, "Equipped creature gets +1/+1 for each Swamp you control.");
			SetGenerator swamps = Count.instance(Intersect.instance(HasSubType.instance(SubType.SWAMP), ControlledBy.instance(You.instance())));
			this.addEffectPart(modifyPowerAndToughness(EquippedBy.instance(This.instance()), swamps, swamps));
		}
	}

	public static final String EQUIP_COST_TYPE = "NightmareLashEquip";

	public NightmareLash(GameState state)
	{
		super(state);

		// Equipped creature gets +1/+1 for each Swamp you control.
		this.addAbility(new NightmareLashAbility0(state));

		// Equip\u2014Pay 3 life. (Pay 3 life: Attach to target creature you
		// control. Equip only as a sorcery. This card enters the battlefield
		// unattached and stays on the battlefield if the creature leaves.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, new CostCollection(EQUIP_COST_TYPE, payLife(You.instance(), 3, "Pay 3 life"))));
	}
}
