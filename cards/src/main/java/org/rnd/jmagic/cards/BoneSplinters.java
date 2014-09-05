package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Bone Splinters")
@Types({Type.SORCERY})
@ManaCost("B")
@Printings({@Printings.Printed(ex = AvacynRestored.class, r = Rarity.COMMON), @Printings.Printed(ex = ShardsOfAlara.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class BoneSplinters extends Card
{
	public BoneSplinters(GameState state)
	{
		super(state);

		this.addCost(sacrifice(You.instance(), 1, HasType.instance(Type.CREATURE), "sacrifice a creature"));

		Target target = this.addTarget(CreaturePermanents.instance(), "target creature");
		this.addEffect(destroy(targetedBy(target), "Destroy target creature."));
	}
}
