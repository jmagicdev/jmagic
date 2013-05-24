package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Curse of Thirst")
@Types({Type.ENCHANTMENT})
@SubTypes({SubType.CURSE, SubType.AURA})
@ManaCost("4B")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class CurseofThirst extends Card
{
	public static final class CurseofThirstAbility1 extends EventTriggeredAbility
	{
		public CurseofThirstAbility1(GameState state)
		{
			super(state, "At the beginning of enchanted player's upkeep, Curse of Thirst deals damage to that player equal to the number of Curses attached to him or her.");

			SetGenerator enchanted = EnchantedBy.instance(ABILITY_SOURCE_OF_THIS);

			SimpleEventPattern pattern = new SimpleEventPattern(EventType.BEGIN_STEP);
			pattern.put(EventType.Parameter.STEP, UpkeepStepOf.instance(enchanted));
			this.addPattern(pattern);

			SetGenerator amount = Count.instance(Intersect.instance(AttachedTo.instance(enchanted), HasSubType.instance(SubType.CURSE)));
			this.addEffect(permanentDealDamage(amount, enchanted, "Curse of Thirst deals damage to that player equal to the number of Curses attached to him or her."));
		}
	}

	public CurseofThirst(GameState state)
	{
		super(state);

		// Enchant player
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Enchant.Player(state));

		// At the beginning of enchanted player's upkeep, Curse of Thirst deals
		// damage to that player equal to the number of Curses attached to him
		// or her.
		this.addAbility(new CurseofThirstAbility1(state));
	}
}
