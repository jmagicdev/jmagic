package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Basilisk Collar")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("1")
@Printings({@Printings.Printed(ex = Worldwake.class, r = Rarity.RARE)})
@ColorIdentity({})
public final class BasiliskCollar extends Card
{
	public static final class HasAbilities extends StaticAbility
	{
		public HasAbilities(GameState state)
		{
			super(state, "Equipped creature has deathtouch and lifelink.");
			this.addEffectPart(addAbilityToObject(EquippedBy.instance(This.instance()), org.rnd.jmagic.abilities.keywords.Deathtouch.class, org.rnd.jmagic.abilities.keywords.Lifelink.class));
		}
	}

	public BasiliskCollar(GameState state)
	{
		super(state);

		// Equipped creature has deathtouch and lifelink.
		this.addAbility(new HasAbilities(state));

		// Equip (2)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(2)"));
	}
}
