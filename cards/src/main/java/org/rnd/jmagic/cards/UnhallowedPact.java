package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Unhallowed Pact")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("2B")
@Printings({@Printings.Printed(ex = AvacynRestored.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class UnhallowedPact extends Card
{
	public static final class UnhallowedPactAbility1 extends EventTriggeredAbility
	{
		public UnhallowedPactAbility1(GameState state)
		{
			super(state, "When enchanted creature dies, return that card to the battlefield under your control.");

			SetGenerator enchanted = EnchantedBy.instance(ABILITY_SOURCE_OF_THIS);
			this.addPattern(whenXDies(enchanted));

			SetGenerator thatCard = NewObjectOf.instance(TriggerZoneChange.instance(This.instance()));
			this.addEffect(putOntoBattlefield(thatCard, You.instance(), "Return that card to the battlefield under your control.", false));
		}
	}

	public UnhallowedPact(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// When enchanted creature dies, return that card to the battlefield
		// under your control.
		this.addAbility(new UnhallowedPactAbility1(state));
	}
}
