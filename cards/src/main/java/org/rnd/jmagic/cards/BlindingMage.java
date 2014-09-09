package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Blinding Mage")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WIZARD})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class BlindingMage extends Card
{
	public static final class TapDown extends ActivatedAbility
	{
		public TapDown(GameState state)
		{
			super(state, "(W), (T): Tap target creature.");

			// (W),
			this.setManaCost(new ManaPool("W"));

			// (T):
			this.costsTap = true;

			// Tap target creature.
			Target target = this.addTarget(CreaturePermanents.instance(), "target creature");
			this.addEffect(tap(targetedBy(target), "Tap target creature."));
		}
	}

	public BlindingMage(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(2);

		this.addAbility(new TapDown(state));
	}
}
