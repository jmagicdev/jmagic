package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Curse of Stalked Prey")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.CURSE, SubType.AURA})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class CurseofStalkedPrey extends Card
{
	public static final class CurseofStalkedPreyAbility1 extends EventTriggeredAbility
	{
		public CurseofStalkedPreyAbility1(GameState state)
		{
			super(state, "Whenever a creature deals combat damage to enchanted player, put a +1/+1 counter on that creature.");
			this.addPattern(new SimpleDamagePattern(CreaturePermanents.instance(), EnchantedBy.instance(ABILITY_SOURCE_OF_THIS), true));

			SetGenerator thatCreature = SourceOfDamage.instance(TriggerDamage.instance(This.instance()));
			this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, thatCreature, "Put a +1/+1 counter on that creature."));
		}
	}

	public CurseofStalkedPrey(GameState state)
	{
		super(state);

		// Enchant player
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Player(state));

		// Whenever a creature deals combat damage to enchanted player, put a
		// +1/+1 counter on that creature.
		this.addAbility(new CurseofStalkedPreyAbility1(state));
	}
}
