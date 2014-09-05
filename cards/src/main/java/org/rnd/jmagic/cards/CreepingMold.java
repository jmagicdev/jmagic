package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Creeping Mold")
@Types({Type.SORCERY})
@ManaCost("2GG")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = NinthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Mirrodin.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = EighthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = SeventhEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = ClassicSixthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Visions.class, r = Rarity.UNCOMMON)})
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
