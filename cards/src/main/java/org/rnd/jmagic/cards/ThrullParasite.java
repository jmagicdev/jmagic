package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Thrull Parasite")
@Types({Type.CREATURE})
@SubTypes({SubType.THRULL})
@ManaCost("B")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class ThrullParasite extends Card
{
	public static final class ThrullParasiteAbility1 extends ActivatedAbility
	{
		public ThrullParasiteAbility1(GameState state)
		{
			super(state, "(T), Pay 2 life: Remove a counter from target nonland permanent.");
			this.costsTap = true;
			this.addCost(payLife(You.instance(), 2, "Pay 2 life"));

			SetGenerator restriction = RelativeComplement.instance(Permanents.instance(), HasType.instance(Type.LAND));
			SetGenerator target = targetedBy(this.addTarget(restriction, "target nonland permanent"));
			EventFactory removeCounters = new EventFactory(EventType.REMOVE_COUNTERS_CHOICE, "Remove a counter from target nonland permanent.");
			removeCounters.parameters.put(EventType.Parameter.CAUSE, This.instance());
			removeCounters.parameters.put(EventType.Parameter.COUNTER, CountersOn.instance(target));
			removeCounters.parameters.put(EventType.Parameter.PLAYER, You.instance());
			this.addEffect(removeCounters);
		}
	}

	public ThrullParasite(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Extort (Whenever you cast a spell, you may pay (w/b). If you do, each
		// opponent loses 1 life and you gain that much life.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Extort(state));

		// (T), Pay 2 life: Remove a counter from target nonland permanent.
		this.addAbility(new ThrullParasiteAbility1(state));
	}
}
