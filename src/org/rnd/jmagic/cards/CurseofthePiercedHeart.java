package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Curse of the Pierced Heart")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.CURSE, SubType.AURA})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class CurseofthePiercedHeart extends Card
{
	public static final class CurseofthePiercedHeartAbility1 extends EventTriggeredAbility
	{
		public CurseofthePiercedHeartAbility1(GameState state)
		{
			super(state, "At the beginning of enchanted player's upkeep, Curse of the Pierced Heart deals 1 damage to that player.");

			SetGenerator enchantedPlayer = EnchantedBy.instance(ABILITY_SOURCE_OF_THIS);

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BEGIN_STEP);
			pattern.put(EventType.Parameter.STEP, UpkeepStepOf.instance(enchantedPlayer));
			this.addPattern(pattern);

			this.addEffect(permanentDealDamage(1, enchantedPlayer, "Curse of the Pierced Heart deals 1 damage to that player."));
		}
	}

	public CurseofthePiercedHeart(GameState state)
	{
		super(state);

		// Enchant player
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Player(state));

		// At the beginning of enchanted player's upkeep, Curse of the Pierced
		// Heart deals 1 damage to that player.
		this.addAbility(new CurseofthePiercedHeartAbility1(state));
	}
}
