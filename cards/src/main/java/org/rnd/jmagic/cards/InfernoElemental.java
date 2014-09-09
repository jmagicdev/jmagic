package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Inferno Elemental")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEMENTAL})
@ManaCost("4RR")
@ColorIdentity({Color.RED})
public final class InfernoElemental extends Card
{
	public static final class CatchFire extends EventTriggeredAbility
	{
		public CatchFire(GameState state)
		{
			super(state, "Whenever Inferno Elemental blocks or becomes blocked by a creature, Inferno Elemental deals 3 damage to that creature.");

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

			this.addEffect(permanentDealDamage(3, thatCreature, "Inferno Elemental deals 3 damage to that creature."));
		}
	}

	public InfernoElemental(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		this.addAbility(new CatchFire(state));
	}
}
