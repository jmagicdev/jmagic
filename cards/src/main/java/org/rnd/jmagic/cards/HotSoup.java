package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Hot Soup")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("1")
@ColorIdentity({})
public final class HotSoup extends Card
{
	public static final class HotSoupAbility0 extends StaticAbility
	{
		public HotSoupAbility0(GameState state)
		{
			super(state, "Equipped creature can't be blocked.");
			this.addEffectPart(unblockable(EquippedBy.instance(This.instance())));
		}
	}

	public static final class HotSoupAbility1 extends EventTriggeredAbility
	{
		public HotSoupAbility1(GameState state)
		{
			super(state, "Whenever equipped creature is dealt damage, destroy it.");
			SetGenerator equipped = EquippedBy.instance(This.instance());
			this.addPattern(whenIsDealtDamage(equipped));
			this.addEffect(destroy(equipped, "Destroy equipped creature."));
		}
	}

	public HotSoup(GameState state)
	{
		super(state);

		// Equipped creature can't be blocked.
		this.addAbility(new HotSoupAbility0(state));

		// Whenever equipped creature is dealt damage, destroy it.
		this.addAbility(new HotSoupAbility1(state));

		// Equip (3) ((3): Attach to target creature you control. Equip only as
		// a sorcery.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(3)"));
	}
}
