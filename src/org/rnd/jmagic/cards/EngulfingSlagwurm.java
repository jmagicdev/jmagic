package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Engulfing Slagwurm")
@Types({Type.CREATURE})
@SubTypes({SubType.WURM})
@ManaCost("5GG")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class EngulfingSlagwurm extends Card
{
	public static final class EngulfingSlagwurmAbility0 extends EventTriggeredAbility
	{
		public EngulfingSlagwurmAbility0(GameState state)
		{
			super(state, "Whenever Engulfing Slagwurm blocks or becomes blocked by a creature, destroy that creature. You gain life equal to that creature's toughness.");

			SetGenerator trigger = TriggerEvent.instance(This.instance());

			// Since we don't know which trigger pattern triggered, we can get
			// 'that creature' by unioning the attacker and defender together,
			// and removing this card.
			SetGenerator thatCreature = RelativeComplement.instance(Union.instance(EventParameter.instance(trigger, EventType.Parameter.ATTACKER), EventParameter.instance(trigger, EventType.Parameter.DEFENDER)), ABILITY_SOURCE_OF_THIS);

			SimpleEventPattern becomesBlockedPattern = new SimpleEventPattern(EventType.BECOMES_BLOCKED_BY_ONE);
			becomesBlockedPattern.put(EventType.Parameter.ATTACKER, ABILITY_SOURCE_OF_THIS);
			becomesBlockedPattern.put(EventType.Parameter.DEFENDER, CreaturePermanents.instance());
			this.addPattern(becomesBlockedPattern);

			SimpleEventPattern blocksPattern = new SimpleEventPattern(EventType.BECOMES_BLOCKED_BY_ONE);
			blocksPattern.put(EventType.Parameter.ATTACKER, CreaturePermanents.instance());
			blocksPattern.put(EventType.Parameter.DEFENDER, ABILITY_SOURCE_OF_THIS);
			this.addPattern(blocksPattern);

			this.addEffect(destroy(thatCreature, "Destroy that creature."));

			this.addEffect(gainLife(You.instance(), ToughnessOf.instance(thatCreature), "You gain life equal to that creature's toughness."));
		}
	}

	public EngulfingSlagwurm(GameState state)
	{
		super(state);

		this.setPower(7);
		this.setToughness(7);

		// Whenever Engulfing Slagwurm blocks or becomes blocked by a creature,
		// destroy that creature. You gain life equal to that creature's
		// toughness.
		this.addAbility(new EngulfingSlagwurmAbility0(state));
	}
}
