package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Necropede")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.INSECT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class Necropede extends Card
{
	public static final class NecropedeAbility1 extends EventTriggeredAbility
	{
		public NecropedeAbility1(GameState state)
		{
			super(state, "When Necropede dies, you may put a -1/-1 counter on target creature.");
			this.addPattern(whenThisIsPutIntoAGraveyardFromTheBattlefield());
			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(youMay(putCounters(1, Counter.CounterType.MINUS_ONE_MINUS_ONE, target, "Put a -1/-1 counter on target creature."), "You may put a -1/-1 counter on target creature."));
		}
	}

	public Necropede(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Infect (This creature deals damage to creatures in the form of -1/-1
		// counters and to players in the form of poison counters.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Infect(state));

		// When Necropede is put into a graveyard from the battlefield, you may
		// put a -1/-1 counter on target creature.
		this.addAbility(new NecropedeAbility1(state));
	}
}
