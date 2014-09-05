package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Soul Ransom")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("2UB")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class SoulRansom extends Card
{
	public static final class SoulRansomAbility1 extends StaticAbility
	{
		public SoulRansomAbility1(GameState state)
		{
			super(state, "You control enchanted creature.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.CHANGE_CONTROL);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, EnchantedBy.instance(This.instance()));
			part.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
			this.addEffectPart(part);
		}
	}

	public static final class SoulRansomAbility2 extends ActivatedAbility
	{
		public SoulRansomAbility2(GameState state)
		{
			super(state, "Discard two cards: Soul Ransom's controller sacrifices it, then draws two cards. Only any opponent may activate this ability.");
			this.addCost(discardCards(You.instance(), 2, "Discard two cards"));

			SetGenerator thisController = ControllerOf.instance(ABILITY_SOURCE_OF_THIS);
			this.addEffect(sacrificeSpecificPermanents(thisController, ABILITY_SOURCE_OF_THIS, "Soul Ransom's controller sacrifices it,"));
			this.addEffect(drawCards(thisController, 2, "then draws two cards."));

			this.onlyOpponentsMayActivateThisAbility();

		}
	}

	public SoulRansom(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// You control enchanted creature.
		this.addAbility(new SoulRansomAbility1(state));

		// Discard two cards: Soul Ransom's controller sacrifices it, then draws
		// two cards. Only any opponent may activate this ability.
		this.addAbility(new SoulRansomAbility2(state));
	}
}
