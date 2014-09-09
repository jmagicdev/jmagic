package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Vedalken Anatomist")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.VEDALKEN})
@ManaCost("2U")
@ColorIdentity({Color.BLUE})
public final class VedalkenAnatomist extends Card
{
	public static final class VedalkenAnatomistAbility0 extends ActivatedAbility
	{
		public VedalkenAnatomistAbility0(GameState state)
		{
			super(state, "(2)(U), (T): Put a -1/-1 counter on target creature. You may tap or untap that creature.");
			this.setManaCost(new ManaPool("(2)(U)"));
			this.costsTap = true;
			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(putCounters(1, Counter.CounterType.MINUS_ONE_MINUS_ONE, target, "Put a -1/-1 counter on target creature."));
			this.addEffect(youMay(tapOrUntap(target, "that creature"), "You may tap or untap that creature."));
		}
	}

	public VedalkenAnatomist(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(2);

		// (2)(U), (T): Put a -1/-1 counter on target creature. You may tap or
		// untap that creature.
		this.addAbility(new VedalkenAnatomistAbility0(state));
	}
}
