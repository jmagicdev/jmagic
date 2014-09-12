package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Rogue's Gloves")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("2")
@ColorIdentity({})
public final class RoguesGloves extends Card
{
	public static final class RoguesGlovesAbility0 extends EventTriggeredAbility
	{
		public RoguesGlovesAbility0(GameState state)
		{
			super(state, "Whenever equipped creature deals combat damage to a player, you may draw a card.");

			SimpleDamagePattern damage = new SimpleDamagePattern(EquippedBy.instance(This.instance()), null, true);
			this.addPattern(damage);

			this.addEffect(youMay(drawACard()));
		}
	}

	public RoguesGloves(GameState state)
	{
		super(state);

		// Whenever equipped creature deals combat damage to a player, you may
		// draw a card.
		this.addAbility(new RoguesGlovesAbility0(state));

		// Equip (2) ((2): Attach to target creature you control. Equip only as
		// a sorcery.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(2)"));
	}
}
