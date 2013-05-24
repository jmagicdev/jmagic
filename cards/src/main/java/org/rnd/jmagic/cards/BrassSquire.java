package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Brass Squire")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.MYR})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class BrassSquire extends Card
{
	public static final class BrassSquireAbility0 extends ActivatedAbility
	{
		public BrassSquireAbility0(GameState state)
		{
			super(state, "(T): Attach target Equipment you control to target creature you control.");
			this.costsTap = true;

			SetGenerator equipment = targetedBy(this.addTarget(Intersect.instance(ControlledBy.instance(You.instance()), HasSubType.instance(SubType.EQUIPMENT)), "target Equipment you control"));
			SetGenerator creature = targetedBy(this.addTarget(CREATURES_YOU_CONTROL, "target creature you control"));

			EventFactory attach = new EventFactory(EventType.ATTACH, "Attach target Equipment you control to target creature you control.");
			attach.parameters.put(EventType.Parameter.OBJECT, equipment);
			attach.parameters.put(EventType.Parameter.TARGET, creature);
			this.addEffect(attach);
		}
	}

	public BrassSquire(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(3);

		// (T): Attach target Equipment you control to target creature you
		// control.
		this.addAbility(new BrassSquireAbility0(state));
	}
}
