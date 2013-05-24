package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Soul Tithe")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1W")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class SoulTithe extends Card
{
	public static final class SoulTitheAbility1 extends EventTriggeredAbility
	{
		public SoulTitheAbility1(GameState state)
		{
			super(state, "At the beginning of the upkeep of enchanted permanent's controller, that player sacrifices it unless he or she pays (X), where X is its converted mana cost.");
			SetGenerator enchantedPermanent = EnchantedBy.instance(ABILITY_SOURCE_OF_THIS);
			SetGenerator enchantedPermanentsController = ControllerOf.instance(enchantedPermanent);

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BEGIN_STEP);
			pattern.put(EventType.Parameter.STEP, UpkeepStepOf.instance(enchantedPermanentsController));
			this.addPattern(pattern);

			EventFactory sac = sacrificeSpecificPermanents(enchantedPermanentsController, enchantedPermanent, "That player sacrifices it");

			EventFactory pay = new EventFactory(EventType.PAY_MANA, "He or she pays (X), where X is its converted mana cost");
			pay.parameters.put(EventType.Parameter.CAUSE, This.instance());
			pay.parameters.put(EventType.Parameter.COST, Identity.instance(new ManaPool("1")));
			pay.parameters.put(EventType.Parameter.PLAYER, enchantedPermanentsController);
			pay.parameters.put(EventType.Parameter.NUMBER, ConvertedManaCostOf.instance(enchantedPermanent));

			this.addEffect(unless(enchantedPermanentsController, sac, pay, "That player sacrifices it unless he or she pays (X), where X is its converted mana cost."));
		}
	}

	public SoulTithe(GameState state)
	{
		super(state);

		// Enchant nonland permanent
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.NonLandPermanent(state));

		// At the beginning of the upkeep of enchanted permanent's controller,
		// that player sacrifices it unless he or she pays (X), where X is its
		// converted mana cost.
		this.addAbility(new SoulTitheAbility1(state));
	}
}
