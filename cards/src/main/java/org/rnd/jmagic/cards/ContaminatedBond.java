package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Contaminated Bond")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.AURA})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = NinthEdition.class, r = Rarity.COMMON), @Printings.Printed(ex = Mirrodin.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class ContaminatedBond extends Card
{
	public static final class Punish extends EventTriggeredAbility
	{
		public Punish(GameState state)
		{
			super(state, "Whenever enchanted creature attacks or blocks, its controller loses 3 life.");

			SetGenerator enchantedCreature = EnchantedBy.instance(This.instance());

			SimpleEventPattern attackerPattern = new SimpleEventPattern(EventType.DECLARE_ONE_ATTACKER);
			attackerPattern.put(EventType.Parameter.OBJECT, enchantedCreature);
			this.addPattern(attackerPattern);

			SimpleEventPattern blockerPattern = new SimpleEventPattern(EventType.DECLARE_ONE_BLOCKER);
			blockerPattern.put(EventType.Parameter.OBJECT, enchantedCreature);
			this.addPattern(blockerPattern);

			this.addEffect(loseLife(ControllerOf.instance(enchantedCreature), 3, "Its controller loses 3 life."));
		}
	}

	public ContaminatedBond(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Creature(state));
		this.addAbility(new Punish(state));
	}
}
