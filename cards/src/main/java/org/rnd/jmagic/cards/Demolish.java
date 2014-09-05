package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Demolish")
@Types({Type.SORCERY})
@ManaCost("3R")
@Printings({@Printings.Printed(ex = AvacynRestored.class, r = Rarity.COMMON), @Printings.Printed(ex = Magic2011.class, r = Rarity.COMMON), @Printings.Printed(ex = Zendikar.class, r = Rarity.COMMON), @Printings.Printed(ex = TenthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = NinthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = EighthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Odyssey.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class Demolish extends Card
{
	public Demolish(GameState state)
	{
		super(state);

		SetGenerator artifact = HasType.instance(Type.ARTIFACT);
		SetGenerator land = HasType.instance(Type.LAND);
		SetGenerator targetableTypes = Union.instance(artifact, land);
		SetGenerator targets = Intersect.instance(InZone.instance(Battlefield.instance()), targetableTypes);

		Target target = this.addTarget(targets, "target artifact or land");
		this.addEffect(destroy(targetedBy(target), "Destroy target artifact or land."));
	}
}
