package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Murder Investigation")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class MurderInvestigation extends Card
{
	public static final class MurderInvestigationAbility1 extends EventTriggeredAbility
	{
		public MurderInvestigationAbility1(GameState state)
		{
			super(state, "When enchanted creature dies, put X 1/1 white Soldier creature tokens onto the battlefield, where X is its power.");

			SetGenerator enchanted = EnchantedBy.instance(ABILITY_SOURCE_OF_THIS);
			this.addPattern(whenXDies(enchanted));

			SetGenerator X = PowerOf.instance(OldObjectOf.instance(TriggerZoneChange.instance(This.instance())));

			CreateTokensFactory factory = new CreateTokensFactory(X, numberGenerator(1), numberGenerator(1), "Put X 1/1 white Soldier creature tokens onto the battlefield, where X is its power.");
			factory.setColors(Color.WHITE);
			factory.setSubTypes(SubType.SOLDIER);
			this.addEffect(factory.getEventFactory());
		}
	}

	public MurderInvestigation(GameState state)
	{
		super(state);

		// Enchant creature you control
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.CreatureYouControl(state));

		// When enchanted creature dies, put X 1/1 white Soldier creature tokens
		// onto the battlefield, where X is its power.
		this.addAbility(new MurderInvestigationAbility1(state));
	}
}
