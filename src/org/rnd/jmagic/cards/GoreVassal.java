package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Gore Vassal")
@Types({Type.CREATURE})
@SubTypes({SubType.HOUND})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class GoreVassal extends Card
{
	public static final class GoreVassalAbility0 extends ActivatedAbility
	{
		public GoreVassalAbility0(GameState state)
		{
			super(state, "Sacrifice Gore Vassal: Put a -1/-1 counter on target creature. Then if that creature's toughness is 1 or greater, regenerate it.");
			this.addCost(sacrificeThis("Gore Vassal"));

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(putCounters(1, Counter.CounterType.MINUS_ONE_MINUS_ONE, target, "Put a -1/-1 counter on target creature."));

			EventFactory factory = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "Then if that creature's toughness is 1 or greater, regenerate it.");
			factory.parameters.put(EventType.Parameter.IF, Intersect.instance(ToughnessOf.instance(target), Between.instance(1, null)));
			factory.parameters.put(EventType.Parameter.THEN, Identity.instance(regenerate(target, "Regenerate it.")));
			this.addEffect(factory);
		}
	}

	public GoreVassal(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Sacrifice Gore Vassal: Put a -1/-1 counter on target creature. Then
		// if that creature's toughness is 1 or greater, regenerate it.
		this.addAbility(new GoreVassalAbility0(state));
	}
}
