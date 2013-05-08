package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Piston Sledge")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class PistonSledge extends Card
{
	public static final class PistonSledgeAbility0 extends EventTriggeredAbility
	{
		public PistonSledgeAbility0(GameState state)
		{
			super(state, "When Piston Sledge enters the battlefield, attach it to target creature you control.");
			this.addPattern(whenThisEntersTheBattlefield());
			SetGenerator target = targetedBy(this.addTarget(CREATURES_YOU_CONTROL, "target creature you control"));

			EventFactory attach = new EventFactory(EventType.ATTACH, "Attach it to target creature you control.");
			attach.parameters.put(EventType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
			attach.parameters.put(EventType.Parameter.TARGET, target);
			this.addEffect(attach);
		}
	}

	public static final class PistonSledgeAbility1 extends StaticAbility
	{
		public PistonSledgeAbility1(GameState state)
		{
			super(state, "Equipped creature gets +3/+1.");
			this.addEffectPart(modifyPowerAndToughness(EquippedBy.instance(This.instance()), +3, +1));
		}
	}

	public static final String EQUIP_COST_TYPE = "PistonSledgeEquip";

	public PistonSledge(GameState state)
	{
		super(state);

		// When Piston Sledge enters the battlefield, attach it to target
		// creature you control.
		this.addAbility(new PistonSledgeAbility0(state));

		// Equipped creature gets +3/+1.
		this.addAbility(new PistonSledgeAbility1(state));

		// Equip\u2014Sacrifice an artifact.
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, new CostCollection(EQUIP_COST_TYPE, sacrifice(You.instance(), 1, HasType.instance(Type.ARTIFACT), "Sacrifice an artifact"))));
	}
}
