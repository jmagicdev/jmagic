package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Beastmaster's Magemark")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("2G")
@Printings({@Printings.Printed(ex = Guildpact.class, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class BeastmastersMagemark extends Card
{
	public static final class BeastmastersMagemarkAbility1 extends StaticAbility
	{
		public BeastmastersMagemarkAbility1(GameState state)
		{
			super(state, "Creatures you control that are enchanted get +1/+1.");

			SetGenerator enchanted = EnchantedBy.instance(HasType.instance(Type.ENCHANTMENT));
			SetGenerator enchantedCreaturesYouControl = Intersect.instance(enchanted, CREATURES_YOU_CONTROL);
			this.addEffectPart(modifyPowerAndToughness(enchantedCreaturesYouControl, +1, +1));
		}
	}

	public static final class BeastmastersMagemarkAbility2 extends EventTriggeredAbility
	{
		public BeastmastersMagemarkAbility2(GameState state)
		{
			super(state, "Whenever a creature you control that's enchanted becomes blocked, it gets +1/+1 until end of turn for each creature blocking it.");

			SetGenerator enchanted = EnchantedBy.instance(HasType.instance(Type.ENCHANTMENT));
			SetGenerator enchantedCreaturesYouControl = Intersect.instance(enchanted, CREATURES_YOU_CONTROL);

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BECOMES_BLOCKED);
			pattern.put(EventType.Parameter.ATTACKER, enchantedCreaturesYouControl);
			this.addPattern(pattern);

			SetGenerator triggerEvent = TriggerEvent.instance(This.instance());
			SetGenerator attacker = EventParameter.instance(triggerEvent, EventType.Parameter.ATTACKER);
			SetGenerator blockers = EventParameter.instance(triggerEvent, EventType.Parameter.DEFENDER);
			SetGenerator count = Count.instance(blockers);
			this.addEffect(ptChangeUntilEndOfTurn(attacker, count, count, "It gets +1/+1 until end of turn for each creature blocking it."));
		}
	}

	public BeastmastersMagemark(GameState state)
	{
		super(state);

		// Enchant creature
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));

		// Creatures you control that are enchanted get +1/+1.
		this.addAbility(new BeastmastersMagemarkAbility1(state));

		// Whenever a creature you control that's enchanted becomes blocked, it
		// gets +1/+1 until end of turn for each creature blocking it.
		this.addAbility(new BeastmastersMagemarkAbility2(state));
	}
}
