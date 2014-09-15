package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Thunderous Might")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1R")
@ColorIdentity({Color.RED})
public final class ThunderousMight extends Card
{
	public static final class ThunderousMightAbility1 extends EventTriggeredAbility
	{
		public ThunderousMightAbility1(GameState state)
		{
			super(state, "Whenever enchanted creature attacks, it gets +X/+0 until end of turn, where X is your devotion to red.");

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.DECLARE_ONE_ATTACKER);
			pattern.put(EventType.Parameter.OBJECT, EnchantedBy.instance(ABILITY_SOURCE_OF_THIS));
			this.addPattern(pattern);

			this.addEffect(ptChangeUntilEndOfTurn(EnchantedBy.instance(ABILITY_SOURCE_OF_THIS), DevotionTo.instance(Color.RED), numberGenerator(0), "Enchanted creature gets +X/+0 until end of turn, where X is your devotion to red."));
		}
	}

	public ThunderousMight(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Whenever enchanted creature attacks, it gets +X/+0 until end of turn,
		// where X is your devotion to red. (Each (R) in the mana costs of
		// permanents you control counts toward your devotion to red.)
		this.addAbility(new ThunderousMightAbility1(state));
	}
}
