package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Reaper of Sheoldred")
@Types({Type.CREATURE})
@SubTypes({SubType.HORROR})
@ManaCost("4B")
@Printings({@Printings.Printed(ex = NewPhyrexia.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class ReaperofSheoldred extends Card
{
	public static final class ReaperofSheoldredAbility1 extends EventTriggeredAbility
	{
		public ReaperofSheoldredAbility1(GameState state)
		{
			super(state, "Whenever a source deals damage to Reaper of Sheoldred, that source's controller gets a poison counter.");
			this.addPattern(whenIsDealtDamage(ABILITY_SOURCE_OF_THIS));

			SetGenerator source = SourceOfDamage.instance(TriggerDamage.instance(This.instance()));
			SetGenerator controller = ControllerOf.instance(source);
			this.addEffect(putCounters(1, Counter.CounterType.POISON, controller, "That source's controller gets a poison counter."));
		}
	}

	public ReaperofSheoldred(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(5);

		// Infect (This creature deals damage to creatures in the form of -1/-1
		// counters and to players in the form of poison counters.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Infect(state));

		// Whenever a source deals damage to Reaper of Sheoldred, that source's
		// controller gets a poison counter.
		this.addAbility(new ReaperofSheoldredAbility1(state));
	}
}
