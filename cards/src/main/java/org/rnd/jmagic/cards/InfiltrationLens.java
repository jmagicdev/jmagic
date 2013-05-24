package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Infiltration Lens")
@Types({Type.ARTIFACT})
@SubTypes({SubType.EQUIPMENT})
@ManaCost("1")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class InfiltrationLens extends Card
{
	public static final class InfiltrationLensAbility0 extends EventTriggeredAbility
	{
		public InfiltrationLensAbility0(GameState state)
		{
			super(state, "Whenever equipped creature becomes blocked by a creature, you may draw two cards.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_BLOCKED_BY_ONE);
			pattern.put(EventType.Parameter.ATTACKER, EquippedBy.instance(ABILITY_SOURCE_OF_THIS));
			pattern.put(EventType.Parameter.DEFENDER, CreaturePermanents.instance());
			this.addPattern(pattern);

			this.addEffect(youMay(drawCards(You.instance(), 2, "Draw two cards."), "You may draw two cards."));
		}
	}

	public InfiltrationLens(GameState state)
	{
		super(state);

		// Whenever equipped creature becomes blocked by a creature, you may
		// draw two cards.
		this.addAbility(new InfiltrationLensAbility0(state));

		// Equip (1)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Equip(state, "(1)"));
	}
}
