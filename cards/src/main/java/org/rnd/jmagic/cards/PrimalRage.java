package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Primal Rage")
@Types({Type.ENCHANTMENT})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = Stronghold.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class PrimalRage extends Card
{
	public static final class GroupActivities extends StaticAbility
	{
		public GroupActivities(GameState state)
		{
			super(state, "Creatures you control have trample.");

			this.addEffectPart(addAbilityToObject(Intersect.instance(CreaturePermanents.instance(), ControlledBy.instance(ControllerOf.instance(This.instance()))), org.rnd.jmagic.abilities.keywords.Trample.class));
		}
	}

	public PrimalRage(GameState state)
	{
		super(state);

		this.addAbility(new GroupActivities(state));
	}
}
