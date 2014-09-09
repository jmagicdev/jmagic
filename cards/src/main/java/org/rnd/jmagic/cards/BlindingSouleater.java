package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Blinding Souleater")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.CLERIC})
@ManaCost("3")
@ColorIdentity({Color.WHITE})
public final class BlindingSouleater extends Card
{
	public static final class BlindingSouleaterAbility0 extends ActivatedAbility
	{
		public BlindingSouleaterAbility0(GameState state)
		{
			super(state, "(w/p), (T): Tap target creature.");
			this.setManaCost(new ManaPool("(w/p)"));
			this.costsTap = true;
			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(tap(target, "Tap target creature."));
		}
	}

	public BlindingSouleater(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(3);

		// (w/p), (T): Tap target creature. ((w/p) can be paid with either (W)
		// or 2 life.)
		this.addAbility(new BlindingSouleaterAbility0(state));
	}
}
