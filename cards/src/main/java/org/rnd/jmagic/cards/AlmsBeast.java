package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Alms Beast")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST})
@ManaCost("2WB")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE, Color.BLACK})
public final class AlmsBeast extends Card
{
	public static final class AlmsBeastAbility0 extends StaticAbility
	{
		public AlmsBeastAbility0(GameState state)
		{
			super(state, "Creatures blocking or blocked by Alms Beast have lifelink.");

			SetGenerator creatures = Union.instance(Blocking.instance(This.instance()), BlockedBy.instance(This.instance()));
			this.addEffectPart(addAbilityToObject(creatures, org.rnd.jmagic.abilities.keywords.Lifelink.class));
		}
	}

	public AlmsBeast(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(6);

		// Creatures blocking or blocked by Alms Beast have lifelink.
		this.addAbility(new AlmsBeastAbility0(state));
	}
}
