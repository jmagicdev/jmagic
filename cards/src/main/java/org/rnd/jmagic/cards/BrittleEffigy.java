package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Brittle Effigy")
@Types({Type.ARTIFACT})
@ManaCost("1")
@Printings({@Printings.Printed(ex = Magic2011.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class BrittleEffigy extends Card
{
	public static final class BrittleEffigyAbility0 extends ActivatedAbility
	{
		public BrittleEffigyAbility0(GameState state)
		{
			super(state, "(4), (T), Exile Brittle Effigy: Exile target creature.");
			this.setManaCost(new ManaPool("(4)"));
			this.costsTap = true;
			this.addCost(exileThis("Brittle Effigy"));
			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(exile(target, "Exile target creature."));
		}
	}

	public BrittleEffigy(GameState state)
	{
		super(state);

		// (4), (T), Exile Brittle Effigy: Exile target creature.
		this.addAbility(new BrittleEffigyAbility0(state));
	}
}
