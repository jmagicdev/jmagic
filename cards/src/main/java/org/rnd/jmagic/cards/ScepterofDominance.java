package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Scepter of Dominance")
@Types({Type.ARTIFACT})
@ManaCost("1WW")
@Printings({@Printings.Printed(ex = Conflux.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class ScepterofDominance extends Card
{
	public static final class TapDown extends ActivatedAbility
	{
		public TapDown(GameState state)
		{
			super(state, "(W), (T): Tap target permanent.");
			this.setManaCost(new ManaPool("W"));
			this.costsTap = true;

			Target target = this.addTarget(Permanents.instance(), "target permanent");
			this.addEffect(tap(targetedBy(target), "Tap target permanent."));
		}
	}

	public ScepterofDominance(GameState state)
	{
		super(state);

		// (W), (T): Tap target permanent.
		this.addAbility(new TapDown(state));
	}
}
