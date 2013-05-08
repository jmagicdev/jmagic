package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Ivy Lane Denizen")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.ELF})
@ManaCost("3G")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class IvyLaneDenizen extends Card
{
	public static final class IvyLaneDenizenAbility0 extends EventTriggeredAbility
	{
		public IvyLaneDenizenAbility0(GameState state)
		{
			super(state, "Whenever another green creature enters the battlefield under your control, put a +1/+1 counter on target creature.");

			SetGenerator greenCreatures = Intersect.instance(HasColor.instance(Color.GREEN), CreaturePermanents.instance());
			SetGenerator anotherGreenCreature = RelativeComplement.instance(greenCreatures, ABILITY_SOURCE_OF_THIS);
			SimpleZoneChangePattern p = new SimpleZoneChangePattern(null, Battlefield.instance(), anotherGreenCreature, You.instance(), false);
			this.addPattern(p);

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, target, "Put a +1/+1 counter on target creature."));
		}
	}

	public IvyLaneDenizen(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// Whenever another green creature enters the battlefield under your
		// control, put a +1/+1 counter on target creature.
		this.addAbility(new IvyLaneDenizenAbility0(state));
	}
}
