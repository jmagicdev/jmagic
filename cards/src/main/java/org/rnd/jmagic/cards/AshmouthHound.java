package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Ashmouth Hound")
@Types({Type.CREATURE})
@SubTypes({SubType.HOUND, SubType.ELEMENTAL})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class AshmouthHound extends Card
{
	public static final class AshmouthHoundAbility0 extends EventTriggeredAbility
	{
		public AshmouthHoundAbility0(GameState state)
		{
			super(state, "Whenever Ashmouth Hound blocks or becomes blocked by a creature, Ashmouth Hound deals 1 damage to that creature.");

			SetGenerator trigger = TriggerEvent.instance(This.instance());

			// Since we don't know which trigger pattern triggered, we can get
			// 'that creature' by unioning the attacker and defender together,
			// and removing this card.
			SetGenerator attacker = EventParameter.instance(trigger, EventType.Parameter.ATTACKER);
			SetGenerator defender = EventParameter.instance(trigger, EventType.Parameter.DEFENDER);
			SetGenerator thatCreature = RelativeComplement.instance(Union.instance(attacker, defender), ABILITY_SOURCE_OF_THIS);

			SimpleEventPattern becomesBlockedPattern = new SimpleEventPattern(EventType.BECOMES_BLOCKED_BY_ONE);
			becomesBlockedPattern.put(EventType.Parameter.ATTACKER, ABILITY_SOURCE_OF_THIS);
			becomesBlockedPattern.put(EventType.Parameter.DEFENDER, CreaturePermanents.instance());
			this.addPattern(becomesBlockedPattern);

			SimpleEventPattern blocksPattern = new SimpleEventPattern(EventType.BECOMES_BLOCKED_BY_ONE);
			blocksPattern.put(EventType.Parameter.ATTACKER, CreaturePermanents.instance());
			blocksPattern.put(EventType.Parameter.DEFENDER, ABILITY_SOURCE_OF_THIS);
			this.addPattern(blocksPattern);

			this.addEffect(permanentDealDamage(1, thatCreature, "Ashmouth Hound deals 1 damage to that creature."));
		}
	}

	public AshmouthHound(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Whenever Ashmouth Hound blocks or becomes blocked by a creature,
		// Ashmouth Hound deals 1 damage to that creature.
		this.addAbility(new AshmouthHoundAbility0(state));
	}
}
