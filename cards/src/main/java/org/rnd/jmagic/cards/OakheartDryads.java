package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Oakheart Dryads")
@Types({Type.ENCHANTMENT, Type.CREATURE})
@SubTypes({SubType.NYMPH, SubType.DRYAD})
@ManaCost("2G")
@ColorIdentity({Color.GREEN})
public final class OakheartDryads extends Card
{
	public static final class OakheartDryadsAbility0 extends EventTriggeredAbility
	{
		public OakheartDryadsAbility0(GameState state)
		{
			super(state, "Constellation \u2014 Whenever Oakheart Dryads or another enchantment enters the battlefield under your control, target creature gets +1/+1 until end of turn.");
			this.addPattern(constellation());
			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(ptChangeUntilEndOfTurn(target, +1, +1, "Target creature gets +1/+1 until end of turn."));
		}
	}

	public OakheartDryads(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(3);

		// Constellation \u2014 Whenever Oakheart Dryads or another enchantment
		// enters the battlefield under your control, target creature gets +1/+1
		// until end of turn.
		this.addAbility(new OakheartDryadsAbility0(state));
	}
}
