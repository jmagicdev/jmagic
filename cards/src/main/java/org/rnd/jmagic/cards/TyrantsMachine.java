package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Tyrant's Machine")
@Types({Type.ARTIFACT})
@ManaCost("2")
@ColorIdentity({})
public final class TyrantsMachine extends Card
{
	public static final class TyrantsMachineAbility0 extends ActivatedAbility
	{
		public TyrantsMachineAbility0(GameState state)
		{
			super(state, "(4), (T): Tap target creature.");
			this.setManaCost(new ManaPool("(4)"));
			this.costsTap = true;

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(tap(target, "Tap target creature."));
		}
	}

	public TyrantsMachine(GameState state)
	{
		super(state);

		// (4), (T): Tap target creature.
		this.addAbility(new TyrantsMachineAbility0(state));
	}
}
