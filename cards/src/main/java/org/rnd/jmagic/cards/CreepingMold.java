package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Creeping Mold")
@Types({Type.SORCERY})
@ManaCost("2GG")
@ColorIdentity({Color.GREEN})
public final class CreepingMold extends Card
{
	public CreepingMold(GameState state)
	{
		super(state);

		SetGenerator artifact = HasType.instance(Type.ARTIFACT);
		SetGenerator enchantment = HasType.instance(Type.ENCHANTMENT);
		SetGenerator land = HasType.instance(Type.LAND);
		SetGenerator targetableTypes = Union.instance(artifact, Union.instance(enchantment, land));
		SetGenerator targets = Intersect.instance(InZone.instance(Battlefield.instance()), targetableTypes);

		Target target = this.addTarget(targets, "target artifact, enchantment, or land");
		this.addEffect(destroy(targetedBy(target), "Destroy target artifact, enchantment, or land."));
	}
}
